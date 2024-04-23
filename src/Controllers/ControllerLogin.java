package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import users.Coach;
import users.Deportista;

public class ControllerLogin implements Initializable{
	

    @FXML
    private TextField id;

    @FXML
    private TextField password;

    @FXML
    private Button entrarBttn;
    private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
    private static final String USER = "root";
    private static final String PASS = "";


    @FXML
    void loginAction(ActionEvent event) {
        try {
            // Recuperar ID y contraseña ingresados por el usuario
            String userId = id.getText();
            String userPassword = password.getText();

            // Verificar el tipo de usuario
            if (userId.startsWith("D")) {
                // Verificar las credenciales del deportista
                Deportista deportista = authenticateDeportista(userId, userPassword);
                if (deportista != null) {
                    // Las credenciales son válidas, abrir la vista del deportista
                    openDeportistaView(event, deportista);
                } else {
                    // Las credenciales son inválidas, mostrar un mensaje de error
                    System.out.println("Credenciales incorrectas para el deportista.");
                }
            } else if (userId.startsWith("C")) {
                // Verificar las credenciales del coach
                Coach coach = authenticateCoach(userId, userPassword);
                if (coach != null) {
                    // Las credenciales son válidas, abrir la vista del coach
                    openCoachView(event, coach);
                } else {
                    // Las credenciales son inválidas, mostrar un mensaje de error
                    System.out.println("Credenciales incorrectas para el coach.");
                }
            } else {
                // El ID no sigue el formato esperado, muestra un mensaje de error
                System.out.println("El formato del ID es incorrecto. Debe comenzar con 'D' o 'C'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Deportista authenticateDeportista(String userId, String userPassword) {
        Deportista deportista = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String selectDeportistaSql = "SELECT * FROM DEPORTISTA WHERE id = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(selectDeportistaSql)) {
                pstmt.setString(1, userId);
                pstmt.setString(2, userPassword);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        deportista = new Deportista(rs.getString("name"), rs.getString("id"), rs.getString("pass"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deportista;
    }

    private Coach authenticateCoach(String userId, String userPassword) {
        Coach coach = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String selectCoachSql = "SELECT * FROM COACH WHERE id = ? AND pass = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(selectCoachSql)) {
                pstmt.setString(1, userId);
                pstmt.setString(2, userPassword);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        coach = new Coach(rs.getString("name"), rs.getString("id"), rs.getString("pass"), null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coach;
    }


    private void openCoachView(ActionEvent event, Coach coach) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/CoachView.fxml"));
            ControllerCoachView controller = new ControllerCoachView(coach);
            loader.setController(controller);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDeportistaView(ActionEvent event, Deportista deportista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/DeportistaView.fxml"));
            ControllerDeportistaView controller = new ControllerDeportistaView(deportista);
            loader.setController(controller);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
}
