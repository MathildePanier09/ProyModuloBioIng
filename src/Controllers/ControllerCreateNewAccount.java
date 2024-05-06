package Controllers;

import BBDD.create.Create;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerCreateNewAccount {


    @FXML
    private Label createNewAcountLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField nameTxtField;

    @FXML
    private Label passLbl;

    @FXML
    private TextField passTxtField;

    @FXML
    private CheckBox deportistaChosen;

    @FXML
    private CheckBox coachChosen;

    @FXML
    private Label nuevoIdLbl;

    @FXML
    private Label nuevoId;

    @FXML
    private Button signUpBttn;

    @FXML
    private Button salirBttn;
    @FXML
    void delete(ActionEvent event) {

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
    void initialize() {
    	
        signUpBttn.disableProperty().bind(
            deportistaChosen.selectedProperty().not().and(coachChosen.selectedProperty().not())
            .or(deportistaChosen.selectedProperty().and(coachChosen.selectedProperty()))
        );
     // Ocultar los labels nuevoIdLbl y nuevoId
        nuevoIdLbl.setVisible(false);
        nuevoId.setVisible(false);
    }

    // TRUE si se ha elegido deportista FALSE si coach
    private boolean RoleChosen() {
    	boolean role= false;
        if( deportistaChosen.isSelected()) {
        	role = true;
        }
        if (coachChosen.isSelected()) {
        	role=false;
        }
        return role;
    }

    @FXML
    void signUp(ActionEvent event) {
    	String name = nameTxtField.getText();
    	String pass = passTxtField.getText();
    	
    	Create.createTablesIfNotExist();
        if (RoleChosen() == true) { // si se ha elegido deportista
       int idDep =  Create.createDeportista(name,pass) ;
          String idNuevo = "D"+ idDep;
         nuevoIdLbl.setVisible(true);
         nuevoId.setText(idNuevo); // Asignar el valor de idNuevo al label nuevoId
         nuevoId.setVisible(true);
           
        } if(RoleChosen()== false) {
        	 int idCoach =  Create.createCoach(name,pass);
        	 String idNuevo = "C"+ idCoach;
        nuevoIdLbl.setVisible(true);
        nuevoId.setText(idNuevo); // Asignar el valor de idNuevo al label nuevoId
        nuevoId.setVisible(true);
        }
    }
}
