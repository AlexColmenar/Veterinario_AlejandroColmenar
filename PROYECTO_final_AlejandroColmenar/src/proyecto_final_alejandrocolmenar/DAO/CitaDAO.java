package proyecto_final_alejandrocolmenar.DAO;

/*@author Alejandro*/
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyecto_final_alejandrocolmenar.Modelos.*;
import proyecto_final_alejandrocolmenar.*;

public class CitaDAO {

    public void solicitarCita(Cita cita) {
        String sql = "INSERT INTO Citas(fecha, hora, id_mascota, id_propietario, id_veterinario, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(cita.getFecha()));
            stmt.setTime(2, Time.valueOf(cita.getHora()));
            stmt.setInt(3, cita.getMascota().getId());
            stmt.setInt(4, cita.getPropietario().getId());
            stmt.setInt(5, cita.getVeterinario().getId());
            stmt.setString(6, cita.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cita> obtenerCitasPorVeterinario(int idVeterinario) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM Citas WHERE id_veterinario = ? AND estado = 'pendiente'";

        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeterinario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Obtener IDs relacionados
                int idMascota = rs.getInt("id_mascota");
                int idPropietario = rs.getInt("id_propietario");

                // Crear objetos mínimos (si quieres puedes cargar datos reales desde sus DAOs)
                Mascotas mascota = new MascotaDAO().obtenerMascotaPorId(idMascota);
                Cliente propietario = new Cliente(idPropietario, "Desconocido", "", "");
                Veterinario veterinario = new Veterinario(idVeterinario, "Vet", "", "", "General");

                // Crear la cita completa
                Cita cita = new Cita(
                        rs.getInt("id_cita"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        mascota,
                        propietario,
                        veterinario,
                        rs.getString("estado")
                );
                citas.add(cita);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citas;
    }

    public void actualizarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE Citas SET estado = ? WHERE id_cita = ?";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
