package beer.unacceptable.unacceptablehealth.Tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
/*
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
*/
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

import beer.unacceptable.unacceptablehealth.Models.GPSCoords;

/**
 * Uses Google Play API for obtaining device locations
 * Created by alejandro.tkachuk
 * alejandro@calculistik.com
 * www.calculistik.com Mobile Development
 */

public class Whereabouts {
/*
    private static final Whereabouts instance = new Whereabouts();

    private static final String TAG = Whereabouts.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;

    private static Workable<GPSCoords> workable;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private static boolean m_bSetup = false;

    private Whereabouts() {

    }

    private static void setup(Context context, Whereabouts whereabouts) {
        /*
        whereabouts.locationRequest = LocationRequest.create();
        whereabouts.locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        whereabouts.locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        whereabouts.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(whereabouts.locationRequest);
        whereabouts.locationSettingsRequest = builder.build();

        whereabouts.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult); // why? this. is. retarded. Android.
                Location currentLocation = locationResult.getLastLocation();

                GPSCoords gpsPoint = new GPSCoords(currentLocation.getLatitude(), currentLocation.getLongitude());
                Log.i(TAG, "Location Callback results: " + gpsPoint);
                if (null != workable)
                    workable.work(gpsPoint);
            }
        };

        whereabouts.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        whereabouts.mFusedLocationClient.requestLocationUpdates(whereabouts.locationRequest,
                whereabouts.locationCallback, Looper.myLooper());
                /*
    }

    public static Whereabouts instance(Context context) {
        if (!m_bSetup)
            setup(context, instance);
        return instance;
    }

    public void onChange(Workable<GPSCoords> workable) {
        this.workable = workable;
    }

    public LocationSettingsRequest getLocationSettingsRequest() {
        return this.locationSettingsRequest;
    }

    public void stop() {
        Log.i(TAG, "stop() Stopping location tracking");
        this.mFusedLocationClient.removeLocationUpdates(this.locationCallback);
    }

    /*public static class GPSPoint {

        private double lat, lon;
        private Date date;
        private String lastUpdate;

        public GPSPoint(double latitude, double longitude) {
            this.lat = latitude;
            this.lon = longitude;
            this.date = new Date();
            this.lastUpdate = DateFormat.getTimeInstance().format(this.date);
        }

        public GPSPoint(Double latitude, Double longitude) {
            this.lat = BigDecimal.valueOf(latitude);
            this.lon = BigDecimal.valueOf(longitude);
        }

        public double getLatitude() {
            return lat;
        }

        public Date getDate() {
            return date;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public double getLongitude() {

            return lon;
        }

        @Override
        public String toString() {
            return "(" + lat + ", " + lon + ")";
        }
    }*/
/*
    public interface Workable<T> {

        public void work(T t);
    }*/
}
