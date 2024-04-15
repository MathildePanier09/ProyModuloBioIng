package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public String login(String username, String password) {
        String userType = ""; // Tipo de usuario (coach o deportista)
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // Verificar si es coach
            String coachLoginSql = "SELECT id FROM COACH WHERE name = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(coachLoginSql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userType = "C";
                        return userType + rs.getInt("id");
                    }
                }
            }

            // Verificar si es deportista
            String deportistaLoginSql = "SELECT id FROM DEPORTISTA WHERE name = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deportistaLoginSql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userType = "D";
                        return userType + rs.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType; // Retorna el tipo de usuario encontrado (o vacío si no se encontró ninguno)
    }

    public static void main(String[] args) {
        Login loginManager = new Login();

        // Ejemplo de autenticación como coach
        String coachLoginResult = loginManager.login("C1", "Pass");
        System.out.println("Resultado de inicio de sesión como coach: " + coachLoginResult);

        // Ejemplo de autenticación como deportista
        String deportistaLoginResult = loginManager.login("D3", "Pass");
        System.out.println("Resultado de inicio de sesión como deportista: " + deportistaLoginResult);
    }
}
