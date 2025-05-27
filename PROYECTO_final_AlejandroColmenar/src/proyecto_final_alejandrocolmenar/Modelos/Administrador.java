package proyecto_final_alejandrocolmenar.Modelos;

/*@author Alejandro*/
public class Administrador extends Usuarios {

    public Administrador(int id, String nombre, String usuario, String contraseña) {
        super(id, nombre, usuario, contraseña, "administrador");
    }

    @Override
    public void mostrarMenu() {
        System.out.println("1. Gestionar Usuarios");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Ver historiales medicos");
        System.out.println("4. Cerrar Sesión");
        System.out.println("Selecciona una opción: ");
    }
}
