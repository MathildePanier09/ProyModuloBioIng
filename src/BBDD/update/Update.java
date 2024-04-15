package BBDD.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public void updateCoachName(int coachId, String newName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String updateSql = "UPDATE COACH SET name = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, newName);
                pstmt.setInt(2, coachId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCoachPassword(int coachId, String newPassword) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String updateSql = "UPDATE COACH SET pass = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, coachId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDeportistaName(int deportistaId, String newName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String updateSql = "UPDATE DEPORTISTA SET name = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, newName);
                pstmt.setInt(2, deportistaId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDeportistaPassword(int deportistaId, String newPassword) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String updateSql = "UPDATE DEPORTISTA SET pass = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, deportistaId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Update updater = new Update();

        // Ejemplo de actualización de un coach
        updater.updateCoachName(3, "NewCoachName");
        updater.updateCoachPassword(3, "NewCoachPass");

        // Ejemplo de actualización de un deportista
        updater.updateDeportistaName(1, "NewDeportistaName");
        updater.updateDeportistaPassword(1, "NewDeportistaPass");
    }
}
