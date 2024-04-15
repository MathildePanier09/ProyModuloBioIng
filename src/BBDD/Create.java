package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import users.Coach;
import users.Deportista; // Cambio de Paciente a Deportista

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

    public int createDeportista(String name, String password) { // Cambio de createPaciente a createDeportista
        int newDeportistaId = 0; // Cambio de newPacienteId a newDeportistaId
        Connection conn = null;
        PreparedStatement pstmtInsert = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String insertSql = "INSERT INTO DEPORTISTA (name, pass) VALUES (?, ?)"; // Cambio de PACIENTE a DEPORTISTA
            pstmtInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmtInsert.setString(1, name);
            pstmtInsert.setString(2, password);

            int rowsInserted = pstmtInsert.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    newDeportistaId = generatedKeys.getInt(1); // Cambio de newPacienteId a newDeportistaId
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
        }

        return newDeportistaId; // Cambio de newPacienteId a newDeportistaId
    }

    public void relateCoachToDeportista(int coachId, int deportistaId) { // Cambio de relateCoachToPaciente a relateCoachToDeportista
        Connection conn = null;
        PreparedStatement pstmtInsert = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String insertSql = "INSERT INTO COACH_DEPORTISTA (coach_id, deportista_id) VALUES (?, ?)"; // Cambio de COACH_PACIENTE a COACH_DEPORTISTA
            pstmtInsert = conn.prepareStatement(insertSql);
            pstmtInsert.setInt(1, coachId);
            pstmtInsert.setInt(2, deportistaId); // Cambio de pacienteId a deportistaId

            pstmtInsert.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
        }
    }

    public static void main(String[] args) {
        Create creator = new Create();
       
        // Ejemplo de creación de un coach y un deportista
        int coachId = creator.createCoach("CoachName", "CoachPass");
        int deportistaId = creator.createDeportista("DeportistaName", "DeportistaPass"); // Cambio de PacienteName a DeportistaName

        // Ejemplo de relación entre coach y deportista
        if (coachId != 0 && deportistaId != 0) { // Cambio de pacienteId a deportistaId
            creator.relateCoachToDeportista(coachId, deportistaId); // Cambio de relateCoachToPaciente a relateCoachToDeportista
        }
    }
}
