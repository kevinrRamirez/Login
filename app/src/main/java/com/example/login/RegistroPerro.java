package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistroPerro extends AppCompatActivity {

    Codigos c= new Codigos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perro);
    }


    public void ctrlBtnRegistroPerro(View v) {
        Intent intent = new Intent(v.getContext(), NavigationPaseando.class);
        startActivity(intent);
    }

}
