package proyecto_final_alejandrocolmenar.DAO;

/*@author Acercandro*/
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyecto_final_alejandrocolmenar.Modelos.*;
import proyecto_final_alejandrocolmenar.*;

public class MascotaDAO {

    public void registrarMascota(Mascotas mascota) {
        String sql = "INSERT INTO Mascotas(nombre, especie, raza, edad, id_propietario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getEspecie());
            stmt.setString(3, mascota.getRaza());
            stmt.setInt(4, mascota.getEdad());
            stmt.setInt(5, mascota.getPropietario().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mascotas> obtenerMascotasPorCliente(Cliente cliente) {
        List<Mascotas> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM Mascotas WHERE id_propietario = ?";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mascotas m = new Mascotas(
                        rs.getInt("id_mascota"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        cliente
                );
                mascotas.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public Mascotas obtenerMascotaPorId(int id) {
        String sql = "SELECT * FROM Mascotas WHERE id_mascota = ?";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente propietario = new Cliente(rs.getInt("id_propietario"), "Dueño", "", "");
                return new Mascotas(
                        rs.getInt("id_mascota"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        propietario
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
