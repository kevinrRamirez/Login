package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView txtCorreo;
    TextView txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcherpaseando);

         */
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
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
        } else {
            // Permission has already been granted
        }
    }

    public void ctrlBtnReg(View view) {
        Intent intent;
                intent = new Intent(MainActivity.this, Registro.class);
            startActivity(intent);
    }
    public void ctrlBtnIngresar(View view) {

        /*
        txtCorreo = (TextView) findViewById(R.id.txtUsuario);
        txtPass = (TextView) findViewById(R.id.txtPass);
        String correo = txtCorreo.getText().toString();
        String pass = txtPass.getText().toString();
        if (correo.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } else if (!validacionCorreo(correo)) {
            Toast.makeText(this, "Correo electronico invalido", Toast.LENGTH_LONG).show();
        } else if (!(pass.length() >= 6)) {
            Toast.makeText(this, "Se requiere una contraseña mayor a 5 caracteres", Toast.LENGTH_LONG).show();
        } else {

            Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
            startActivity(intent);
            limpTextView();
        }

         */

        /*


         */
        Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
        startActivity(intent);
        limpTextView();

    }

    public void limpTextView() {
        txtCorreo = (TextView) findViewById(R.id.txtUsuario);
        txtPass = (TextView) findViewById(R.id.txtPass);
        txtCorreo.setText("");
        txtPass.setText("");
    }

    boolean validacionCorreo(String s) {
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(s);
        if (mather.find()) {
            return true;
        } else {
            Toast.makeText(this, "Correo electronico invalido", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
