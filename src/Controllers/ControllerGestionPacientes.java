package Controllers;
import application.controller.login.ControllerLoginDef;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerGestionPacientes {

	@FXML
	private Button createBttn;

	@FXML
	private Button borrarBttn;

	@FXML
	private Button salirBttn;

	@FXML
	void borrar(ActionEvent event) {

	}

	@FXML
	void create(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/CreateNewAccount.fxml"));
			ControllerNewAccount controlLogin = new ControllerNewAccount();

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

	}

}
