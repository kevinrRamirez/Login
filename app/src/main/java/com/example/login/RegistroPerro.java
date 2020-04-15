package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    String nombrePerro,razaPerro,cuidadosPerro,edadPerro,tamPerro;

    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;

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
        progressDialog = new ProgressDialog(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

    }


    public void ctrlBtnRegistroPerro(View v) {
        nombrePerro=et_NombrePerro.getText().toString().trim();
        razaPerro=et_raza.getText().toString().trim();
        cuidadosPerro=et_cuidados.getText().toString().trim();
        edadPerro=spinnerEdad.getSelectedItem().toString().trim();
        tamPerro=spinnerTam.getSelectedItem().toString().trim();

        if (nombrePerro.isEmpty()||razaPerro.isEmpty()||cuidadosPerro.isEmpty()||edadPerro.equals("Edad:")||tamPerro.equals("Tamaño:")) {
            Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
            return;
        }
        if(!c.validacionPalabra(nombrePerro)){
            Toast.makeText(getApplicationContext(), "Ingresar nombre valido", Toast.LENGTH_LONG).show();
            return;
        }
        if(!c.validacionPalabra(razaPerro)){
            Toast.makeText(getApplicationContext(), "Ingresar raza valido", Toast.LENGTH_LONG).show();
            return;
        }
        if(!c.validacionCaracteresEspeciales(cuidadosPerro)){
            Toast.makeText(getApplicationContext(), "No usar "+c.caracteres+" en el campo de cuidados", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();
        //hacer el registro de datos
        Map<String, Object> mascota = new HashMap<>();
        mascota.put("id_usuario",firebaseUser.getUid());
        mascota.put("nombrePerro", nombrePerro);
        mascota.put("razaPerro", razaPerro);
        mascota.put("cuidadosPerro", cuidadosPerro);
        mascota.put("edadPerro", edadPerro);
        mascota.put("tamPerro", tamPerro);
        db.collection("mascotas")
                .add(mascota)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            Intent i= new Intent(getApplication(), PrivacidadActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

/*
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
                        loDelInsertMascota();


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


    public void loDelInsertMascota(){
        nombrePerro=et_NombrePerro.getText().toString();
        razaPerro=et_raza.getText().toString();
        cuidadosPerro=et_cuidados.getText().toString();
        edadPerro=spinnerEdad.getSelectedItem().toString();
        tamPerro=spinnerTam.getSelectedItem().toString();

        if(!c.hacerValidaciones){
            insertMascota(c.direccionIP+"registro_mascota.php");
            finish();
            Intent intent = new Intent(RegistroPerro.this, MainActivity.class);
            startActivity(intent);
        }else{
            if (nombrePerro.isEmpty()||razaPerro.isEmpty()||cuidadosPerro.isEmpty()||edadPerro.equals("Edad:")||tamPerro.equals("Tamaño:")){
                Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
            }else if(!c.validacionPalabra(nombrePerro)){
                Toast.makeText(getApplicationContext(), "Ingresar nombre valido", Toast.LENGTH_LONG).show();
            }else if(!c.validacionPalabra(razaPerro)){
                Toast.makeText(getApplicationContext(), "Ingresar raza valido", Toast.LENGTH_LONG).show();
            }else if(!c.validacionCaracteresEspeciales(cuidadosPerro)){
                Toast.makeText(getApplicationContext(), "No usar "+c.caracteres+" en el campo de cuidados", Toast.LENGTH_LONG).show();
            }else{
                insertMascota(c.direccionIP+"registro_mascota.php");
                finish();
                Intent intent = new Intent(RegistroPerro.this, PrivacidadActivity.class);
                startActivity(intent);
            }
        }
    }
*/
}
