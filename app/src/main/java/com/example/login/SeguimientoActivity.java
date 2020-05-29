package com.example.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        textView = (TextView) findViewById(R.id.textView);
        //btnVerMapa = (Button) findViewById(R.id.btnVerMapa);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        String str_idPaseo = preferences.getString("str_idPaseo", null);
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
}
