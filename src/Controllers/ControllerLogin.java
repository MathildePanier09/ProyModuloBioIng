package Controllers;

import BBDD.Login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import users.Coach;
import users.Deportista;

public class ControllerLogin {
	  @FXML
	    private Label loginLbl;

	    @FXML
	    private Label idLbl;

	    @FXML
	    private TextField idTxtField;

	    @FXML
	    private Label passLbl;

	    @FXML
	    private TextField passTxtField;

	    @FXML
	    private Button entrarBttn1;

	    @FXML
	    private Button salirBttn;
    @FXML
    void loginAction(ActionEvent event) {
        String id = idTxtField.getText();
        String pass = passTxtField.getText();

        char role = id.charAt(0); // Recupera el primer caracter del ID

        Login login = new Login();
        if (role == 'D') { // Si el ID indica que es un deportista
            int idNumber = Integer.parseInt(id.substring(1)); // Extrae el número de ID del deportista
            Deportista deportista = login.loginDeportista(idNumber, pass); // Usa la función de loginDeportista
            if (deportista != null) {
                // Hacer algo si el inicio de sesión es exitoso para el deportista
            	try {
        			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/DeportistaView.fxml"));
        			ControllerDeportistaView controlLogin = new ControllerDeportistaView(deportista);

        			Stage actualStage=(Stage) ((Node) event.getSource()).getScene().getWindow();

        			loaderLogin.setController(controlLogin);
        			Parent rootLogin = loaderLogin.load();

        			Stage stage = new Stage();
        			stage.setScene(new Scene(rootLogin));
        			stage.show();

        			actualStage.close();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            	
            } 
        } else if (role == 'C') { // Si el ID indica que es un coach
            int idNumber = Integer.parseInt(id.substring(1)); // Extrae el número de ID del coach
            Coach coach = login.loginCoach(idNumber, pass); // Usa la función de loginCoach
            if (coach != null) {
            	try {
        			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/CoachView.fxml"));
        			ControllerCoachView controlLogin = new ControllerCoachView(coach);

        			Stage actualStage=(Stage) ((Node) event.getSource()).getScene().getWindow();

        			loaderLogin.setController(controlLogin);
        			Parent rootLogin = loaderLogin.load();

        			Stage stage = new Stage();
        			stage.setScene(new Scene(rootLogin));
        			stage.show();

        			actualStage.close();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            } else {
                // Hacer algo si el inicio de sesión falla para el coach
            }
        } else {
            // Hacer algo si el ID no es válido
        }
    }
}
