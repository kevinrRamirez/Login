package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Hospedaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedaje);
    }


    public void ctrlBtnAceptarC(View view) {
        Intent intent = new Intent(view.getContext(), NavigationPaseando.class);
        startActivity(intent);
    }
}
