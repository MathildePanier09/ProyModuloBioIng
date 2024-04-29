package Controllers;

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


public class ControllerGestionDeportistas {

	Coach coach;
	public ControllerGestionDeportistas(Coach coach) {
		//super();
		this.coach = coach;
	}
	@FXML
	private Label gestionDeportistasLbl;

	@FXML
	private Button createDeportistaBttn;

	@FXML
	private Button borrarDeportistaBttn;

	@FXML
	private Button salirBttn;

	@FXML
	void borrarDeportista(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/deleteDeportista.fxml"));
			ControllerDeleteDep controlLogin = new ControllerDeleteDep(coach);

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
	void createDeportista(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/CreateNewAccountDEPORTISTA.fxml"));
			ControllerCreateNewAccountDEPORTISTA controlLogin = new ControllerCreateNewAccountDEPORTISTA(coach);

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
	void salir(ActionEvent event) {
		try {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
