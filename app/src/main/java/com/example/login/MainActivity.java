package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /*
    ahorita vemos que pedo
    pablooooo
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcherpaseando);

         */
    }

    public void ctrlBoton(View view)
    {
        Intent intent = null;

        switch (view.getId())
        {
            case R.id.btnIngresar:
                intent = new Intent(MainActivity.this, NavigationPaseando.class);
                break;
            case R.id.btnRegistro:
            //case R.id.tv_registrate:
                intent = new Intent(MainActivity.this, Registro.class);
                break;


        }
        startActivity(intent);

    }

}
