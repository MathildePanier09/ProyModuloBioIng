package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import users.Coach;
import users.Deportista;

public class Update {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public boolean updateCoach(int coachId, String newName, String newPassword) {
        boolean updated = false;
        Connection conn = null;
        PreparedStatement pstmtUpdate = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String updateSql = "UPDATE COACH SET name = ?, pass = ? WHERE id = ?";
            pstmtUpdate = conn.prepareStatement(updateSql);
            pstmtUpdate.setString(1, newName);
            pstmtUpdate.setString(2, newPassword);
            pstmtUpdate.setInt(3, coachId);

            int rowsUpdated = pstmtUpdate.executeUpdate();
            if (rowsUpdated > 0) {
                updated = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
        }

        return updated;
    }

    public boolean updateDeportista(int deportistaId, String newName, String newPassword) {
        boolean updated = false;
        Connection conn = null;
        PreparedStatement pstmtUpdate = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String updateSql = "UPDATE DEPORTISTA SET name = ?, pass = ? WHERE id = ?";
            pstmtUpdate = conn.prepareStatement(updateSql);
            pstmtUpdate.setString(1, newName);
            pstmtUpdate.setString(2, newPassword);
            pstmtUpdate.setInt(3, deportistaId);

            int rowsUpdated = pstmtUpdate.executeUpdate();
            if (rowsUpdated > 0) {
                updated = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
        }

        return updated;
    }

    public static void main(String[] args) {
        Update updater = new Update();

        // Ejemplo de actualización de un entrenador
        boolean coachUpdated = updater.updateCoach(1, "NuevoNombreEntrenador", "NuevaContraseñaEntrenador");
        if (coachUpdated) {
            System.out.println("¡Entrenador actualizado con éxito!");
        } else {
            System.out.println("Error al actualizar el entrenador.");
        }

        // Ejemplo de actualización de un deportista
        boolean deportistaUpdated = updater.updateDeportista(1, "NuevoNombreDeportista", "NuevaContraseñaDeportista");
        if (deportistaUpdated) {
            System.out.println("¡Deportista actualizado con éxito!");
        } else {
            System.out.println("Error al actualizar el deportista.");
        }
    }
}

