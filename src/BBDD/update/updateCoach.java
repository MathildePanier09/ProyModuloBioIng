package BBDD.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class updateCoach {
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
}
