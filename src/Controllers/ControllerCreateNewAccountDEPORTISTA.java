package Controllers;
import BBDD.create.Create;
import BBDD.delete.Delete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	Coach coach;

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
	void delete(ActionEvent event) {
	    int deportistaId = Integer.parseInt(nuevoId.getText());
	    
	    // Eliminar el deportista
	    Delete.deleteDeportista(deportistaId);
	    
	    // Eliminar la relación entre el deportista y el coach
	    int coachId = Integer.parseInt(coach.getId());
	    Delete.deleteDeportistaFromCoach(coachId, deportistaId);
	}


}

