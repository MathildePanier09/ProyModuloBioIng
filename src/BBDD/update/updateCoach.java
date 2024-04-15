package BBDD.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class updateCoach {
	  private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
	    private static final String USER = "root";
	    private static final String PASS = "";

	        public static boolean updateCoachName(int coachId, String newName) {
	            boolean updated = false;

	            Connection conn = null;
	            PreparedStatement pstmtUpdate = null;

	            try {
	                // Paso 1: Registrar el controlador JDBC y establecer la conexión
	                Class.forName("org.mariadb.jdbc.Driver");
	                conn = DriverManager.getConnection(URL, USER, PASS);

	                // Paso 2: Actualizar el nombre del entrenador
	                String updateSql = "UPDATE COACH SET name = ? WHERE id = ?";
	                pstmtUpdate = conn.prepareStatement(updateSql);
	                pstmtUpdate.setString(1, newName);
	                pstmtUpdate.setInt(2, coachId);
	                int rowsUpdated = pstmtUpdate.executeUpdate();
	                if (rowsUpdated > 0) {
	                    updated = true;
	                }

	            } catch (SQLException | ClassNotFoundException e) {
	                e.printStackTrace();
	            } finally {
	                // Cerrar recursos
	                try {
	                    if (pstmtUpdate != null) pstmtUpdate.close();
	                    if (conn != null) conn.close();
	                } catch (SQLException se) {
	                    se.printStackTrace();
	                }
	            }

	            return updated;
	        }

	        public static boolean updateCoachPassword(int coachId, String newPassword) {
	            boolean updated = false;

	            Connection conn = null;
	            PreparedStatement pstmtUpdate = null;

	            try {
	                // Paso 1: Registrar el controlador JDBC y establecer la conexión
	                Class.forName("org.mariadb.jdbc.Driver");
	                conn = DriverManager.getConnection(URL, USER, PASS);

	                // Paso 2: Actualizar la contraseña del entrenador
	                String updateSql = "UPDATE COACH SET pass = ? WHERE id = ?";
	                pstmtUpdate = conn.prepareStatement(updateSql);
	                pstmtUpdate.setString(1, newPassword);
	                pstmtUpdate.setInt(2, coachId);
	                int rowsUpdated = pstmtUpdate.executeUpdate();
	                if (rowsUpdated > 0) {
	                    updated = true;
	                }

	            } catch (SQLException | ClassNotFoundException e) {
	                e.printStackTrace();
	            } finally {
	                // Cerrar recursos
	                try {
	                    if (pstmtUpdate != null) pstmtUpdate.close();
	                    if (conn != null) conn.close();
	                } catch (SQLException se) {
	                    se.printStackTrace();
	                }
	            }

	            return updated;
	        }
	    }
