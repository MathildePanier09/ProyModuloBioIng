package BBDD.create;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

            // Crear tabla DEPORTISTA si no existe
            String createDeportistaTableSql = "CREATE TABLE IF NOT EXISTS DEPORTISTA (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), pass VARCHAR(255))";
            try (PreparedStatement pstmt = conn.prepareStatement(createDeportistaTableSql)) {
                pstmt.executeUpdate();
            }

            // Crear tabla COACH_DEPORTISTA si no existe
            String createCoachDeportistaTableSql = "CREATE TABLE IF NOT EXISTS COACH_DEPORTISTA (coach_id INT, deportista_id INT, FOREIGN KEY (coach_id) REFERENCES COACH(id), FOREIGN KEY (deportista_id) REFERENCES DEPORTISTA(id))";
            try (PreparedStatement pstmt = conn.prepareStatement(createCoachDeportistaTableSql)) {
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

    public int createDeportista(String name, String password) {
        int newDeportistaId = 0;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String insertSql = "INSERT INTO DEPORTISTA (name, pass) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, password);

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newDeportistaId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newDeportistaId;
    }

    public void relateCoachToDeportista(int coachId, int deportistaId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String insertSql = "INSERT INTO COACH_DEPORTISTA (coach_id, deportista_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setInt(1, coachId);
                pstmt.setInt(2, deportistaId);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Create creator = new Create();

        // Ejemplo de creación de un coach y un deportista
        int coachId = creator.createCoach("Coach2", "Pass");
        int deportistaId = creator.createDeportista("Deportista2", "Pass");

        // Ejemplo de relación entre coach y deportista
        if (coachId != 0 && deportistaId != 0) {
            creator.relateCoachToDeportista(coachId, deportistaId);
        }
    }
}