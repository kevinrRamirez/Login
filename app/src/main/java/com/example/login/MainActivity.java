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
import com.example.login.ui.Codigos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    Codigos c= new Codigos();
    RequestQueue rq;
    JsonRequest jrq;

    RequestQueue requestQueue;
    private EditText txtCorreo;
    private EditText txtPass;
    TextView txtCorreoUs;
    TextView txtNombreUs;
    TextView textView1;
    NavigationPaseando navigationPaseando = new NavigationPaseando();
    String obtenerCorreo,obtenerPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtCorreo = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);
        textView1 = (TextView) findViewById(R.id.textView1);
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
    private void iniciarSesion(){
        String url = c.direccionIP+"buscar_duenio_prb.php?correo="+txtCorreo.getText().toString()+"&contrasenia="+txtPass.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error de conexión xd", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Conexión xd", Toast.LENGTH_LONG).show();
        JSONArray jsonArray = response.optJSONArray("array");
        JSONObject jsonObject =null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            c.setId(jsonObject.optString("id_duenio"));
            c.setNombre(jsonObject.optString("nombre"));
            c.setCorreo(jsonObject.optString("correo"));
            c.setPass(jsonObject.optString("contrasenia"));
            c.setPaseo(jsonObject.optString("paseo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void ctrlBtnReg(View view) {
        Intent intent;
        intent = new Intent(MainActivity.this, Registro.class);
        startActivity(intent);
    }

    public void ctrlBtnIngresar(View view) {

        /*
        txtCorreo = (TextView) findViewById(R.id.txtUsuario);
        txtPass = (TextView) findViewById(R.id.txtPass);
        String correo = txtCorreo.getText().toString();
        String pass = txtPass.getText().toString();
        if (correo.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } else if (!validacionCorreo(correo)) {
            Toast.makeText(this, "Correo electronico invalido", Toast.LENGTH_LONG).show();
        } else if (!(pass.length() >= 6)) {
            Toast.makeText(this, "Se requiere una contraseña mayor a 5 caracteres", Toast.LENGTH_LONG).show();
        } else {

            Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
            startActivity(intent);
            limpTextView();
        }

         */


        /*
        buscarDuenio(c.direccionIP+"buscar_duenio.php?correo="+txtCorreo.getText().toString()+"");
        textView1.setText(obtenerCorreo+"--"+obtenerPass);

        if (!txtCorreo.getText().equals(obtenerCorreo)&&!txtPass.getText().equals(obtenerPass)){
            Toast.makeText(this, "Sin coincidencia", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
            startActivity(intent);
            limpTextView();
        }

         */



        //buscarDuenio(c.direccionIP+"buscar_duenio.php?correo="+txtCorreo.getText().toString()+"");
        //textView1.setText(obtenerCorreo+"--"+obtenerPass);

        iniciarSesion();


    }



    public void limpTextView() {
        txtCorreo = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtCorreo.setText("");
        txtPass.setText("");
    }

    boolean validacionCorreo(String s) {
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(s);
        if (mather.find()) {
            return true;
        } else {
            Toast.makeText(this, "Correo electronico invalido", Toast.LENGTH_LONG).show();
            return false;
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

/*
                        textView1.setText(jsonObject.getString("id_duenio"));
                        textView1.setText(jsonObject.getString("nombre"));
                        textView1.setText(jsonObject.getString("correo"));
                        textView1.setText(jsonObject.getString("contrasenia"));
                        textView1.setText(jsonObject.getString("paseo"));
 */
                        //textView1.setText(jsonObject.getString("correo")+"--"+jsonObject.getString("contrasenia"));
                        obtenerCorreo= jsonObject.optString("correo");
                        obtenerPass=jsonObject.optString("contrasenia");

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

    public void servicio(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Regsitro exitoso", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el registro -> "+error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                /*

                parametros.put("id",txtNombre.getText().toString());
                parametros.put("nombre",txtCorreo.getText().toString());
                parametros.put("apellido_pat",txtPass.getText().toString());
                parametros.put("apellido_mat",txtPassConf.getText().toString());

                 */

                return parametros;
            }
        };
         requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
