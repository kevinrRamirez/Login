package com.example.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.login.ui.home.HomeFragment;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.TestLooperManager;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationPaseando extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView textViewCorreo;
    private TextView textViewNombre;
    private FragmentManager supportFragmentManager;
    String correo2;
    TextView txtPrbPaseo;
    public static final String nombre="names";
    TextView btnNuevoP;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_paseando);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                 */
                ctrlBotonNuevoPaseo(view);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_mis_paseos,
                R.id.nav_mascotas, R.id.nav_perfil, R.id.nav_acerca)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //textViewCorreo = (TextView)
        //textViewNombre = (TextView)findViewById(R.id.textViewNombre);
        //Bundle datoCorreo = getIntent().getExtras();
        //correo2 = datoCorreo.getString("datoCorreo");
        //String nombre = datoCorreo.getString("datoNombre");



        View headerView = navigationView.getHeaderView(0);
        textViewCorreo = (TextView) headerView.findViewById(R.id.textViewCorreo);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        String correo = preferences.getString("correo_", null);
        String nombre = preferences.getString("nombre", null);
        if (correo != null && nombre != null)
        {
            textViewCorreo.setText(correo.toString());
            textViewNombre = (TextView) headerView.findViewById(R.id.textViewNombre);
            textViewNombre.setText(nombre);
        }

/*
          Bundle b = getIntent().getExtras();
          String bCorreo = b.getString("datoCorreo");
          String bNombre = b.getString("datoNombre");



       textViewNombre = (TextView) headerView.findViewById(R.id.textViewNombre);
        textViewNombre.setText(bNombre);
        textViewCorreo.setText(bCorreo);
        btnNuevoP = (TextView)findViewById(R.id.btnNuevoPaseo);

 */

        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);


    }




    public void cerrarApp(View view)
    {
        finish();
    }

    public void editText(View view)
    {
        TextView text = (TextView) view.findViewById(R.id.txtPrbPaseo);
        text.setText("test");
    }

    public void ctrlBtnHome(View view)
    {
        Intent intent = new Intent(view.getContext(),NavigationPaseando.class);
        startActivity(intent);
    }



    public void ctrlBotonNuevoPaseo(View view)
    {
        Intent intent = new Intent(NavigationPaseando.this,PedirPaseo.class);
        intent.putExtra("correo",correo2);
        startActivity(intent);
    }
    public void ctrlBotonHospedaje(View view)
    {
        Intent intent = new Intent(view.getContext(),Hospedaje.class);
        startActivity(intent);
    }
    public void ctrlBotonSeguimiento(View view)
    {
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        CollectionReference collectionReference = db.collection("paseos");
        collectionReference
                .whereEqualTo("status", "1")
                .whereEqualTo("id_usuario", firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String str = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                str +=  "usuario: \t" + document.get("id_usuario").toString()+ "\n";
                                str +=  "status: \t" + document.get("status").toString()+ "\n";
                            }
                            progressDialog.dismiss();
                            if (!str.equals("")){

                            }else{
                                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_paseando, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_ayuda)
        {
            Uri uri = Uri.parse("https://paseando.bss.design/");
            //Uri uri = Uri.parse("https://waze.com/ul?q=19.285601C-99.5924230");//"https://www.waze.com/ul?ll=19.285601C-99.5924230&navigate=yes&zoom=17");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.action_salir)
        {
            Intent intent = new Intent(this,PopupSalir.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
