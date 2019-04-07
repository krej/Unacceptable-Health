package beer.unacceptable.unacceptablehealth.Receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.unacceptable.unacceptablelibrary.Repositories.LibraryRepository;
import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import java.util.Calendar;

import beer.unacceptable.unacceptablehealth.Controllers.DailyLogLogic;
import beer.unacceptable.unacceptablehealth.Controllers.DateLogic;
import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Repositories.Repository;
import beer.unacceptable.unacceptablehealth.Screens.ViewDailyLog;

import static android.content.Context.ALARM_SERVICE;

public class DailyLogAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        final DailyLogLogic oLogic = new DailyLogLogic(new Repository(), new DateLogic(), new LibraryRepository());

        oLogic.LoadTodaysLog(new RepositoryCallback() {
            @Override
            public void onSuccess(String t) {
                if (oLogic.continueToLog())
                    sendNotification(context, oLogic.getLog().idString);
            }

            @Override
            public void onError(VolleyError error) {
                //TODO: Maybe send a notification here to go to the daily log list screen?
                Tools.ShowToast(context, "Error checking for daily log.", Toast.LENGTH_SHORT);
                sendNotification(context, "");
            }
        });

    }

    private void sendNotification(Context context, String sIdString) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, ViewDailyLog.class);
        notificationIntent.putExtra("idString", sIdString);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String sNotificationText = getNotificationText(sIdString);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.NOTIFICATION_CHANNEL_ID))
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setContentTitle("Daily Log")
                .setContentText(sNotificationText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setWhen(when)
                .setVibrate(new long [] {1000, 1000, 1000})
                .setAutoCancel(true);

        //TODO: Maybe have this reach out to the server each time to double check that nothing has been logged today? If it has, don't start the notification
        notificationManager.notify(1, mBuilder.build());
    }

    private String getNotificationText(String sIdString) {
        if (Tools.IsEmptyString(sIdString))
            return "The day is coming to an end, time to fill out your daily log!";

        return "Time to finish completing your daily log!";
    }

    public static void SetupDailyLogAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(context, DailyLogAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
