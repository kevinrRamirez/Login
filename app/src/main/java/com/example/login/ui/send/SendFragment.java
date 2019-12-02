package com.example.login.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.Codigos;
import com.example.login.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    //RequestQueue requestQueue;
    TextView txtCorre;
    TextView txtnombre;
    TextView txt1;
    TextView direc;
    //Codigos c;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


         */
        Bundle dato = getActivity().getIntent().getExtras();
        String correo = dato.getString("datoCorreo");
        String nombre = dato.getString("datoNombre");
        String paseo = dato.getString("datoPaseo");
        String dirccion = dato.getString("datoDireccion");

        //String url = c.direccionIP+"buscar_duenio.php?correo="+correo+"";

        txtCorre = (TextView) root.findViewById(R.id.txtCorreoP);
        txtCorre.setText(correo);
        txtnombre = (TextView) root.findViewById(R.id.txtNombreUsu);
        txtnombre.setText(nombre);
        txt1 = (TextView) root.findViewById(R.id.txtPrb1);
        txt1.setText(paseo);
        direc = (TextView) root.findViewById(R.id.txtDireccionP);
        direc.setText(dirccion);


        //bDuenioNavi(url,txtCorre,txtnombre,txt1,txt2);
        return root;
    }

/*
    public void bDuenioNavi(String URL, final TextView txtCorreoUs, final TextView txtNombreUs,
                            final TextView txtPrbContra, final TextView txtPrbPaseo) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        txtNombreUs.setText(jsonObject.getString("id_duenio") + jsonObject.getString("nombre"));
                        txtCorreoUs.setText(jsonObject.getString("correo"));
                        txtPrbContra.setText(jsonObject.getString("contrasenia"));
                        txtPrbPaseo.setText(jsonObject.getString("paseo"));

                        Toast.makeText(getActivity().getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión xd", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

 */
}