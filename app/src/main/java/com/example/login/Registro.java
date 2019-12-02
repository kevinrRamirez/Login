package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCorreo;
    EditText txtPass;
    EditText txtPassConf;
    Button btnIngresar;
    TextView textView1;
    RequestQueue requestQueue;

    String nombre;
    String correo;
    String pass ;
    String passConf ;

    Codigos c = new Codigos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = (EditText) findViewById(R.id.txtCorreoP);
        txtCorreo = (EditText) findViewById(R.id.txtCorreoR);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtPassConf = (EditText) findViewById(R.id.txtPassConf);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        textView1 = (TextView) findViewById(R.id.tv_1);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*
    Método para ejecutar instrucciones y cambia de Activity cuando se hace click en el boton Ingresar
     */
    public void ButtonIngresar(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);/*Se recibe el contexto en el que se encuentra y la clase a la cual se desa ingresar*/
        startActivity(intent);/*Método para cambiar de Activity*/
    }


    public void ctrlBtnRegistroDuenio(View view) {
        nombre = txtNombre.getText().toString();
        correo = txtCorreo.getText().toString();
        pass = txtPass.getText().toString();
        passConf = txtPassConf.getText().toString();

        if(c.hacerValidaciones=true) {
            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || passConf.isEmpty()) {//todos los campos son requeridos
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
            } else if (!c.validacionNombreApellido(nombre)) {//no es Nombre Apellido
                Toast.makeText(this, "Ingresar Nombre y Apellido de forma correcta", Toast.LENGTH_LONG).show();
            } else if (!c.validacionCorreo(correo)) {
                Toast.makeText(this, "Correo electronico invalido", Toast.LENGTH_LONG).show();
            } else if (!(pass.length() >= 6)) {
                Toast.makeText(this, "Se requiere una contraseña mayor a 5 caracteres", Toast.LENGTH_LONG).show();
            } else if (!pass.equals(passConf)) {
                Toast.makeText(this, "Las contaseñas no coinciden", Toast.LENGTH_LONG).show();
            } else {
                //cumple con todas las validaciones
                servicio(c.direccionIP+"registro_duenio.php");
                finish();
                Intent intent = new Intent(Registro.this, RegistroPerro.class);
                intent.putExtra("variableCorreo",txtCorreo.getText().toString());
                startActivity(intent);
            }
        }else{
            servicio(c.direccionIP+"registro_duenio.php");
            finish();
            Intent intent = new Intent(Registro.this, RegistroPerro.class);
            intent.putExtra("variableCorreo",txtCorreo.getText().toString());
            startActivity(intent);
        }

    }

    public void insertDuenio(String url)
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
                parametros.put("nombre",nombre);//txtNombre.getText().toString()
                parametros.put("correo",correo);//txtCorreo.getText().toString()
                parametros.put("contrasenia",pass);//txtPass.getText().toString()
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void servicio(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Regsitro exitoso ", Toast.LENGTH_SHORT).show();
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
                parametros.put("nombre",txtNombre.getText().toString());
                parametros.put("correo",txtCorreo.getText().toString());
                parametros.put("contrasenia",txtPass.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}