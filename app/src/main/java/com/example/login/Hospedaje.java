package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Hospedaje extends AppCompatActivity {

    public static final String nombre="names";
    TextView tv_construccion;
    Codigos c= new Codigos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedaje);
        tv_construccion = (TextView)findViewById(R.id.tv_construccion);

        //Calendar calendar = Calendar.getInstance();
        //String fecha = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());//December 1,2019
        //String fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());//12/1/19
        //String fecha = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());//December 1,2019

        //String fecha1=new Date().toString();//Sun Dec 01 22:11:59 CST 2019

        //tv_construccion.setText();
    }


    public void ctrlBtnAceptarC(View view) {finish();}
}
