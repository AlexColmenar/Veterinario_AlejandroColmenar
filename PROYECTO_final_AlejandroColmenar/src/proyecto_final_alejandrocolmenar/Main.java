package proyecto_final_alejandrocolmenar;

import proyecto_final_alejandrocolmenar.DAO.*;
import proyecto_final_alejandrocolmenar.Modelos.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final MascotaDAO mascotaDAO = new MascotaDAO();
    private static final CitaDAO citaDAO = new CitaDAO();
    private static final HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse como Cliente");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" ->
                    iniciarSesion();
                case "2" ->
                    registrarCliente();
                case "3" ->
                    salir = true;
                default ->
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void registrarCliente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        Cliente nuevo = new Cliente(0, nombre, usuario, contraseña);
        usuarioDAO.registrarCliente(nuevo);
        System.out.println("✅ Cliente registrado.");
    }

    private static void iniciarSesion() {
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        Usuarios u = usuarioDAO.login(usuario, contraseña);
        if (u != null) {
            boolean cerrar = false;
            while (!cerrar) {
                u.mostrarMenu();
                System.out.print("Opción: ");
                String opcion = scanner.nextLine();
                cerrar = switch (u.getRol()) {
                    case "cliente" ->
                        menuCliente((Cliente) u, opcion);
                    case "veterinario" ->
                        menuVeterinario((Veterinario) u, opcion);
                    case "administrador" ->
                        menuAdministrador((Administrador) u, opcion);
                    default ->
                        true;
                };
            }
        } else {
            System.out.println("❌ Credenciales incorrectas.");
        }
    }

    private static boolean menuCliente(Cliente c, String op) {
        return switch (op) {
            case "1" -> {
                añadirMascota(c);
                yield false;
            }
            case "2" -> {
                solicitarCita(c);
                yield false;
            }
            case "3" -> {
                verHistorialCliente(c);
                yield false;
            }
            case "4" ->
                true;
            default ->
                false;
        };
    }

    private static void añadirMascota(Cliente cliente) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Especie: ");
        String especie = scanner.nextLine();
        System.out.print("Raza: ");
        String raza = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = Integer.parseInt(scanner.nextLine());

        Mascotas m = new Mascotas(0, nombre, especie, raza, edad, cliente);
        mascotaDAO.registrarMascota(m);
        System.out.println("✅ Mascota registrada.");
    }

    private static void solicitarCita(Cliente cliente) {
        List<Mascotas> mascotas = mascotaDAO.obtenerMascotasPorCliente(cliente);

        if (mascotas.isEmpty()) {
            System.out.println("⚠ No tienes mascotas registradas.");
            return;
        }

        System.out.println("Selecciona una mascota:");
        for (int i = 0; i < mascotas.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, mascotas.get(i).getNombre());
        }

        int indiceMascota = Integer.parseInt(scanner.nextLine()) - 1;
        Mascotas mascota = mascotas.get(indiceMascota);

        List<Veterinario> veterinarios = usuarioDAO.obtenerTodosLosVeterinarios();
        if (veterinarios.isEmpty()) {
            System.out.println("❌ No hay veterinarios disponibles.");
            return;
        }

        System.out.println("Selecciona un veterinario:");
        for (int i = 0; i < veterinarios.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, veterinarios.get(i).getNombre());
        }

        int indiceVet = Integer.parseInt(scanner.nextLine()) - 1;
        Veterinario vetSeleccionado = veterinarios.get(indiceVet);

        System.out.print("Fecha de la cita (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());

        System.out.print("Hora de la cita (HH:MM): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        Cita cita = new Cita(0, fecha, hora, mascota, cliente, vetSeleccionado, "pendiente");
        citaDAO.solicitarCita(cita);

        System.out.println("✅ Cita registrada correctamente.");
    }

    private static void verHistorialCliente(Cliente cliente) {
        List<Mascotas> mascotas = mascotaDAO.obtenerMascotasPorCliente(cliente);
        for (Mascotas m : mascotas) {
            System.out.println("Mascota: " + m.getNombre());
            HistorialMedico h = historialDAO.obtenerHistorialPorMascota(m, new Veterinario(1, "Vet", "", "", "General"));
            for (Diagnostico d : h.getDiagnosticos()) {
                System.out.println("  - " + d);
            }
        }
    }

    private static boolean menuVeterinario(Veterinario v, String op) {
        return switch (op) {
            case "1" -> {
                verCitas(v);
                yield false;
            }
            case "2" -> {
                atenderCita(v);
                yield false;
            }
            case "3" -> {
                verHistorialMascota();
                yield false;
            }
            case "4" ->
                true;
            default ->
                false;
        };
    }

    private static void verCitas(Veterinario vet) {
        List<Cita> citas = citaDAO.obtenerCitasPorVeterinario(vet.getId());
        for (Cita c : citas) {
            System.out.printf("ID: %d | %s %s | Mascota: %s\n",
                    c.getId(), c.getFecha(), c.getHora(), c.getMascota().getNombre());
        }
    }

    private static void atenderCita(Veterinario vet) {
        System.out.print("ID de mascota: ");
        int idMascota = Integer.parseInt(scanner.nextLine());
        Mascotas mascota = new Mascotas(idMascota, "Desconocido", "", "", 0, null);

        System.out.print("Descripción: ");
        String desc = scanner.nextLine();
        System.out.print("Tratamiento: ");
        String trat = scanner.nextLine();

        HistorialMedico historial = new HistorialMedico(0, mascota, vet);
        historialDAO.agregarDiagnostico(historial, new Diagnostico(LocalDate.now(), desc, trat));

        System.out.println("✅ Diagnóstico añadido.");
    }

    private static void verHistorialMascota() {
        System.out.print("ID de mascota: ");
        int id = Integer.parseInt(scanner.nextLine());
        Mascotas m = new Mascotas(id, "Mascota", "", "", 0, null);
        Veterinario v = new Veterinario(1, "Vet", "", "", "General");

        HistorialMedico h = historialDAO.obtenerHistorialPorMascota(m, v);
        for (Diagnostico d : h.getDiagnosticos()) {
            System.out.println("- " + d);
        }
    }

    private static boolean menuAdministrador(Administrador a, String op) {
        return switch (op) {
            case "1" -> {
                gestionarUsuarios();
                yield false;
            }
            case "2" -> {
                verEstadisticas();
                yield false;
            }
            case "3" -> {
                verTodosLosHistoriales();
                yield false;
            }
            case "4" ->
                true;
            default ->
                false;
        };

    }

    private static void gestionarUsuarios() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Gestión de Usuarios ---");
            System.out.println("1. Ver todos los usuarios");
            System.out.println("2. Añadir veterinario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    List<Usuarios> usuarios = usuarioDAO.listarUsuarios();
                    for (Usuarios u : usuarios) {
                        System.out.printf("ID: %d | Nombre: %s | Usuario: %s | Rol: %s\n",
                                u.getId(), u.getNombre(), u.getUsuario(), u.getRol());
                    }
                    break;

                case "2":
                    System.out.print("Nombre completo: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Usuario: ");
                    String usuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contraseña = scanner.nextLine();
                    System.out.print("Especialidad: ");
                    String especialidad = scanner.nextLine();

                    Veterinario vet = new Veterinario(0, nombre, usuario, contraseña, especialidad);
                    usuarioDAO.registrarVeterinario(vet);
                    System.out.println("✅ Veterinario añadido.");
                    break;

                case "3":
                    System.out.print("ID del usuario a eliminar: ");
                    int idEliminar = Integer.parseInt(scanner.nextLine());
                    usuarioDAO.eliminarUsuarioPorId(idEliminar);
                    System.out.println("✅ Usuario eliminado (si existía).");
                    break;

                case "4":
                    salir = true;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void verEstadisticas() {
        try (var conn = CONEXION.conectar()) {
            var stmt = conn.prepareStatement("SELECT COUNT(*) FROM Citas");
            var rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Total citas: " + rs.getInt(1));
            }

            var top = conn.prepareStatement("""
                SELECT descripcion, COUNT(*) FROM Historial_medico
                GROUP BY descripcion ORDER BY COUNT(*) DESC LIMIT 3
            """);
            var r2 = top.executeQuery();
            while (r2.next()) {
                System.out.printf("- %s (%d veces)\n", r2.getString(1), r2.getInt(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void verTodosLosHistoriales() {
        List<HistorialMedico> historiales = historialDAO.obtenerTodosLosHistoriales();

        if (historiales.isEmpty()) {
            System.out.println("No hay historial médico registrado.");
            return;
        }

        System.out.println("\n--- Historial Médico Global ---");
        for (HistorialMedico h : historiales) {
            System.out.println("🐾 Mascota: " + h.getMascota().getNombre());
            System.out.println("👨‍⚕ Veterinario: " + h.getVeterinario().getNombre());
            for (Diagnostico d : h.getDiagnosticos()) {
                System.out.println("📅 " + d);
            }
            System.out.println("--------------------------------------");
        }
    }
}
