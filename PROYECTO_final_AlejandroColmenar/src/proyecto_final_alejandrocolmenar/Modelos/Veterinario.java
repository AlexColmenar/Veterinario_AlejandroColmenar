package proyecto_final_alejandrocolmenar.Modelos;

/*@author Alejandro*/
public class Veterinario extends Usuarios {

    private String especialidad;

    public Veterinario(int id, String nombre, String usuario, String contraseña, String especialidad) {
        super(id, nombre, usuario, contraseña, "veterinario");
        this.especialidad = especialidad;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("1. Ver Citas Programadas");
        System.out.println("2. Atender una Cita");
        System.out.println("3. Ver historial de una Mascota");
        System.out.println("4. Cerrar Sesión");
        System.out.println("Selecciona una opción: ");
    }

    @Override
    public String getContraseña() {
        return super.getContraseña();
    }

    @Override
    public String getUsuario() {
        return super.getUsuario();
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public int getId() {
        return super.getId();
    }

}
