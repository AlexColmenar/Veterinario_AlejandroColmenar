package proyecto_final_alejandrocolmenar.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import proyecto_final_alejandrocolmenar.Modelos.*;
import proyecto_final_alejandrocolmenar.CONEXION;

/*@author Alejandro*/
public class HistorialMedicoDAO {

    // Agrega un diagnóstico nuevo al historial de una mascota
    public void agregarDiagnostico(HistorialMedico historial, Diagnostico diagnostico) {
        String sql = "INSERT INTO Historial_medico(id_mascota, id_veterinario, fecha, descripcion, tratamiento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, historial.getMascota().getId());
            stmt.setInt(2, historial.getVeterinario().getId());
            stmt.setDate(3, Date.valueOf(diagnostico.getFecha()));
            stmt.setString(4, diagnostico.getDescripcion());
            stmt.setString(5, diagnostico.getTratamiento_aplicado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Recupera historial completo de una mascota con todos sus diagnósticos
    public HistorialMedico obtenerHistorialPorMascota(Mascotas mascota, Veterinario veterinario) {
        String sql = "SELECT * FROM Historial_medico WHERE id_mascota = ? ORDER BY fecha DESC";
        HistorialMedico historial = new HistorialMedico(0, mascota, veterinario);

        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mascota.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String descripcion = rs.getString("descripcion");
                String tratamiento = rs.getString("tratamiento");

                Diagnostico d = new Diagnostico(fecha, descripcion, tratamiento);
                historial.agregarDiagnostico(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }

    // Recupera todos los historiales del sistema (para administrador)
    public List<HistorialMedico> obtenerTodosLosHistoriales() {
        List<HistorialMedico> historiales = new ArrayList<>();
        String sql = """
        SELECT h.id_historial, h.fecha, h.descripcion, h.tratamiento,
               m.id_mascota, m.nombre AS nombre_mascota,
               u.id_usuario, u.nombre AS nombre_veterinario
        FROM Historial_medico h
        JOIN Mascotas m ON h.id_mascota = m.id_mascota
        JOIN Usuarios u ON h.id_veterinario = u.id_usuario
        ORDER BY m.nombre, h.fecha DESC
    """;

        try (Connection conn = CONEXION.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Veterinario
                Veterinario vet = new Veterinario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_veterinario"),
                        "", "", "General"
                );

                // Mascota
                Mascotas mascota = new Mascotas(
                        rs.getInt("id_mascota"),
                        rs.getString("nombre_mascota"),
                        "", "", 0, null
                );

                // Historial con un solo diagnóstico
                HistorialMedico historial = new HistorialMedico(
                        rs.getInt("id_historial"),
                        mascota,
                        vet
                );

                Diagnostico diag = new Diagnostico(
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("descripcion"),
                        rs.getString("tratamiento")
                );
                historial.agregarDiagnostico(diag);

                historiales.add(historial);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historiales;
    }

}
