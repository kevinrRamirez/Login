package com.example.login;//comentario

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
public class MainActivity extends AppCompatActivity {

    Codigos c= new Codigos();
    RequestQueue rq;
    JsonRequest jrq;

    RequestQueue requestQueue;
    private EditText txtCorreo;
    private EditText txtPass;
    TextView textView1;
    TextView tv_registrate;
    NavigationPaseando navigationPaseando = new NavigationPaseando();
    String obtenerCorreo,obtenerPass;

    String id="";
    String idMas ;
    String idDue;
    String nombre="";
    String nombreMas;
    String paseo="";
    String contrasenia="";
    String corre;
    String tamanio;
    String cuidados;
    String raza;
    String edad;
    String seguro;
    String sCo,sPa;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCorreo = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);
        textView1 = (TextView) findViewById(R.id.textView1);
        txtCorreo.setText("");
        txtPass.setText("");

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        tv_registrate = (TextView) findViewById(R.id.tv_registrate);
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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        validaSesion();
    }

    public void ctrlBtnReg(View view) {
        Intent intent = new Intent(getApplication(), Registro.class);
        startActivity(intent);
    }

    public void ctrlBtnIngresar(View view) {
        final String str_correo = txtCorreo.getText().toString().trim();
        String str_contrasena = txtPass.getText().toString().trim();


        //obtenemos los valores de los EditText
        Codigos c = new Codigos ();
        boolean vacios = false;
        //validamos que no esten vacias
        if (TextUtils.isEmpty(str_correo)){
            txtCorreo.setError("Requerido");
            vacios=true;
        }
        if (TextUtils.isEmpty(str_contrasena)){
            txtPass.setError("Requerido");
            vacios=true;
        }
        if (vacios){
            return;
        }
        if (!c.validacionCorreo(str_correo)){
            txtCorreo.setError("Inválido");
            return;
        }
        if (str_contrasena.length()<6){
            txtPass.setError("Mínimo 6 caracteres");
            return;
        }
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        //creacion del nuevo usuario
        mAuth.signInWithEmailAndPassword(str_correo, str_contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            if (mAuth.getCurrentUser().isEmailVerified()){
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                Query query = db.collection("usuarios").whereEqualTo("id", firebaseUser.getUid());
                                query
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    //Toast.makeText(getApplicationContext(), "Exito", Toast.LENGTH_LONG).show();
                                                    String nombre = "";
                                                    String correo = "";
                                                    String name = "";
                                                    String email = "";
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        nombre = document.get("nombre").toString();
                                                         name = document.get("nombre").toString();
                                                        correo = document.get("correo").toString();
                                                         email= document.get("correo").toString();
                                                    }
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplication(),"Bienvenido "+nombre,Toast.LENGTH_LONG).show();

                                                    Intent intent = new Intent(getApplication(), NavigationPaseando.class);
                                                    intent.putExtra("datoNombre",nombre);
                                                    intent.putExtra("datoCorreo",correo);
                                                    startActivity(intent);

                                                    //Intent intent = new Intent(getApplication(), NavigationPaseando.class);
                                                    preferencias(email,name);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,"Correo sin verificar",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void ctrlReestablecerPass(View view)
    {
        //obtenemos los valores de los EditText
        final String str_correo = txtCorreo.getText().toString().trim();
        Codigos c = new Codigos ();
        if (TextUtils.isEmpty(str_correo)){
            txtCorreo.setError("Requerido");
            return;
        }
        if (!c.validacionCorreo(str_correo)){
            Toast.makeText(getApplicationContext(), "Correo invalido", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(str_correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Dialog dialog = new Dialog("Importante","Se te ha enviado un correo para reestablecer la contraseña");
                    dialog.show(getSupportFragmentManager(),"");
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    String str = "";
    public  void selectDatosUsuario(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = db.collection("usuarios").whereEqualTo("id", firebaseUser.getUid());
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(), "Exito", Toast.LENGTH_LONG).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                str
                                        +=  "Nombre: \t" + document.get("nombre").toString()+ "\n"
                                        +  "Correo: \t" + document.get("correo").toString()+ "\n";
                            }
                            progressDialog.dismiss();
                            Toast.makeText(getApplication(),"Bienvenido "+str,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), NavigationPaseando.class);
                            intent.putExtra(NavigationPaseando.nombre,"Esta linea no es necesaria, pero no la he podido quitar");
                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void preferencias(String str_cor, String str_nom)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo_",str_cor);
        editor.putString("nombre_",str_nom);
        editor.commit();
    }

    public void loDelIntent(){
                Intent intent = new Intent(MainActivity.this, NavigationPaseando.class);
                intent.putExtra("datoCorreo", sCo);
                intent.putExtra("datoNombre", nombre);
                intent.putExtra("datoId", id);
                intent.putExtra("datoContrasenia", contrasenia);
                intent.putExtra("datoPaseo", paseo);
                //buscarMascota(c.direccionIP + "buscar_mascota.php?id_mascota=" + id);
                intent.putExtra("datoNomMas", nombreMas);
                intent.putExtra("datoEdadMas", edad);
                intent.putExtra("datoCuidados", cuidados);
                startActivity(intent);
                limpTextView();
    }

    public void buscarDuenio(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id = jsonObject.getString("id_duenio");
                        nombre = jsonObject.getString("nombre");
                        corre = jsonObject.getString("correo");
                        contrasenia = jsonObject.getString("contrasenia");
                        paseo = jsonObject.getString("paseo");

                        sCo=txtCorreo.getText().toString();
                        sPa=txtPass.getText().toString();
                        if(c.hacerValidaciones) {
                            if (sCo.isEmpty() || sPa.isEmpty()||sCo.equals("")||sPa.equals("")) {
                                Toast.makeText(getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_LONG).show();
                            } else if (!c.validacionCorreo(sCo)) {
                                Toast.makeText(getApplicationContext(), "Correo invalido", Toast.LENGTH_LONG).show();
                            } else if (!(sPa.length() >= 6)) {
                                Toast.makeText(getApplicationContext(), "Se requiere una contraseña mayor a 5 caracteres", Toast.LENGTH_LONG).show();
                            } else {
                                if (sPa.equals(contrasenia)) {
                                    buscarMascota(c.direccionIP + "buscar_mascota.php?id_mascota=" + id);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Verifica la contraseña", Toast.LENGTH_LONG).show();
                                }
                            }
                        }else {
                            if (sPa.equals(contrasenia)) {
                                buscarMascota(c.direccionIP + "buscar_mascota.php?id_mascota=" + id);
                            } else {
                                Toast.makeText(getApplicationContext(), "Verifica la contraseña", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Vuelve a intentarlo xd", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void buscarMascota(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        idMas = jsonObject.getString("id_mascota");
                        idDue = jsonObject.getString("id_duenio");
                        nombreMas = jsonObject.getString("nombre_mascota");
                        tamanio = jsonObject.getString("tamanio");
                        cuidados = jsonObject.getString("cuidados");
                        raza = jsonObject.getString("raza");
                        edad = jsonObject.getString("edad");
                        seguro = jsonObject.getString("id_seguro");
                        loDelIntent();
                        //Toast.makeText(getApplicationContext(), "Iniciando...", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Vuelve a intentarlo xd x2", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void validaSesion()
    {
        String correo = preferences.getString("correo_", null);
        String nombre = preferences.getString("nombre_", null);

        if (correo != null && nombre != null)
        {
            finish();
            Intent intent = new Intent(getApplication(), NavigationPaseando.class);
            startActivity(intent);
        }
    }


        /*
    private void iniciarSesion(){
        String url = c.direccionIP+"buscar_duenio%20_prb.php?correo="+txtCorreo.getText().toString()+"&contrasenia="+txtPass.getText().toString();
        //String url = c.direccionIP+"buscar_duenio.php?correo="+txtCorreo.getText().toString()+"&contrasenia="+txtPass.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error de conexión xd", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onResponse(JSONObject response) {
        Codigos c1= new Codigos();
        Toast.makeText(this, "Conexión xd", Toast.LENGTH_LONG).show();
        JSONArray jsonArray = response.optJSONArray("array");
        JSONObject jsonObject =null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            c1.setId(jsonObject.optString("id_duenio"));
            c1.setNombre(jsonObject.optString("nombre"));
            c1.setCorreo(jsonObject.optString("correo"));
            c1.setPass(jsonObject.optString("contrasenia"));
            c1.setPaseo(jsonObject.optString("paseo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textView1.setText(c.getCorreo()+c.getPass()+c.getPaseo());
        Intent intent = new Intent(MainActivity.this, NavigationPaseando.class);
        intent.putExtra(NavigationPaseando.nombre,c1.getNombre());
        startActivity(intent);
        limpTextView();
    }
*/

    public void limpTextView() {
        txtCorreo = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtCorreo.setText("");
        txtPass.setText("");
    }

}
