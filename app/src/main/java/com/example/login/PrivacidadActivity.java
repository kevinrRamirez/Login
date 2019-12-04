package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class PrivacidadActivity extends AppCompatActivity {
    Button btnContinuar;
    RadioButton rBtnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacidad);

        btnContinuar = (Button) findViewById(R.id.btnContinuarPri);
        rBtnCheck = (RadioButton) findViewById(R.id.rBtnCheck);
    }


    public void continuar(View view)
    {
        if (rBtnCheck.isChecked())
        {
            Intent intent = new Intent(PrivacidadActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"hola 16465152bb",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(getApplicationContext(),"hola bb",Toast.LENGTH_LONG).show();
        }
    }
}
