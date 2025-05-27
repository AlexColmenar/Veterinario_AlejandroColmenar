package proyecto_final_alejandrocolmenar.Modelos;

import java.time.LocalDate;

/* @author Alejandro*/
public class Diagnostico {

    private LocalDate fecha;
    private String descripcion;
    private String tratamiento_aplicado;

    public Diagnostico(LocalDate fecha, String descripcion, String tratamiento_aplicado) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tratamiento_aplicado = tratamiento_aplicado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTratamiento_aplicado() {
        return tratamiento_aplicado;
    }

    @Override
    public String toString() {
        return "Diagnostico: " + "fecha: " + fecha + " " + descripcion + ", tratamiento_aplicado: " + tratamiento_aplicado + '}';
    }

}
