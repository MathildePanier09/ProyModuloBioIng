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

    // Función para iniciar sesión y devolver el tipo de usuario y su ID
    public String login(int id, String password) {
        String userType = ""; // Tipo de usuario (coach o deportista)
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // Verificar si es coach
            String coachLoginSql = "SELECT id FROM COACH WHERE id = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(coachLoginSql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userType = "C";
                        return userType + rs.getInt("id");
                    }
                }
            }

            // Verificar si es deportista
            String deportistaLoginSql = "SELECT id FROM DEPORTISTA WHERE id = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deportistaLoginSql)) {
                pstmt.setInt(1, id);
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

    // Función para obtener la información del usuario que ha iniciado sesión
    public String getUserInfo(int id, String password) {
        String userInfo = ""; // Información del usuario
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // Verificar si es coach
            String coachLoginSql = "SELECT * FROM COACH WHERE id = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(coachLoginSql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userInfo = "Coach: ID - " + rs.getInt("id") + ", Nombre - " + rs.getString("name");
                        return userInfo;
                    }
                }
            }

            // Verificar si es deportista
            String deportistaLoginSql = "SELECT * FROM DEPORTISTA WHERE id = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deportistaLoginSql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userInfo = "Deportista: ID - " + rs.getInt("id") + ", Nombre - " + rs.getString("name");
                        return userInfo;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo; // Retorna la información del usuario encontrado (o vacío si no se encontró ninguno)
    }

    public static void main(String[] args) {
        Login loginManager = new Login();

        // Ejemplo de autenticación como coach
        String coachLoginResult = loginManager.login(2, "Pass");
        System.out.println("Resultado de inicio de sesión como coach: " + coachLoginResult);

        // Ejemplo de autenticación como deportista
        String deportistaLoginResult = loginManager.login(3, "Pass");
        System.out.println("Resultado de inicio de sesión como deportista: " + deportistaLoginResult);

        // Ejemplo de obtener información del usuario
        String userInfo = loginManager.getUserInfo(2, "Pass");
        System.out.println("Información del usuario: " + userInfo);
    }
}
