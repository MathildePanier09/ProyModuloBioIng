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

public class ControllerWelcome {

	@FXML
	private Label welcomeLbl;

	@FXML
	private Button loginBttn;

	@FXML
	private Button newAccountBttn;

	@FXML
	// FUNCIONAL
	void createNewAccount(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/CreateNewAccount.fxml"));
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
	void login(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/Login.fxml"));
			ControllerLogin controlLogin = new ControllerLogin();

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
}


