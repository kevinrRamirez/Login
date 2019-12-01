package com.example.login;

public class Codigos {

    //public String direccionIP = "http://192.168.100.119/prueba/"; //compu keew

    public String direccionIP = "http://192.168.1.70/paseando/"; //compu Orlas

    private String id="";
    private String nombre="";
    private String correo="";
    private String pass="";
    private String paseo="";



    public Codigos(){
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
