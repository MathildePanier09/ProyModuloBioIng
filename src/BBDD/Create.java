package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import users.Coach;
import users.Paciente;

public class Create {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

    public Create() {
        createTablesIfNotExist();
    }

    private void createTablesIfNotExist() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // Crear tabla COACH si no existe
            String createCoachTableSql = "CREATE TABLE IF NOT EXISTS COACH (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), pass VARCHAR(255))";
            try (PreparedStatement pstmt = conn.prepareStatement(createCoachTableSql)) {
                pstmt.executeUpdate();
            }

            // Crear tabla PACIENTE si no existe
            String createPacienteTableSql = "CREATE TABLE IF NOT EXISTS PACIENTE (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), pass VARCHAR(255))";
            try (PreparedStatement pstmt = conn.prepareStatement(createPacienteTableSql)) {
                pstmt.executeUpdate();
            }

            // Crear tabla COACH_PACIENTE si no existe
            String createCoachPacienteTableSql = "CREATE TABLE IF NOT EXISTS COACH_PACIENTE (coach_id INT, paciente_id INT, FOREIGN KEY (coach_id) REFERENCES COACH(id), FOREIGN KEY (paciente_id) REFERENCES PACIENTE(id))";
            try (PreparedStatement pstmt = conn.prepareStatement(createCoachPacienteTableSql)) {
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createCoach(String name, String password) {
        int newCoachId = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String insertSql = "INSERT INTO COACH (name, pass) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, password);

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newCoachId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCoachId;
    }

    public int createPaciente(String name, String password) {
        int newPacienteId = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String insertSql = "INSERT INTO PACIENTE (name, pass) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, password);

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newPacienteId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPacienteId;
    }

    public void relateCoachToPaciente(int coachId, int pacienteId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String insertSql = "INSERT INTO COACH_PACIENTE (coach_id, paciente_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, coachId);
                pstmt.setInt(2, pacienteId);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Create creator = new Create();

        // Ejemplo de creación de un coach y un paciente
        int coachId = creator.createCoach("CoachName", "CoachPass");
        int pacienteId = creator.createPaciente("PacienteName", "PacientePass");

        // Ejemplo de relación entre coach y paciente
        if (coachId != 0 && pacienteId != 0) {
            creator.relateCoachToPaciente(coachId, pacienteId);
        }
    }
}
