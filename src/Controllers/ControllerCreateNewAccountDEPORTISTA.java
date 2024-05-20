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
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/GestionDeportistas.fxml"));
			ControllerGestionDeportistas controllerGestionDeportistas = new ControllerGestionDeportistas(coach);
			loader.setController(controllerGestionDeportistas);
			Parent root = loader.load();
			Stage currentStage = null;
			// Close the current window
			currentStage.close();

			// Open the new window
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Coach coach;

	@FXML
	private boolean isRegistered = false; // Variable para verificar si el usuario ya se ha registrado

	public void signUp(ActionEvent event) {
		if (!isRegistered) { // Verificar si el usuario ya se ha registrado
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

			isRegistered = true; // Marcar que el usuario ya se ha registrado
			signUpBttn.setVisible(false); // Ocultar el botón de registro
		} else {
			// Mostrar un mensaje de error o advertencia si el usuario ya se ha registrado
			System.out.println("Ya has sido registrado. No puedes registrarte nuevamente.");
		}
	}


}