package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PedirPaseo extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    Button btnubicacion;
    TextView txtUbicacion;
    GoogleMap mMap;
    MapView mapView;
    private double lat;
    private double lon;
    private Location location;
    private LocationManager locationManager;
    private  List<Address>  direccion;
    private Spinner spnrTiempo;
    private TextView txtPrecio;
    RequestQueue requestQueue;
    Codigos c;
    String direc;
    String tiempo;
    String costo;
    String nombreUs;
    String correoUs;
    String idUs;
    String cont;
    String paseo;
    String datoCorreo;

    @Override
    protected void onResume() {

        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_paseo);
        txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);
        mapView = (MapView) findViewById(R.id.mapViewPP);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMyLocationEnabled(true);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
           direccion = geocoder.getFromLocation(lat,lon,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);

        spnrTiempo = (Spinner)findViewById(R.id.spnrTiempo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.selecciona_tiempo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTiempo.setAdapter(adapter);
        spnrTiempo.setOnItemSelectedListener(this);
        /*
       btnubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> direccion = geocoder.getFromLocation( location.getLatitude(), location.getLongitude(),1);
                    txtUbicacion.setText(direccion.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

         */
        Bundle dato = getIntent().getExtras();
        datoCorreo = dato.getString("correo");


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();


        if (text.equals("30 min"))
        {
            txtPrecio.setText("Costo: " +"\n"+" 25$");
            costo = "25";
            tiempo = "30";

            Toast.makeText(getApplicationContext(),"Estaras pagando 25$ " ,Toast.LENGTH_SHORT).show();
        }else if (text.equals("1 hr"))
        {
            txtPrecio.setText("Costo: " +"\n"+" 40$");
            costo = "40";
            tiempo = "60";
            Toast.makeText(getApplicationContext(),"Estaras pagando 25$",Toast.LENGTH_SHORT).show();
        }else if (text.equals("2 hr"))
        {
            txtPrecio.setText("Costo: " +"\n"+" 60$");
            costo = "60";
            tiempo = "120";
            Toast.makeText(getApplicationContext(),"Estaras pagando 25$",Toast.LENGTH_SHORT).show();
        }else if (text.equals("Proximas..."))
        {
            txtPrecio.setText("Esparalo ");
            costo = "";
            tiempo = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void ctrlBtnAceptar(View view) {


        String urlCon = "http://192.168.100.119/prueba/buscar_duenio.php?=correo"+datoCorreo;
        Toast.makeText(getApplicationContext(),urlCon,Toast.LENGTH_SHORT).show();
        consultaDuenio(urlCon);

            try {
                String urlSer = "http://192.168.100.119/prueba/registro_contrato.php";
                servicioContrato(urlSer);
                Toast.makeText(getApplicationContext(), "En proceso ...", Toast.LENGTH_SHORT).show();
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }


            finish();

    }


    public void ctrlBtnOtraUbi(View view)
    {
        Intent intent;
        intent = new Intent(view.getContext(), OtraUbicacionActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        lon = location.getLongitude();
        lat = location.getLatitude();
        // Add a marker in Sydney and move the camera

        LatLng ubicacion = new LatLng(lat, lon);
        //mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,15));

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> direccion = geocoder.getFromLocation( location.getLatitude(), location.getLongitude(),1);
            direc = direccion.get(0).getAddressLine(0);
            txtUbicacion.setText(direc);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void servicioContrato(String url)
    {
        final String lati = Double.toString(lat);
        final String longi = Double.toString(lon);
        final String dato1 = new Date().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Regsitro exitoso #", Toast.LENGTH_SHORT).show();
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
                parametros.put("latitud", "12" );//Double.toString(lat));
                parametros.put("longitud","12");//Double.toString(lon));
                parametros.put("id_paseador","13");
                parametros.put("id_mascota","15");
                parametros.put("hora_inicio", "121212");//new Date().toString());
                parametros.put("hora_fin"," 232");
                parametros.put("costo","eee");
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void consultaDuenio(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        idUs = jsonObject.getString("id_duenio");
                        nombreUs = jsonObject.getString("nombre");
                        correoUs = jsonObject.getString("correo");
                        cont = jsonObject.getString("contrasenia");
                        paseo = jsonObject.getString("paseo");

                        Toast.makeText(getApplicationContext(), "Iniciando...", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión xd#", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }




}
