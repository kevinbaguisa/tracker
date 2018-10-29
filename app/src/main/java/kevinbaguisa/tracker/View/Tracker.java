package kevinbaguisa.tracker.View;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import kevinbaguisa.tracker.Controller.ClientInstance;
import kevinbaguisa.tracker.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import java.util.Timer;
import java.util.TimerTask;

public class Tracker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button getLocBtn;
    ClientInstance mkClient;
    private FusedLocationProviderClient client;
    LatLng currLatLng;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocBtn = (Button) findViewById(R.id.btnGetLoc);
        client = LocationServices.getFusedLocationProviderClient(this);

        requestPermission();

        getLocBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public void startTimer(){
        Timer myTimer = new Timer();

        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(Tracker.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return; }client.getLastLocation().addOnSuccessListener(Tracker.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!= null) {
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                                currLatLng = new LatLng(lat, lng);
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(currLatLng).title("Your current location."));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLatLng, 6));
                                Toast.makeText(Tracker.this, "You are here: " + location.getLatitude() + ", " + location.getLongitude(),
                                        Toast.LENGTH_LONG).show();

                                postToDB();
                            }
                        }
                    });
                Log.i("Tracker", "Repeated task");}

                },0,5000);
    }

    public void postToDB(){
        JSONObject obj = new JSONObject();

//        String coord = lat + ", " + lng;
        String bus_id = "testID321";
        Call<ResponseBody> call = mkClient.getInstance().getAPI().updateCoordinates(lat, lng, bus_id);
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(MapsActivity.this, "Successfully added location coordinates", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.toString());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}