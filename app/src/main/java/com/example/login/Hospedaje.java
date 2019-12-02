package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Hospedaje extends AppCompatActivity {

    public static final String nombre="names";
    TextView tv_construccion;
    Codigos c= new Codigos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedaje);
        tv_construccion = (TextView)findViewById(R.id.tv_construccion);

        Calendar calendar = Calendar.getInstance();
        String fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        tv_construccion.setText(fecha);
    }


    public void ctrlBtnAceptarC(View view) {
       finish();
    }
}
