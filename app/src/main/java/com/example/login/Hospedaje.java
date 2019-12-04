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
import java.util.StringTokenizer;

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


    public void ctrlBtnAceptarC(View view) {
        //sacarHoras();
        finish();
    }

/*
    String horaIni,horaFin,tiempo="120";
    public void sacarHoras(){
        horaIni=new Date().toString();
        String hrs,min;
        StringTokenizer t = new StringTokenizer(horaIni, " ");
        t.nextToken();
        t.nextToken();
        t.nextToken();
        horaIni=t.nextToken();
        StringTokenizer t1 = new StringTokenizer(horaIni, ":");
        hrs=t1.nextToken();
        min=t1.nextToken();
        hrs=sumaHoras(hrs,"1");
        horaIni=hrs+":"+min;
        if(tiempo.equals("30")){
            int x1,x2;
            x1=Integer.parseInt(min);
            x2=Integer.parseInt("30");
            x1+=x2;
            if(x1>=60){
                x1-=60;
                hrs=sumaHoras(hrs,"1");
            }
            horaFin=hrs+":"+x1;
        }else if(tiempo.equals("60")){
            hrs=sumaHoras(hrs,"1");
            horaFin=hrs+":"+min;
        }else if(tiempo.equals("120")){
            hrs=sumaHoras(hrs,"2");
            horaFin=hrs+":"+min;
        }
    }
    public String sumaMinutos(String m1,String m2){
        int x1=Integer.parseInt(m1);
        int x2=Integer.parseInt(m2);
        x1+=x2;
        if(x1>=60){
            x1-=60;
        }
        return Integer.toString(x1);
    }
    public String sumaHoras(String h1,String h2){
        int x1=Integer.parseInt(h1);
        int x2=Integer.parseInt(h2);
        x1+=x2;
        if(x1>=24){
            x1-=23;
        }
        return Integer.toString(x1);
    }*/
}
