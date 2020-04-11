package com.example.login;

import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Codigos {

    //public String direccionIP = "http://192.168.100.119/prueba/"; //compu keew

    public String direccionIP = "http://192.168.213.187/paseando/"; //compu Orlas

    private String id="";
    private String nombre="";
    private String correo="";
    private String pass="";
    private String paseo="";

    String caracteres = "!#$%&/=*/+-{}[]";
    boolean hacerValidaciones=true;

    boolean bln_CloudFirestore = true;
    boolean bln_RealtimeDatabase = true;

    public Codigos(){
    }




    /*
Método para hacer la validación de un coreo electronico
 */
    boolean validacionCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");/*expresión regular para el correo electronico*/
        Matcher mather = pattern.matcher(correo);
        if (mather.find()) { /*cuando cumple con la expresión regular regresa un valor bolleano true*/
            return true;
        } else { /*cuando no cumple con la expresión regular manda un Toast al usuario y regresa un valor bolleano false*/
            return false;
        }
    }

    boolean validacionNombreApellido(String s) {
        boolean bandera = true;
        StringTokenizer t = new StringTokenizer(s, " ");
        if (t.countTokens() != 2) {

            return false;
        }
        while (t.hasMoreTokens()) {
            if (!validacionNombre(t.nextToken())) {
                bandera = false;
            }
        }
        return bandera;
    }

    boolean validacionNombre1(String s) {
        boolean bandera = true;
        String ABC = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        String abc = "abcdefghijklmnñopqrstuvwxyz";
        if (!ABC.contains(Character.toString(s.charAt(0)))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!abc.contains(Character.toString(s.charAt(i)))) {
                return false;
            }
        }
        return bandera;
    }

    public static boolean validacionNombre(String nombre) {
        boolean nombreClienteCorrecto = false;
        Pattern patron = Pattern.compile("[ 0-9A-Za-zñÑáéíóúÁÉÍÓÚ¡!¿?@#$%()=+-€/.,]{1,50}");
        Matcher comprobacion = patron.matcher(nombre);
        if (comprobacion.matches()) {
            nombreClienteCorrecto = true;
        }
        return nombreClienteCorrecto ;
    }

    boolean validacionPalabra(String s) {
        boolean bandera = true;
        String Abc = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

        for (int i = 0; i < s.length(); i++) {
            if (!Abc.contains(Character.toString(s.charAt(i)))) {
                return false;
            }
        }
        return bandera;
    }

    boolean validacionCaracteresEspeciales(String s) {
        boolean bandera = true;
        for (int i = 0; i < s.length(); i++) {
            if (caracteres.contains(Character.toString(s.charAt(i)))) {
                return false;
            }
        }
        return bandera;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPaseo() {
        return paseo;
    }

    public void setPaseo(String paseo) {
        this.paseo = paseo;
    }
}
