package BBDD.delete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public void deleteCoach(int coachId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // Eliminar registros relacionados en COACH_DEPORTISTA
            String deleteCoachDeportistaSql = "DELETE FROM COACH_DEPORTISTA WHERE coach_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteCoachDeportistaSql)) {
                pstmt.setInt(1, coachId);
                pstmt.executeUpdate();
            }

            // Eliminar el coach de la tabla COACH
            String deleteCoachSql = "DELETE FROM COACH WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteCoachSql)) {
                pstmt.setInt(1, coachId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDeportista(int deportistaId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // Eliminar registros relacionados en COACH_DEPORTISTA
            String deleteCoachDeportistaSql = "DELETE FROM COACH_DEPORTISTA WHERE deportista_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteCoachDeportistaSql)) {
                pstmt.setInt(1, deportistaId);
                pstmt.executeUpdate();
            }

            // Eliminar el deportista de la tabla DEPORTISTA
            String deleteDeportistaSql = "DELETE FROM DEPORTISTA WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteDeportistaSql)) {
                pstmt.setInt(1, deportistaId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDeportistaFromCoach(int coachId, int deportistaId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String deleteSql = "DELETE FROM COACH_DEPORTISTA WHERE coach_id = ? AND deportista_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, coachId);
                pstmt.setInt(2, deportistaId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    public void deleteSensorFromDeportista(int sensorId, int deportistaId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String deleteSql = "DELETE FROM SENSOR_DEP WHERE sensor_id = ? AND deportista_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, sensorId);
                pstmt.setInt(2, deportistaId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
