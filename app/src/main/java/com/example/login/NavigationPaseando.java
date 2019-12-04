package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.login.ui.home.HomeFragment;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
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

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class NavigationPaseando extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView textViewCorreo;
    private TextView textViewNombre;
    private FragmentManager supportFragmentManager;

    TextView txtPrbPaseo;
    public static final String nombre="names";
    TextView btnNuevoP;
    Codigos c = new Codigos();
    View view;

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
                R.id.nav_home, R.id.nav_mis_paseos, R.id.nav_cerrar_se,
                R.id.nav_mascotas, R.id.nav_perfil, R.id.nav_acerca)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //textViewCorreo = (TextView)
        //textViewNombre = (TextView)findViewById(R.id.textViewNombre);
        Bundle datoCorreo = getIntent().getExtras();
        String correo = datoCorreo.getString("datoCorreo");
        String nombre = datoCorreo.getString("datoNombre");
        View headerView = navigationView.getHeaderView(0);
        textViewCorreo = (TextView) headerView.findViewById(R.id.textViewCorreo);
        textViewNombre = (TextView) headerView.findViewById(R.id.textViewNombre);
        textViewCorreo.setText(correo);
        textViewNombre.setText(nombre);


        btnNuevoP = (TextView)findViewById(R.id.btnNuevoPaseo);



        //String corr = mainActivity.getCorre();
        //String url = "buscar_duenio.php?correo=";//+corr+"";
        // String usuario=getIntent().getStringExtra("names");
        //usuario="orlasss";
        //c.setNombre(usuario);
        //btnNuevoP.setText("Bienvenido"+usuario);

    }

   // public NavigationPaseando(TextView textViewCorreo) {
      //  this.textViewCorreo = textViewCorreo;
    //}


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
        Intent intent = new Intent(view.getContext(),PedirPaseo.class);
        intent.putExtra("correo",textViewCorreo.getText().toString());
        startActivity(intent);
    }
    public void ctrlBotonHospedaje(View view)
    {
        Intent intent = new Intent(view.getContext(),Hospedaje.class);
        startActivity(intent);
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
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
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
