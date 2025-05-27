package proyecto_final_alejandrocolmenar.Modelos;

import java.util.Scanner;

/* @author Alejandro*/
public abstract class Usuarios {

    private int id;
    private String nombre;
    private String usuario;
    private String contraseña;
    private String rol;

    public Usuarios(int id, String nombre, String usuario, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public abstract void mostrarMenu();

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getRol() {
        return rol;
    }

}
