package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView txtCorreo;
    TextView txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcherpaseando);

         */
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


         */
        Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
        startActivity(intent);
        limpTextView();

    }

    public void limpTextView() {
        txtCorreo = (TextView) findViewById(R.id.txtUsuario);
        txtPass = (TextView) findViewById(R.id.txtPass);
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

}
