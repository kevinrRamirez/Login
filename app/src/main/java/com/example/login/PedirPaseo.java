package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PedirPaseo extends AppCompatActivity {
    Button btnubicacion;
    TextView txtUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_paseo);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
     btnubicacion = (Button) findViewById(R.id.btnAccUbica);
     txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);

     btnubicacion.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             LocationManager locationManager = (LocationManager) PedirPaseo.this.getSystemService(Context.LOCATION_SERVICE);

             LocationListener locationListener = new LocationListener()
             {

                 @Override
                 public void onLocationChanged(Location location) {
                     txtUbicacion.setText(""+location.getLatitude()+" "+ location.getLongitude());

                 }

                 @Override
                 public void onStatusChanged(String provider, int status, Bundle extras) {

                 }

                 @Override
                 public void onProviderEnabled(String provider) {

                 }

                 @Override
                 public void onProviderDisabled(String provider) {

                 }
             };

             int permissionCheck = ContextCompat.checkSelfPermission(PedirPaseo.this,
                     Manifest.permission.ACCESS_FINE_LOCATION);

             locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


         }
     });


        if (permissionCheck == PackageManager.PERMISSION_DENIED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }
}
