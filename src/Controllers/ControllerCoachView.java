package Controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import users.Coach;
import users.Deportista;

public class ControllerCoachView {

	Coach coach;

	public ControllerCoachView(Coach coach) {
		this.coach=coach;
	}

	@FXML
	private Label coachViewLbl;

	@FXML
	private Label coachIdLbl;

	@FXML
	private Label deportistaIdLabel;

	@FXML
	private Button gestionBttn;

	@FXML
	private Button visualizeBttn;

	@FXML
	private Button salirBttn;

	@FXML
	void gestionarAction(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/GestionDeportistas.fxml"));
			ControllerGestionDeportistas controlLogin = new ControllerGestionDeportistas(coach);

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

	@FXML
	void salirAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/welcome.fxml"));
			ControllerWelcome controllerWelcome = new ControllerWelcome();
			loader.setController(controllerWelcome);
			Parent root = loader.load();

			// Obtener la escena actual y cerrar la ventana actual
			Stage stage = (Stage) salirBttn.getScene().getWindow();
			stage.close();

			// Abrir la nueva ventana
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void visualizeAction(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/SelectDep.fxml"));
			ControllerSelectDep controlLogin = new ControllerSelectDep();

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
	@FXML
	void initialize() {

		String idCoach = coach.getId();
		String nombre = coach.getName();
		coachIdLbl.setText("Hola Coach " + nombre + " (C"+idCoach+")");
	}

}

