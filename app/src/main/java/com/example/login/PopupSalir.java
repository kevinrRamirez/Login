package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class PopupSalir extends AppCompatActivity {

    Button btn_cancelar;
    Button btn_cerrarSesion;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_salir);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
         int alto = displayMetrics.heightPixels;
         int ancho = displayMetrics.widthPixels;

         getWindow().setLayout((int)(ancho * 0.8), (int)(alto * 0.5));
         btn_cancelar = findViewById(R.id.btnCancelar);
         btn_cerrarSesion = findViewById(R.id.btnCerrarSesion);

         btn_cancelar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

         btn_cerrarSesion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cerrarSesion();
                 onDestroy();
             }
         });

    }

    private void cerrarSesion()
    {
        preferences.edit().clear().commit();
        finish();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);

    }
}
