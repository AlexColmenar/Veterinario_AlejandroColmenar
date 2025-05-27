package proyecto_final_alejandrocolmenar.Modelos;

/*@author Alejandro*/
public class Mascotas {

    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private Cliente propietario;

    public Mascotas(int id, String nombre, String especie, String raza, int edad, Cliente propietario) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.propietario = propietario;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public int getEdad() {
        return edad;
    }

    public Cliente getPropietario() {
        return propietario;
    }
}
