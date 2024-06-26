package Login;

import java.util.Vector;

import Controllers.ControllerCreateNewAccount;
import Controllers.ControllerLogin;
import Controllers.ControllerWelcome;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/welcome.fxml"));

			ControllerWelcome controllerWelcome = new ControllerWelcome();

			loader.setController(controllerWelcome);
			Parent root = loader.load();

			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}