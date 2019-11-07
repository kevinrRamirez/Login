package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity  {

    EditText txtNombre;
    EditText txtCorreo;
    EditText txtPass;
    EditText txtPassConf ;
    Button btnIngresar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtPass = (EditText)findViewById(R.id.txtPass);
        txtPassConf = (EditText)findViewById(R.id.txtPassConf);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        //btnIngresar.setOnClickListener(this);
    }


/*
       @Override
    public void onClick(View v) {
        final String nombre = txtNombre.getText().toString();
        final String correo = txtCorreo.getText().toString();
        final String pass = txtPass.getText().toString();
        final String passConf = txtPassConf.getText().toString();

        Intent intent = new Intent (v.getContext(),NavigationPaseando.class);
        startActivity(intent);
    }
*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void ctrlBoton(View view) {

        Intent intent = null;

        switch (view.getId())
        {
            case R.id.btnRegistro:
                intent = new Intent(Registro.this, NavigationPaseando.class);
                break;
            case R.id.tv_ingresar:
                intent = new Intent(Registro.this, MainActivity.class);
                break;

        }
        startActivity(intent);

    }
}
