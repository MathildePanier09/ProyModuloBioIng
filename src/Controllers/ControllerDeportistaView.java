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
import users.Deportista;

public class ControllerDeportistaView {

	Deportista deportista;
	
    public ControllerDeportistaView(Deportista deportista) {
		//super();
		this.deportista = deportista;
	}
    @FXML
    private Label deportistaViewLbl;

    @FXML
    private Label deportistaIdLbl;

    @FXML
    private Label deportistaIdLabel;

    @FXML
    private Button visualizeBttn;

    @FXML
    private Button salirBttn;
    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void initialize() {
    	
		String idDep = deportista.getId();
		String nombre = deportista.getName();
		deportistaIdLbl.setText("Hola " + nombre + " (D"+idDep+")");
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
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/Metricas.fxml"));
			ControllerMetricas controlLogin = new ControllerMetricas(deportista);

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
