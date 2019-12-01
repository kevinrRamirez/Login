package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Intent intent = new Intent(v.getContext(), NavigationPaseando.class);
        startActivity(intent);
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
