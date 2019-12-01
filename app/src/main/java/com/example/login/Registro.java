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
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText txtNombre;
    EditText txtCorreo;
    EditText txtPass;
    EditText txtPassConf;
    Button btnIngresar;
    TextView textView1;

    Codigos c = new Codigos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = (EditText) findViewById(R.id.txtCorreoP);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
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



        /*

        String nombre = txtNombre.getText().toString();
        String correo = txtCorreo.getText().toString();
        String pass = txtPass.getText().toString();
        String passConf = txtPassConf.getText().toString();

        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || passConf.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
        } else if (validacionNombreApellido(nombre) && validacionCorreo(correo)) {
            if (!(pass.length() >= 6)) {
                Toast.makeText(this, "Se requiere una contraseña mayor a 5 caracteres", Toast.LENGTH_LONG).show();
            } else if (!pass.equals(passConf)) {
                Toast.makeText(this, "Las contaseñas no coinciden", Toast.LENGTH_LONG).show();
            }else{
                servicioRegistro(c.direccionIP+"registro_duenio.php");

                Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
                startActivity(intent);
            }
        }
         */

        servicioRegistro(c.direccionIP+"registro_duenio.php");
        Intent intent = new Intent(view.getContext(), RegistroPerro.class);
        startActivity(intent);


    }


    boolean validacionNombreApellido(String s) {
        boolean bandera = true;
        StringTokenizer t = new StringTokenizer(s, " ");
        if (t.countTokens() != 2) {
            Toast.makeText(this, "Ingresar Nombre y Apellido", Toast.LENGTH_LONG).show();
            return false;
        }
        while (t.hasMoreTokens()) {
            if (!validacionNombre(t.nextToken())) {
                Toast.makeText(this, "Ingresar Nombre y Apellido de forma correcta", Toast.LENGTH_LONG).show();
                bandera = false;
            }
        }
        return bandera;
    }

    boolean validacionNombre(String s) {
        boolean bandera = true;
        String ABC = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        String abc = "abcdefghijklmnñopqrstuvwxyz";
        if (!ABC.contains(Character.toString(s.charAt(0)))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!abc.contains(Character.toString(s.charAt(i)))) {
                return false;
            }
        }
        return bandera;
    }

    /*
    Método para hacer la validación de un coreo electronico
     */
    boolean validacionCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");/*expresión regular para el correo electronico*/
        Matcher mather = pattern.matcher(correo);
        if (mather.find()) { /*cuando cumple con la expresión regular regresa un valor bolleano true*/
            return true;
        } else { /*cuando no cumple con la expresión regular manda un Toast al usuario y regresa un valor bolleano false*/
            Toast.makeText(this, "Correo electronico invalido", Toast.LENGTH_LONG).show();
            return false;
        }
    }



    public void servicioRegistro(String url)
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

                parametros.put("nombre",txtNombre.getText().toString());
                parametros.put("correo",txtCorreo.getText().toString());
                parametros.put("contrasenia",txtPass.getText().toString());

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

                parametros.put("id",txtNombre.getText().toString());
                parametros.put("nombre",txtCorreo.getText().toString());
                parametros.put("apellido_pat",txtPass.getText().toString());
                parametros.put("apellido_mat",txtPassConf.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
