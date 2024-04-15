package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteDeportista {

    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public static boolean deleteDeportistaByUserId(int deportistaId, int userId) {
        boolean deleted = false;

        Connection conn = null;
        PreparedStatement pstmtDelete = null;

        try {
            // Paso 1: Registrar el controlador JDBC y establecer la conexión
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            // Paso 2: Verificar si el deportista con el ID dado coincide con el ID del usuario
            String selectSql = "SELECT COUNT(*) FROM deportistas WHERE id = ? AND userId = ?";
            PreparedStatement pstmtSelect = conn.prepareStatement(selectSql);
            pstmtSelect.setInt(1, deportistaId);
            pstmtSelect.setInt(2, userId);
            ResultSet resultSet = pstmtSelect.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) { // Si hay una coincidencia, eliminar el deportista
                String deleteSql = "DELETE FROM deportistas WHERE id = ?";
                pstmtDelete = conn.prepareStatement(deleteSql);
                pstmtDelete.setInt(1, deportistaId);
                int rowsDeleted = pstmtDelete.executeUpdate();
                if (rowsDeleted > 0) {
                    deleted = true;
                }
            } else { // Si no hay coincidencia, no hacer nada
                System.out.println("El ID del deportista no coincide con el ID del usuario. No se eliminó ningún deportista.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (pstmtDelete != null) pstmtDelete.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return deleted;
    }
}
