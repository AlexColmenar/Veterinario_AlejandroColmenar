package proyecto_final_alejandrocolmenar.Modelos;

import java.util.ArrayList;
import java.util.List;

/* @author Alejandro */
public class HistorialMedico {

    private Mascotas mascota;
    private int idHistorial;
    private List<Diagnostico> diagnosticos;
    private Veterinario veterinario;

    public HistorialMedico(int idHistorial, Mascotas mascota, Veterinario veterinario) {
        this.mascota = mascota;
        this.idHistorial = idHistorial;
        this.diagnosticos = new ArrayList<>();
        this.veterinario = veterinario;
    }

    public Mascotas getMascota() {
        return mascota;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void agregarDiagnostico(Diagnostico diag) {
        diagnosticos.add(diag);
    }

}
