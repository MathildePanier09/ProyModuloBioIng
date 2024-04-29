package BBDD.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import users.Coach;
import users.Deportista;

public class Login {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public Deportista loginDeportista(int id, String password) {
        String sql = "SELECT * FROM DEPORTISTA WHERE id = ? AND pass = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Deportista(rs.getString("name"), rs.getString("id"), rs.getString("pass"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encontró el deportista
    }

    public Coach loginCoach(int id, String password) {
        String sql = "SELECT * FROM COACH WHERE id = ? AND pass = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Si las credenciales del coach son correctas, creamos el objeto Coach
                    String coachName = rs.getString("name");
                    String coachPassword = rs.getString("pass");
                    Vector<Deportista> pacientes = retrievePacientesForCoach(id);
                    return new Coach(coachName, Integer.toString(id), coachPassword, pacientes);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra ningún coach con las credenciales proporcionadas
    }
    
    // DEVUELVE LA LISTA DE DEPORTISTAS EN UN VECTOR
    private Vector<Deportista> retrievePacientesForCoach(int coachId) {
        Vector<Deportista> pacientes = new Vector<>();
        String sql = "SELECT DEPORTISTA.* FROM DEPORTISTA " +
                     "INNER JOIN COACH_DEPORTISTA ON DEPORTISTA.id = COACH_DEPORTISTA.deportista_id " +
                     "WHERE COACH_DEPORTISTA.coach_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, coachId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String deportistaName = rs.getString("name");
                    String deportistaPassword = rs.getString("pass");
                    Deportista deportista = new Deportista(deportistaName, Integer.toString(rs.getInt("id")), deportistaPassword);
                    pacientes.add(deportista);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public static void main(String[] args) {
    	 // Crear una instancia de la clase Login
        Login login = new Login();

        // Probar el login de un deportista
        Deportista deportista = login.loginDeportista(1, "password_de_deportista");
        if (deportista != null) {
            System.out.println("Deportista autenticado: " + deportista.getName());
        } else {
            System.out.println("Credenciales de deportista incorrectas.");
        }

        // Probar el login de un coach
        Coach coach = login.loginCoach(1, "password_de_coach");
        if (coach != null) {
            System.out.println("Coach autenticado: " + coach.getName());
            System.out.println("Pacientes del coach:");
            for (Deportista paciente : coach.getPacientes()) {
                System.out.println("- " + paciente.getName());
            }
        } else {
            System.out.println("Credenciales de coach incorrectas.");
        }
    }
    }

