package beer.unacceptable.unacceptablehealth.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class GPSCoords implements Serializable {
    @Expose
    public double Latitude;
    @Expose
    public double Longitude;
    @Expose
    public OffsetDateTime Time;

    public GPSCoords(double lat, double lon) {
        Latitude = lat;
        Longitude = lon;
    }

    public GPSCoords() {}
}
