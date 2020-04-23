package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SeguimientoActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    TextView textView;
    Button btnVerMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        textView = (TextView) findViewById(R.id.textView);
        btnVerMapa = (Button) findViewById(R.id.btnVerMapa);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        String str_idPaseo = preferences.getString("str_idPaseo", null);
        String str_infoPaseo = preferences.getString("str_infoPaseo", null);
        String str_status = preferences.getString("str_status", null);
        if (str_idPaseo != null && str_infoPaseo != null && str_status != null)
        {
            textView.setText(str_idPaseo+"\n"+str_infoPaseo+"\n"+str_status+"\n");
        }
        if (str_status != null)
        {
            if(str_status.equals("0")||str_status.equals("1") ){
                btnVerMapa.setVisibility(View.INVISIBLE);
            }else if (str_status.equals("2")){
                btnVerMapa.setVisibility(View.VISIBLE);
            }
        }
    }

    public void ctrlBtnAceptar(View view){
        Intent intent = new Intent(getApplication(), NavigationPaseando.class);
        startActivity(intent);
    }

    public void ctrlBtnVerMapa(View view){
        Toast.makeText(this, "Ver mapa", Toast.LENGTH_LONG).show();
    }
}
