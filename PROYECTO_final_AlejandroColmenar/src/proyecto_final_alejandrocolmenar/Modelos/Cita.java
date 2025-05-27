package proyecto_final_alejandrocolmenar.Modelos;

import java.time.LocalDate;
import java.time.LocalTime;

/*@author Alejandro*/
public class Cita {

    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private Mascotas mascota;
    private Cliente propietario;
    private Veterinario veterinario;
    private String estado;

    public Cita(int id, LocalDate fecha, LocalTime hora, Mascotas mascota, Cliente propietario, Veterinario veterinario, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.mascota = mascota;
        this.propietario = propietario;
        this.veterinario = veterinario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Mascotas getMascota() {
        return mascota;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public String getEstado() {
        return estado;
    }

    //agregamos un nuevo estado por el antiguo
    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

}
