package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Workout extends ListableObject implements Serializable {
    @Expose
    public WorkoutPlan WorkoutPlan;
    @Expose
    public String Notes;
    @Expose
    public Date Date;
    @Expose
    public long StartTime;
    @Expose
    public long EndTime;
    @Expose
    public ArrayList<GPSCoords> GPSCoords;

    public int DurationInMinutes() {
        return (int)((EndTime - StartTime) / 1000 / 60);
    }

    public void AddGPSCoords(double longitude, double latitude, OffsetDateTime time) {
        if (GPSCoords == null) GPSCoords = new ArrayList<GPSCoords>();

        beer.unacceptable.unacceptablehealth.Models.GPSCoords gps = new GPSCoords();
        gps.Latitude = latitude;
        gps.Longitude = longitude;
        gps.Time = null;

        GPSCoords.add(gps);
    }
}
