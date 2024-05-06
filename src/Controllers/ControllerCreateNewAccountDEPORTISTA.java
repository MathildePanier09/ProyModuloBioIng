package Controllers;
import BBDD.create.Create;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import users.Coach;

public class ControllerCreateNewAccountDEPORTISTA {
	

    public ControllerCreateNewAccountDEPORTISTA(Coach coach) {
	//	super();
		this.coach = coach;
	}
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

    Coach coach;
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
    void signUp(ActionEvent event) {
    	String name = nameTxtField.getText();
    	String pass = passTxtField.getText();
    	
    	Create.createTablesIfNotExist();
        
       int idDep =  Create.createDeportista(name,pass) ;
       int coachId = Integer.parseInt(coach.getId());
      Create.relateCoachToDeportista(coachId, idDep);
          String idNuevo = "D"+ idDep;
         nuevoIdLbl.setVisible(true);
         nuevoId.setText(idNuevo); // Asignar el valor de idNuevo al label nuevoId
         nuevoId.setVisible(true);   
    
    }

}
