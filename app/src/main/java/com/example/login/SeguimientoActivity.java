package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SeguimientoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private SharedPreferences preferences;
    TextView textView;
    Button btnVerMapa;
    private GoogleMap mMap;
    private MapView mapView;
    private Location location;
    private LocationManager locationManager;
    private double lat;
    private double lon;
    private DatabaseReference databaseReference;


    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    FirebaseFirestore db;
    String str_idPaseo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        textView = (TextView) findViewById(R.id.textView);
        //btnVerMapa = (Button) findViewById(R.id.btnVerMapa);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        str_idPaseo = preferences.getString("str_idPaseo", null);
        String str_infoPaseo = preferences.getString("str_infoPaseo", null);
        String str_status = preferences.getString("str_status", null);
        if (str_idPaseo != null && str_infoPaseo != null && str_status != null) {
            textView.setText(str_idPaseo + "\n" + str_infoPaseo + "\n" + str_status + "\n");
        }
        /*
        if (str_status != null)
        {
            if(str_status.equals("0")||str_status.equals("1") ){
                btnVerMapa.setVisibility(View.INVISIBLE);
            }else if (str_status.equals("2")){
                btnVerMapa.setVisibility(View.VISIBLE);
            }
        }

         */

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        //inicio locattion
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//use GPS (mayor presicion)
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);//use Torrres y Wifi
        //event that is triggered whenever the update interval is met (1000)
        locationCallBack = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //save the location
                Location location = locationResult.getLastLocation();
                updateLatLon(location);
            }
        };
        //fin location
        //updateGPS();

        if (str_status.equals("2")){
            //activar el gps
            updateGPS();
            //variable para detectar los cambios de ubicacion
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        }

    }

    public void ctrlBtnAceptar(View view) {
        Intent intent = new Intent(getApplication(), NavigationPaseando.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
        MapsInitializer.initialize(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        lon = location.getLongitude();
        lat = location.getLatitude();

        // Add a marker in Sydney and move the camera
        final LatLng ubicacion = new LatLng(lat, lon);
        //mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,13));


    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



/*
    public void ctrlBtnVerMapa(View view){
        Toast.makeText(this, "Ver mapa", Toast.LENGTH_LONG).show();
    }

 */

    public void updateLatLon(Location location){
        //String str_idPaseo = "EUcfgCXaUhlGy0pyRm9Y";

        CollectionReference collectionReference = db.collection("paseos");
        collectionReference
                .whereEqualTo("id", str_idPaseo)
                .whereEqualTo("status", "2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String la = "",lo = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                la +=  document.get("latitudPaseador").toString();
                                lo +=  document.get("longitudPaseador").toString();
                                break;
                            }
                            Toast.makeText(getApplicationContext(), la+","+lo, Toast.LENGTH_LONG).show();
                            LatLng ubicacion = new LatLng(Double.valueOf(la),Double.valueOf(lo) );
                            mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));

                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        //Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
        //String aux ="EUcfgCXaUhlGy0pyRm9Y";

        /*
        LatLng ubicacion = new LatLng(location.getLongitude(), location.getLatitude());
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Paseador"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
*/
    }

    public void updateGPS(){
        //get permissions from the user ti track GPS
        //get current location from the fused client
        //update the UI - i.e. set all properties in their associated text view items
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //user provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //we got permissions. Put the values of location
                    //Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            //permission not granted yet
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_FINE_LOCATION);
            }
        }
    }
}
