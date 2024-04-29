package Controllers;

import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerMetricas {


	@FXML
	private Label metricasLbl;

	@FXML
	private Label deportistaLbl;

	@FXML
	private Label deportistaIdLbl;

	@FXML
	private Label typeLbl;

	@FXML
	private ComboBox<?> typeCombo;

	@FXML
	private Button entrarBttn;

	@FXML
	void loginAction(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/Graficas.fxml"));
			ControllerCreateNewAccount controlLogin = new ControllerCreateNewAccount();

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
	void selectExercise(ActionEvent event) {

	}

}


