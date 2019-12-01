package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Hospedaje extends AppCompatActivity {

    public static final String nombre="names";
    TextView tv_construccion;
    Codigos c= new Codigos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedaje);
        tv_construccion = (TextView)findViewById(R.id.tv_construccion);
        tv_construccion.setText("Hola"+c.getNombre());
    }


    public void ctrlBtnAceptarC(View view) {
        Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
        startActivity(intent);
    }
}
