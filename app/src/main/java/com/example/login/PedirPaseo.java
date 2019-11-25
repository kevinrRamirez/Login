package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PedirPaseo extends AppCompatActivity implements OnMapReadyCallback {
    Button btnubicacion;
    TextView txtUbicacion;
    GoogleMap mMap;
    MapView mapView;
    private double lat;
    private double lon;
    private Location location;
    private LocationManager locationManager;
    private  List<Address>  direccion;

    @Override
    protected void onResume() {

        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_paseo);
        txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);
        mapView = (MapView) findViewById(R.id.mapViewPP);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMyLocationEnabled(true);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
           direccion = geocoder.getFromLocation(lat,lon,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
       btnubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> direccion = geocoder.getFromLocation( location.getLatitude(), location.getLongitude(),1);
                    txtUbicacion.setText(direccion.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

         */





    }

    public void ctrlBtnAceptar(View view) {
        Intent intent;
        intent = new Intent(view.getContext(), NavigationPaseando.class);
        startActivity(intent);
    }


    public void ctrlBtnOtraUbi(View view)
    {
        Intent intent;
        intent = new Intent(view.getContext(), OtraUbicacionActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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

        LatLng ubicacion = new LatLng(lat, lon);
        //mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,15));

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> direccion = geocoder.getFromLocation( location.getLatitude(), location.getLongitude(),1);
            txtUbicacion.setText(direccion.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
