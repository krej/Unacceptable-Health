package beer.unacceptable.unacceptablehealth.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

import beer.unacceptable.unacceptablehealth.R;

public class GPXTest extends BaseActivity implements OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap gmap;
    private GoogleMap mMap;
    private TextView label;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_x_test);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        /*mapView = findViewById(R.id.mapViewTest);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        label = findViewById(R.id.textView4);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Location gps_loc = null;
        Location network_loc = null;
        Location final_loc;
        double longitude;
        double latitude;
        String userCountry, userAddress;

        try {

            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else {
            latitude = 0.0;
            longitude = 0.0;
        }


        LatLng from = new LatLng(latitude, longitude);
        LatLng to = new LatLng(41.905732, -87.802192);
        double distance = SphericalUtil.computeDistanceBetween(from, to);


        String sGPS = "Latitude: " + Double.toString(latitude) + "; Longitude: " + Double.toString(longitude) + "; Distance: " + Double.toString(distance);
        label.setText(sGPS);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));*/
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(41.887708, -87.800290);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        ArrayList<LatLng> coords = createCoords2();
        PolylineOptions plo = new PolylineOptions();

        plo.addAll(coords);
        plo.width(10);
        plo.color(Color.BLUE);
        mMap.addPolyline(plo);


    }
    private ArrayList<LatLng> createCoords2() {
        ArrayList<LatLng> coords = new ArrayList<>();
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88769115,
                -87.80032094));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88860509,
                -87.79881991));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88750939,
                -87.79705212));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88207576,
                -87.79700325));
        coords.add(new LatLng(41.88715,
                -87.79886626));
        coords.add(new LatLng(41.88715,
                -87.79886626));
        coords.add(new LatLng(41.88715,
                -87.79886626));
        coords.add(new LatLng(41.88715,
                -87.79886626));
        coords.add(new LatLng(41.88715,
                -87.79886626));
        coords.add(new LatLng(41.88715,
                -87.79886626));
        return coords;
    }

    private ArrayList<LatLng> createCoords() {
        ArrayList<LatLng> coords = new ArrayList<>();
        coords.add(new LatLng(41.887637000, -87.800420000));
        coords.add(new LatLng(41.887770000, -87.800401000));
        coords.add(new LatLng(41.887993000, -87.800312000));
        coords.add(new LatLng(41.887639000, -87.800486000));
        coords.add(new LatLng(41.887718000, -87.800436000));
        coords.add(new LatLng(41.887760000, -87.800329000));
        coords.add(new LatLng(41.887830000, -87.800268000));
        coords.add(new LatLng(41.887872000, -87.800174000));
        coords.add(new LatLng(41.887961000, -87.800138000));
        coords.add(new LatLng(41.888049000, -87.800132000));
        coords.add(new LatLng(41.888138000, -87.800124000));
        coords.add(new LatLng(41.888220000, -87.800129000));
        coords.add(new LatLng(41.888302000, -87.800095000));
        coords.add(new LatLng(41.888397000, -87.800101000));
        coords.add(new LatLng(41.888483000, -87.800118000));
        coords.add(new LatLng(41.888569000, -87.800091000));
        coords.add(new LatLng(41.888609000, -87.799976000));
        coords.add(new LatLng(41.888687000, -87.799937000));
        coords.add(new LatLng(41.888757000, -87.800008000));
        coords.add(new LatLng(41.888845000, -87.800010000));
        coords.add(new LatLng(41.888935000, -87.800031000));
        coords.add(new LatLng(41.889018000, -87.800054000));
        coords.add(new LatLng(41.889108000, -87.800039000));
        coords.add(new LatLng(41.889191000, -87.800016000));
        coords.add(new LatLng(41.889280000, -87.799995000));
        coords.add(new LatLng(41.889370000, -87.800013000));
        coords.add(new LatLng(41.889444000, -87.800069000));
        coords.add(new LatLng(41.889548000, -87.800073000));
        coords.add(new LatLng(41.889632000, -87.800085000));
        coords.add(new LatLng(41.889725000, -87.800086000));
        coords.add(new LatLng(41.889822000, -87.800087000));
        coords.add(new LatLng(41.889912000, -87.800096000));
        coords.add(new LatLng(41.890001000, -87.800105000));
        coords.add(new LatLng(41.890084000, -87.800106000));
        coords.add(new LatLng(41.890178000, -87.800091000));
        coords.add(new LatLng(41.890261000, -87.800082000));
        coords.add(new LatLng(41.890345000, -87.800062000));
        coords.add(new LatLng(41.890437000, -87.800067000));
        coords.add(new LatLng(41.890527000, -87.800075000));
        coords.add(new LatLng(41.890611000, -87.800084000));
        coords.add(new LatLng(41.890672000, -87.800182000));
        coords.add(new LatLng(41.890695000, -87.800290000));
        coords.add(new LatLng(41.890694000, -87.800404000));
        coords.add(new LatLng(41.890703000, -87.800523000));
        coords.add(new LatLng(41.890702000, -87.800638000));
        coords.add(new LatLng(41.890697000, -87.800768000));
        coords.add(new LatLng(41.890689000, -87.800889000));
        coords.add(new LatLng(41.890692000, -87.801003000));
        coords.add(new LatLng(41.890641000, -87.801096000));
        coords.add(new LatLng(41.890680000, -87.801208000));
        coords.add(new LatLng(41.890685000, -87.801333000));
        coords.add(new LatLng(41.890677000, -87.801444000));
        coords.add(new LatLng(41.890681000, -87.801555000));
        coords.add(new LatLng(41.890688000, -87.801668000));
        coords.add(new LatLng(41.890679000, -87.801796000));
        coords.add(new LatLng(41.890669000, -87.801926000));
        coords.add(new LatLng(41.890673000, -87.802039000));
        coords.add(new LatLng(41.890660000, -87.802154000));
        coords.add(new LatLng(41.890668000, -87.802271000));
        coords.add(new LatLng(41.890661000, -87.802390000));
        coords.add(new LatLng(41.890655000, -87.802511000));
        coords.add(new LatLng(41.890690000, -87.802610000));
        coords.add(new LatLng(41.890776000, -87.802613000));
        coords.add(new LatLng(41.890860000, -87.802614000));
        coords.add(new LatLng(41.890942000, -87.802620000));
        coords.add(new LatLng(41.891026000, -87.802627000));
        coords.add(new LatLng(41.891110000, -87.802636000));
        coords.add(new LatLng(41.891195000, -87.802635000));
        coords.add(new LatLng(41.891276000, -87.802630000));
        coords.add(new LatLng(41.891364000, -87.802645000));
        coords.add(new LatLng(41.891451000, -87.802642000));
        coords.add(new LatLng(41.891542000, -87.802623000));
        coords.add(new LatLng(41.891634000, -87.802629000));
        coords.add(new LatLng(41.891717000, -87.802649000));
        coords.add(new LatLng(41.891804000, -87.802667000));
        coords.add(new LatLng(41.891886000, -87.802686000));
        coords.add(new LatLng(41.891968000, -87.802697000));
        coords.add(new LatLng(41.892054000, -87.802713000));
        coords.add(new LatLng(41.892139000, -87.802707000));
        coords.add(new LatLng(41.892224000, -87.802706000));
        coords.add(new LatLng(41.892316000, -87.802712000));
        coords.add(new LatLng(41.892407000, -87.802699000));
        coords.add(new LatLng(41.892497000, -87.802686000));
        coords.add(new LatLng(41.892594000, -87.802692000));
        coords.add(new LatLng(41.892683000, -87.802689000));
        coords.add(new LatLng(41.892770000, -87.802707000));
        coords.add(new LatLng(41.892858000, -87.802701000));
        coords.add(new LatLng(41.892945000, -87.802695000));
        coords.add(new LatLng(41.892984000, -87.802591000));
        coords.add(new LatLng(41.892980000, -87.802476000));
        coords.add(new LatLng(41.892997000, -87.802363000));
        coords.add(new LatLng(41.893006000, -87.802247000));
        coords.add(new LatLng(41.893010000, -87.802115000));
        coords.add(new LatLng(41.893013000, -87.801997000));
        coords.add(new LatLng(41.893028000, -87.801875000));
        coords.add(new LatLng(41.893022000, -87.801762000));
        coords.add(new LatLng(41.893023000, -87.801652000));
        coords.add(new LatLng(41.893028000, -87.801538000));
        coords.add(new LatLng(41.893045000, -87.801415000));
        coords.add(new LatLng(41.893088000, -87.801320000));
        coords.add(new LatLng(41.893174000, -87.801283000));
        coords.add(new LatLng(41.893198000, -87.801176000));
        coords.add(new LatLng(41.893209000, -87.801067000));
        coords.add(new LatLng(41.893209000, -87.800956000));
        coords.add(new LatLng(41.893216000, -87.800834000));
        coords.add(new LatLng(41.893223000, -87.800725000));
        coords.add(new LatLng(41.893222000, -87.800615000));
        coords.add(new LatLng(41.893229000, -87.800487000));
        coords.add(new LatLng(41.893254000, -87.800382000));
        coords.add(new LatLng(41.893339000, -87.800383000));
        coords.add(new LatLng(41.893424000, -87.800385000));
        coords.add(new LatLng(41.893517000, -87.800394000));
        coords.add(new LatLng(41.893598000, -87.800402000));
        coords.add(new LatLng(41.893690000, -87.800412000));
        coords.add(new LatLng(41.893778000, -87.800417000));
        coords.add(new LatLng(41.893862000, -87.800436000));
        coords.add(new LatLng(41.893950000, -87.800442000));
        coords.add(new LatLng(41.894038000, -87.800446000));
        coords.add(new LatLng(41.894127000, -87.800448000));
        coords.add(new LatLng(41.894214000, -87.800452000));
        coords.add(new LatLng(41.894213000, -87.800576000));
        coords.add(new LatLng(41.894211000, -87.800694000));
        coords.add(new LatLng(41.894223000, -87.800807000));
        coords.add(new LatLng(41.894259000, -87.800911000));
        coords.add(new LatLng(41.894338000, -87.800965000));
        coords.add(new LatLng(41.894411000, -87.801046000));
        coords.add(new LatLng(41.894407000, -87.801174000));
        coords.add(new LatLng(41.894393000, -87.801303000));
        coords.add(new LatLng(41.894384000, -87.801423000));
        coords.add(new LatLng(41.894378000, -87.801551000));
        coords.add(new LatLng(41.894370000, -87.801666000));
        coords.add(new LatLng(41.894371000, -87.801786000));
        coords.add(new LatLng(41.894451000, -87.801820000));
        coords.add(new LatLng(41.894544000, -87.801821000));
        coords.add(new LatLng(41.894624000, -87.801805000));
        coords.add(new LatLng(41.894718000, -87.801813000));
        coords.add(new LatLng(41.894810000, -87.801820000));
        coords.add(new LatLng(41.894894000, -87.801843000));
        coords.add(new LatLng(41.894978000, -87.801855000));
        coords.add(new LatLng(41.895063000, -87.801863000));
        coords.add(new LatLng(41.895150000, -87.801864000));
        coords.add(new LatLng(41.895242000, -87.801856000));
        coords.add(new LatLng(41.895329000, -87.801850000));
        coords.add(new LatLng(41.895417000, -87.801840000));
        coords.add(new LatLng(41.895506000, -87.801858000));
        coords.add(new LatLng(41.895592000, -87.801873000));
        coords.add(new LatLng(41.895686000, -87.801887000));
        coords.add(new LatLng(41.895776000, -87.801878000));
        coords.add(new LatLng(41.895870000, -87.801868000));
        coords.add(new LatLng(41.895966000, -87.801871000));
        coords.add(new LatLng(41.896058000, -87.801878000));
        coords.add(new LatLng(41.896152000, -87.801878000));
        coords.add(new LatLng(41.896235000, -87.801891000));
        coords.add(new LatLng(41.896323000, -87.801896000));
        coords.add(new LatLng(41.896409000, -87.801907000));
        coords.add(new LatLng(41.896499000, -87.801903000));
        coords.add(new LatLng(41.896580000, -87.801888000));
        coords.add(new LatLng(41.896674000, -87.801901000));
        coords.add(new LatLng(41.896755000, -87.801905000));
        coords.add(new LatLng(41.896841000, -87.801901000));
        coords.add(new LatLng(41.896929000, -87.801910000));
        coords.add(new LatLng(41.897021000, -87.801924000));
        coords.add(new LatLng(41.897107000, -87.801922000));
        coords.add(new LatLng(41.897189000, -87.801914000));
        coords.add(new LatLng(41.897277000, -87.801914000));
        coords.add(new LatLng(41.897365000, -87.801928000));
        coords.add(new LatLng(41.897451000, -87.801938000));
        coords.add(new LatLng(41.897539000, -87.801948000));
        coords.add(new LatLng(41.897626000, -87.801936000));
        coords.add(new LatLng(41.897713000, -87.801945000));
        coords.add(new LatLng(41.897798000, -87.801955000));
        coords.add(new LatLng(41.897856000, -87.801871000));
        coords.add(new LatLng(41.897870000, -87.801745000));
        coords.add(new LatLng(41.897867000, -87.801620000));
        coords.add(new LatLng(41.897886000, -87.801505000));
        coords.add(new LatLng(41.897882000, -87.801387000));
        coords.add(new LatLng(41.897880000, -87.801271000));
        coords.add(new LatLng(41.897890000, -87.801151000));
        coords.add(new LatLng(41.897902000, -87.801035000));
        coords.add(new LatLng(41.897914000, -87.800916000));
        coords.add(new LatLng(41.897913000, -87.800798000));
        coords.add(new LatLng(41.897824000, -87.800806000));
        coords.add(new LatLng(41.897740000, -87.800821000));
        coords.add(new LatLng(41.897647000, -87.800823000));
        coords.add(new LatLng(41.897555000, -87.800826000));
        coords.add(new LatLng(41.897470000, -87.800826000));
        coords.add(new LatLng(41.897378000, -87.800825000));
        coords.add(new LatLng(41.897296000, -87.800828000));
        coords.add(new LatLng(41.897201000, -87.800823000));
        coords.add(new LatLng(41.897114000, -87.800817000));
        coords.add(new LatLng(41.897029000, -87.800817000));
        coords.add(new LatLng(41.896942000, -87.800810000));
        coords.add(new LatLng(41.896846000, -87.800804000));
        coords.add(new LatLng(41.896756000, -87.800797000));
        coords.add(new LatLng(41.896666000, -87.800797000));
        coords.add(new LatLng(41.896582000, -87.800804000));
        coords.add(new LatLng(41.896486000, -87.800788000));
        coords.add(new LatLng(41.896398000, -87.800775000));
        coords.add(new LatLng(41.896315000, -87.800768000));
        coords.add(new LatLng(41.896233000, -87.800755000));
        coords.add(new LatLng(41.896151000, -87.800753000));
        coords.add(new LatLng(41.896061000, -87.800752000));
        coords.add(new LatLng(41.895970000, -87.800764000));
        coords.add(new LatLng(41.895881000, -87.800777000));
        coords.add(new LatLng(41.895798000, -87.800785000));
        coords.add(new LatLng(41.895710000, -87.800768000));
        coords.add(new LatLng(41.895617000, -87.800771000));
        coords.add(new LatLng(41.895531000, -87.800779000));
        coords.add(new LatLng(41.895445000, -87.800754000));
        coords.add(new LatLng(41.895361000, -87.800749000));
        coords.add(new LatLng(41.895275000, -87.800746000));
        coords.add(new LatLng(41.895191000, -87.800745000));
        coords.add(new LatLng(41.895097000, -87.800744000));
        coords.add(new LatLng(41.895014000, -87.800751000));
        coords.add(new LatLng(41.894929000, -87.800733000));
        coords.add(new LatLng(41.894843000, -87.800735000));
        coords.add(new LatLng(41.894757000, -87.800723000));
        coords.add(new LatLng(41.894663000, -87.800719000));
        coords.add(new LatLng(41.894575000, -87.800730000));
        coords.add(new LatLng(41.894486000, -87.800741000));
        coords.add(new LatLng(41.894404000, -87.800736000));
        coords.add(new LatLng(41.894320000, -87.800737000));
        coords.add(new LatLng(41.894226000, -87.800748000));
        coords.add(new LatLng(41.894156000, -87.800691000));
        coords.add(new LatLng(41.894189000, -87.800578000));
        coords.add(new LatLng(41.894192000, -87.800467000));
        coords.add(new LatLng(41.894108000, -87.800432000));
        coords.add(new LatLng(41.894022000, -87.800444000));
        coords.add(new LatLng(41.893933000, -87.800456000));
        coords.add(new LatLng(41.893844000, -87.800457000));
        coords.add(new LatLng(41.893757000, -87.800453000));
        coords.add(new LatLng(41.893675000, -87.800453000));
        coords.add(new LatLng(41.893589000, -87.800436000));
        coords.add(new LatLng(41.893501000, -87.800432000));
        coords.add(new LatLng(41.893415000, -87.800434000));
        coords.add(new LatLng(41.893326000, -87.800436000));
        coords.add(new LatLng(41.893240000, -87.800433000));
        coords.add(new LatLng(41.893154000, -87.800431000));
        coords.add(new LatLng(41.893071000, -87.800436000));
        coords.add(new LatLng(41.892984000, -87.800438000));
        coords.add(new LatLng(41.892891000, -87.800427000));
        coords.add(new LatLng(41.892799000, -87.800415000));
        coords.add(new LatLng(41.892715000, -87.800397000));
        coords.add(new LatLng(41.892631000, -87.800409000));
        coords.add(new LatLng(41.892547000, -87.800401000));
        coords.add(new LatLng(41.892458000, -87.800397000));
        coords.add(new LatLng(41.892375000, -87.800385000));
        coords.add(new LatLng(41.892282000, -87.800391000));
        coords.add(new LatLng(41.892198000, -87.800397000));
        coords.add(new LatLng(41.892117000, -87.800403000));
        coords.add(new LatLng(41.892031000, -87.800409000));
        coords.add(new LatLng(41.891942000, -87.800399000));
        coords.add(new LatLng(41.891857000, -87.800393000));
        coords.add(new LatLng(41.891770000, -87.800396000));
        coords.add(new LatLng(41.891689000, -87.800420000));
        coords.add(new LatLng(41.891602000, -87.800414000));
        coords.add(new LatLng(41.891521000, -87.800436000));
        coords.add(new LatLng(41.891430000, -87.800427000));
        coords.add(new LatLng(41.891343000, -87.800423000));
        coords.add(new LatLng(41.891266000, -87.800389000));
        coords.add(new LatLng(41.891182000, -87.800373000));
        coords.add(new LatLng(41.891094000, -87.800372000));
        coords.add(new LatLng(41.891002000, -87.800375000));
        coords.add(new LatLng(41.890920000, -87.800363000));
        coords.add(new LatLng(41.890835000, -87.800375000));
        coords.add(new LatLng(41.890752000, -87.800368000));
        coords.add(new LatLng(41.890694000, -87.800445000));
        coords.add(new LatLng(41.890606000, -87.800473000));
        coords.add(new LatLng(41.890518000, -87.800457000));
        coords.add(new LatLng(41.890436000, -87.800507000));
        coords.add(new LatLng(41.890351000, -87.800459000));
        coords.add(new LatLng(41.890264000, -87.800437000));
        coords.add(new LatLng(41.890174000, -87.800455000));
        coords.add(new LatLng(41.890092000, -87.800487000));
        coords.add(new LatLng(41.890011000, -87.800481000));
        coords.add(new LatLng(41.889919000, -87.800455000));
        coords.add(new LatLng(41.889834000, -87.800480000));
        coords.add(new LatLng(41.889753000, -87.800491000));
        coords.add(new LatLng(41.889668000, -87.800526000));
        coords.add(new LatLng(41.889621000, -87.800433000));
        coords.add(new LatLng(41.889608000, -87.800309000));
        coords.add(new LatLng(41.889517000, -87.800332000));
        coords.add(new LatLng(41.889424000, -87.800356000));
        coords.add(new LatLng(41.889338000, -87.800371000));
        coords.add(new LatLng(41.889251000, -87.800388000));
        coords.add(new LatLng(41.889159000, -87.800386000));
        coords.add(new LatLng(41.889073000, -87.800380000));
        coords.add(new LatLng(41.888987000, -87.800333000));
        coords.add(new LatLng(41.888901000, -87.800296000));
        coords.add(new LatLng(41.888816000, -87.800290000));
        coords.add(new LatLng(41.888726000, -87.800257000));
        coords.add(new LatLng(41.888642000, -87.800269000));
        coords.add(new LatLng(41.888557000, -87.800258000));
        coords.add(new LatLng(41.888482000, -87.800199000));
        coords.add(new LatLng(41.888453000, -87.800097000));
        coords.add(new LatLng(41.888368000, -87.800086000));
        coords.add(new LatLng(41.888287000, -87.800148000));
        coords.add(new LatLng(41.888204000, -87.800165000));
        coords.add(new LatLng(41.888114000, -87.800196000));
        coords.add(new LatLng(41.888029000, -87.800233000));
        coords.add(new LatLng(41.887947000, -87.800227000));
        coords.add(new LatLng(41.887858000, -87.800232000));
        coords.add(new LatLng(41.887794000, -87.800310000));
        coords.add(new LatLng(41.887771000, -87.800415000));
        coords.add(new LatLng(41.887738000, -87.800523000));
        coords.add(new LatLng(41.887640000, -87.800487000));
        coords.add(new LatLng(41.887621000, -87.800376000));
        coords.add(new LatLng(41.887540000, -87.800354000));
        coords.add(new LatLng(41.887621000, -87.800381000));
        coords.add(new LatLng(41.887705000, -87.800398000));
        coords.add(new LatLng(41.887710000, -87.800402000));

        return coords;
    }
}