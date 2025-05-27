package proyecto_final_alejandrocolmenar.Modelos;

/*@author Alejandro*/
public class Cliente extends Usuarios {

    public Cliente(int id, String nombre, String usuario, String contraseña) {
        super(id, nombre, usuario, contraseña, "cliente");

    }

    @Override
    public void mostrarMenu() {
        System.out.println("1. Añadir mascota");
        System.out.println("2. Solicitar cita");
        System.out.println("3. Ver Historial de Mascotas");
        System.out.println("4. Cerrar sesión");
    }

}
