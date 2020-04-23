package com.example.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

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
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OtraUbicacionActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    SupportMapFragment smp;
    SearchView searchView;
    TextView txtUbi;
    List<Address> addressList = null;
    List<Address> direccion;
    private Location location;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra_ubicacion);
        searchView = (SearchView) findViewById(R.id.sv_location);
        smp = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        txtUbi = (TextView) findViewById(R.id.txtUbicacion);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();


                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(OtraUbicacionActivity.this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                    Geocoder geocoder2 = new Geocoder(getApplicationContext(), Locale.getDefault());

                    try {
                        direccion = geocoder2.getFromLocation(address.getLatitude(), address.getLongitude(), 1);
                        txtUbi.setText(direccion.get(2).getAddressLine(2));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        smp.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
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
        LatLng ubicacion = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,17));
    }

    public double coorLat()
    {
        Address address = addressList.get(0);
        double Clat = address.getLatitude();

        return Clat;
    }

    public double coorLon()
    {
        Address address = addressList.get(0);
        double Clon = address.getLongitude();
        return Clon;

    }

    public void ctrlBtnAceptarSv(View view) {
        /*
        Intent intent;
        intent = new Intent(view.getContext(), NavigationPaseando.class);
        startActivity(intent);

         */
        finish();
        Toast toast = Toast.makeText(getApplicationContext(),
                "Buscando Paseaddr", Toast.LENGTH_LONG);
        toast.show();

    }


}
