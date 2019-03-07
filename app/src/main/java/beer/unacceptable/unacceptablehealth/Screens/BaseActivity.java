package beer.unacceptable.unacceptablehealth.Screens;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.unacceptable.unacceptablelibrary.Tools.CustomizedExceptionHandler;
import com.unacceptable.unacceptablelibrary.Tools.Network;
import com.unacceptable.unacceptablelibrary.Tools.Preferences;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import beer.unacceptable.unacceptablehealth.R;
import beer.unacceptable.unacceptablehealth.Receivers.DailyLogAlarmReceiver;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitialAppSetup();
    }

    private boolean InitialAppSetup() {
        createNotificationChannel();
        DailyLogAlarmReceiver.SetupDailyLogAlarm(this);

        //Tools.LoadSharedPrefs(getApplicationContext(), "health");
        Network.getInstance(this.getApplicationContext()); //start the network singleton
        Preferences.getInstance(getApplicationContext(), "health");

        if (!CheckForServerSetting()) return false;

        if (!Tools.LoginTokenExists(this, MainActivity.class)) return false;


        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        Thread.setDefaultUncaughtExceptionHandler(new CustomizedExceptionHandler(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()));

        return true;
    }

    private boolean CheckForServerSetting() {
        if (!Preferences.ServerSettingExists()) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
            Tools.ShowToast(getApplicationContext(), "Select a server to connect to.", Toast.LENGTH_LONG);
            return false;
        }

        return true;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getResources().getString(R.string.NOTIFICATION_CHANNEL_ID), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Tools.ShowToast(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG);

        }
    }

}
