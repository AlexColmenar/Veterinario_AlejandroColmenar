package proyecto_final_alejandrocolmenar.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyecto_final_alejandrocolmenar.Modelos.*;
import proyecto_final_alejandrocolmenar.*;


/*@author Alejandro*/
public class UsuarioDAO {

    public Usuarios login(String usuario, String contraseña) {
        String sql = "SELECT * FROM Usuarios WHERE usuario = ? AND contraseña = ?";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");

                switch (rol.toLowerCase()) {
                    case "cliente":
                        return new Cliente(id, nombre, usuario, contraseña);
                    case "veterinario":
                        return new Veterinario(id, nombre, usuario, contraseña, "General");
                    case "administrador":
                        return new Administrador(id, nombre, usuario, contraseña);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO Usuarios(nombre, usuario, contraseña, rol) VALUES (?, ?, ?, 'cliente')";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getUsuario());
            stmt.setString(3, cliente.getContraseña());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuarios> listarUsuarios() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String usuario = rs.getString("usuario");
                String contraseña = rs.getString("contraseña");
                String rol = rs.getString("rol");

                Usuarios u = switch (rol) {
                    case "cliente" ->
                        new Cliente(id, nombre, usuario, contraseña);
                    case "veterinario" ->
                        new Veterinario(id, nombre, usuario, contraseña, "General");
                    case "administrador" ->
                        new Administrador(id, nombre, usuario, contraseña);
                    default ->
                        null;
                };

                if (u != null) {
                    lista.add(u);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void registrarVeterinario(Veterinario vet) {
        String sql = "INSERT INTO Usuarios(nombre, usuario, contraseña, rol) VALUES (?, ?, ?, 'veterinario')";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vet.getNombre());
            stmt.setString(2, vet.getUsuario());
            stmt.setString(3, vet.getContraseña());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarUsuarioPorId(int id) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Veterinario obtenerPrimerVeterinarioDisponible() {
        String sql = "SELECT * FROM Usuarios WHERE rol = 'veterinario' LIMIT 1";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String usuario = rs.getString("usuario");
                String contraseña = rs.getString("contraseña");

                return new Veterinario(id, nombre, usuario, contraseña, "General"); // O pon especialidad real si existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Veterinario> obtenerTodosLosVeterinarios() {
        List<Veterinario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE rol = 'veterinario'";

        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veterinario vet = new Veterinario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contraseña"),
                        "General" // O agregar campo "especialidad" si lo tienes
                );
                lista.add(vet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
