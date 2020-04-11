package com.example.login;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    String str_title = "";
    String str_message = "";

    public Dialog(String str_title, String str_message) {
        this.str_title = str_title;
        this.str_message = str_message;
    }

    public Dialog() {
        this.str_title = str_title;
        this.str_message = str_message;
    }


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(str_title)
                .setMessage(str_message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
