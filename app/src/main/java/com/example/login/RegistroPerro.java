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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroPerro extends AppCompatActivity {

    Codigos c= new Codigos();
    String variableId;
    TextView tv_datosPerro;
    RequestQueue requestQueue;
    String variableCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perro);
        variableId = getIntent().getStringExtra("variableId");
        tv_datosPerro = (TextView) findViewById(R.id.tv_datosPerro);
        variableCorreo = getIntent().getStringExtra("variableCorreo");
        tv_datosPerro.setText(variableCorreo+"xd");

        //selectDuenio(c.direccionIP+"buscar_duenio.php?correo="+variableCorreo+"");

    }


    public void ctrlBtnRegistroPerro(View v) {
        insertMascota(c.direccionIP+"registro_mascota.php");
        Intent intent = new Intent(v.getContext(), NavigationPaseando.class);
        startActivity(intent);
    }

    public void insertMascota(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Regsitro exitoso xd", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el registro xd -> "+error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_duenio","13");//
                parametros.put("nombre_mascota","firu");//
                parametros.put("peso","0."+"10");//
                parametros.put("cuidados","no");//
                parametros.put("raza","pitbull");//
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void selectDuenio(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        c.setId(jsonObject.getString("id_duenio"));
                        c.setNombre(jsonObject.getString("nombre"));
                        c.setCorreo(jsonObject.getString("correo"));
                        c.setPass(jsonObject.getString("contrasenia"));
                        c.setPaseo(jsonObject.getString("paseo"));
                        //if (!c.getId().equals("")){
                        Intent intent = new Intent(RegistroPerro.this, NavigationPaseando.class);
                        //intent.putExtra("variableId",c.getId());
                        startActivity(intent);
                        //}
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión xd", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
