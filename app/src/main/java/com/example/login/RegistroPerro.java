package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
    EditText et_NombrePerro,et_raza,et_cuidados;
    Spinner spinnerEdad,spinnerTam;
    RequestQueue requestQueue;
    String variableCorreo;
    String idDuenio;
    String nombreDuenio;
    String correoDuenio;
    String extrasDuenio;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perro);
        tv_datosPerro = (TextView) findViewById(R.id.tv_datosPerro);

        et_NombrePerro = (EditText)findViewById(R.id.et_NombrePerro);
        et_raza = (EditText)findViewById(R.id.et_raza);
        et_cuidados = (EditText)findViewById(R.id.et_cuidados);


        spinnerEdad=(Spinner)findViewById(R.id.spinnerEdad);
        String [] arrayEdad ={"Edad:","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        ArrayAdapter <String> arrayAdapterEdad = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item,arrayEdad);
        spinnerEdad.setAdapter(arrayAdapterEdad);

        spinnerTam=(Spinner)findViewById(R.id.spinnerTam);
        String [] arrayTam ={"Tamaño:","Pequeño","Mediano","Grande","Gigante"};
        ArrayAdapter <String> arrayAdapterTam = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item,arrayTam);
        spinnerTam.setAdapter(arrayAdapterTam);

        Bundle dato = getIntent().getExtras();
        variableCorreo = dato.getString("variableCorreo");


        //selectDuenio(c.direccionIP+"buscar_duenio.php?correo="+variableCorreo+"");
        url = c.direccionIP+"buscar_duenio.php?correo="+variableCorreo+"";

    }


    public void ctrlBtnRegistroPerro(View v) {
        selectDuenio(c.direccionIP+"buscar_duenio.php?correo="+variableCorreo+"");
    }

    public void insertMascota(String url)
    {
        selectDuenio(url);
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
                parametros.put("id_duenio",idDuenio);//
                parametros.put("nombre_mascota",et_NombrePerro.getText().toString());//
                parametros.put("tamanio",spinnerTam.getSelectedItem().toString());//
                parametros.put("cuidados",et_cuidados.getText().toString());//
                parametros.put("raza",et_raza.getText().toString());//
                parametros.put("edad",spinnerEdad.getSelectedItem().toString());//
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
                        idDuenio = jsonObject.getString("id_duenio");
                        nombreDuenio = jsonObject.getString("nombre");
                        correoDuenio = jsonObject.getString("correo");
                        extrasDuenio = jsonObject.getString("contrasenia")+ jsonObject.getString("paseo");

                        insertMascota(c.direccionIP+"registro_mascota.php");
                        finish();
                        Intent intent = new Intent(RegistroPerro.this, MainActivity.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Listo xd ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
