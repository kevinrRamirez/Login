package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
public class MainActivity extends AppCompatActivity {

    Codigos c= new Codigos();
    RequestQueue rq;
    JsonRequest jrq;

    RequestQueue requestQueue;
    private EditText txtCorreo;
    private EditText txtPass;
    TextView txtCorreoUs;
    TextView txtNombreUs;
    TextView textView1;
    TextView tv_registrate;
    NavigationPaseando navigationPaseando = new NavigationPaseando();
    String obtenerCorreo,obtenerPass;

    String id="";
    String idMas ;
    String idDue;
    String nombre="";
    String nombreMas;
    String paseo="";
    String contrasenia="";
    String corre="";
    String tamanio;
    String cuidados;
    String raza;
    String edad;
    String seguro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCorreo = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);
        textView1 = (TextView) findViewById(R.id.textView1);

        tv_registrate = (TextView) findViewById(R.id.tv_registrate);
        // Instantiate the RequestQueue.
        rq  = Volley.newRequestQueue(MainActivity.this);
        /*
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcherpaseando);
         */
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    public void ctrlBtnReg(View view) {
        Intent intent;
        intent = new Intent(MainActivity.this, Registro.class);
        startActivity(intent);
    }

    public void ctrlBtnIngresar(View view) {
        buscarDuenio(c.direccionIP+"buscar_duenio.php?correo="+txtCorreo.getText().toString()+"");
       // buscarMascota(c.direccionIP+"buscar_mascota.php?id_mascota=50");
    }
    public void loDelIntent(){
        String sCo,sPa;
        sCo=txtCorreo.getText().toString();
        sPa=txtPass.getText().toString();
        if(c.hacerValidaciones=false){
            if (sCo.isEmpty()||sPa.isEmpty()){
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
            }else if(!c.validacionCorreo(sCo)){
                Toast.makeText(this, "Correo invalido", Toast.LENGTH_LONG).show();
            }else if(!(sPa.length() >= 6)){
                Toast.makeText(this, "Se requiere una contraseña mayor a 5 caracteres", Toast.LENGTH_LONG).show();
            }else if(sPa.equals(contrasenia)){
                    Intent intent = new Intent(MainActivity.this, NavigationPaseando.class);
                    intent.putExtra("datoCorreo",corre);
                    intent.putExtra("datoNombre",nombre);
                    intent.putExtra("datoId",id);
                    intent.putExtra("datoContrasenia",contrasenia);
                    intent.putExtra("datoPaseo",paseo);
                    buscarMascota(c.direccionIP+"buscar_mascota.php?id_mascota="+id);
                    intent.putExtra("datoNomMas",nombreMas);
                    intent.putExtra("datoEdadMas",edad);
                    intent.putExtra("datoCuidados",cuidados);
                    startActivity(intent);
                    limpTextView();
            }
        }else if(sPa.equals(contrasenia)){
                Intent intent = new Intent(MainActivity.this, NavigationPaseando.class);
                intent.putExtra("datoCorreo",corre);
                intent.putExtra("datoNombre",nombre);
                intent.putExtra("datoId",id);
                intent.putExtra("datoContrasenia",contrasenia);
                intent.putExtra("datoPaseo",paseo);
                buscarMascota(c.direccionIP+"buscar_mascota.php?id_mascota="+id);
                intent.putExtra("datoNomMas",nombreMas);
                intent.putExtra("datoEdadMas",edad);
                intent.putExtra("datoCuidados",cuidados);;
                startActivity(intent);
                limpTextView();
        }
    }

    public void buscarDuenio(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        /*textView1.setText(jsonObject.getString("id_duenio"));
                        textView1.setText(jsonObject.getString("nombre"));
                        textView1.setText(jsonObject.getString("correo"));
                        textView1.setText(jsonObject.getString("contrasenia"));
                        textView1.setText(jsonObject.getString("paseo"));*/
                        //textView1.setText(jsonObject.getString("correo")+"--"+jsonObject.getString("contrasenia"));
                        //obtenerCorreo= jsonObject.optString("correo");
                        //obtenerPass=jsonObject.optString("contrasenia");
                        //s=jsonObject.getString("id_duenio")+jsonObject.getString("nombre")+jsonObject.getString("correo")+jsonObject.getString("contrasenia")+jsonObject.getString("paseo");
                        //textView1.setText(s);
                        id = jsonObject.getString("id_duenio");
                        nombre = jsonObject.getString("nombre");
                        corre = jsonObject.getString("correo");
                        contrasenia = jsonObject.getString("contrasenia");
                        paseo = jsonObject.getString("paseo");
                        loDelIntent();
                        //Toast.makeText(getApplicationContext(), "Iniciando...", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Vuelve a intentarlo xd", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void buscarMascota(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        idMas = jsonObject.getString("id_mascota");
                        idDue = jsonObject.getString("id_duenio");
                        nombreMas = jsonObject.getString("nombre_mascota");
                        tamanio = jsonObject.getString("tamanio");
                        cuidados = jsonObject.getString("cuidados");
                        raza = jsonObject.getString("raza");
                        edad = jsonObject.getString("edad");
                        seguro = jsonObject.getString("id_seguro");
                        loDelIntent();
                        //Toast.makeText(getApplicationContext(), "Iniciando...", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Vuelve a intentarlo xd x2", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



        /*
    private void iniciarSesion(){
        String url = c.direccionIP+"buscar_duenio%20_prb.php?correo="+txtCorreo.getText().toString()+"&contrasenia="+txtPass.getText().toString();
        //String url = c.direccionIP+"buscar_duenio.php?correo="+txtCorreo.getText().toString()+"&contrasenia="+txtPass.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error de conexión xd", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onResponse(JSONObject response) {
        Codigos c1= new Codigos();
        Toast.makeText(this, "Conexión xd", Toast.LENGTH_LONG).show();
        JSONArray jsonArray = response.optJSONArray("array");
        JSONObject jsonObject =null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            c1.setId(jsonObject.optString("id_duenio"));
            c1.setNombre(jsonObject.optString("nombre"));
            c1.setCorreo(jsonObject.optString("correo"));
            c1.setPass(jsonObject.optString("contrasenia"));
            c1.setPaseo(jsonObject.optString("paseo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textView1.setText(c.getCorreo()+c.getPass()+c.getPaseo());
        Intent intent = new Intent(MainActivity.this, NavigationPaseando.class);
        intent.putExtra(NavigationPaseando.nombre,c1.getNombre());
        startActivity(intent);
        limpTextView();
    }
*/

    public void limpTextView() {
        txtCorreo = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtCorreo.setText("");
        txtPass.setText("");
    }

}
