package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import users.Coach;
import users.Paciente;

public class Create {
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";

   
    public int createCoach(String name, String password) {
        int newCoachId = 0;
        Connection conn = null;
        PreparedStatement pstmtInsert = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String insertSql = "INSERT INTO COACH (name, pass) VALUES (?, ?)";
            pstmtInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmtInsert.setString(1, name);
            pstmtInsert.setString(2, password);

            int rowsInserted = pstmtInsert.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    newCoachId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
        }

        return newCoachId;
    }

    public int createPaciente(String name, String password) {
        int newPacienteId = 0;
        Connection conn = null;
        PreparedStatement pstmtInsert = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String insertSql = "INSERT INTO PACIENTE (name, pass) VALUES (?, ?)";
            pstmtInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmtInsert.setString(1, name);
            pstmtInsert.setString(2, password);

            int rowsInserted = pstmtInsert.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    newPacienteId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
        }

        return newPacienteId;
    }

    public void relateCoachToPaciente(int coachId, int pacienteId) {
        Connection conn = null;
        PreparedStatement pstmtInsert = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String insertSql = "INSERT INTO COACH_PACIENTE (coach_id, paciente_id) VALUES (?, ?)";
            pstmtInsert = conn.prepareStatement(insertSql);
            pstmtInsert.setInt(1, coachId);
            pstmtInsert.setInt(2, pacienteId);

            pstmtInsert.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
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
