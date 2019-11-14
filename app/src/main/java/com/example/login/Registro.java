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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtPassConf = (EditText) findViewById(R.id.txtPassConf);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void ctrlBotonIngresar(View view) {
        Intent intent = null;
        intent = new Intent(Registro.this, MainActivity.class);
        startActivity(intent);
    }


    public void ctrlBtnRegistrar(View view) {
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
                Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
                startActivity(intent);
            }
        }

         */
        servicio("http://192.168.209.23/prueba/insertar_preba.php");

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
