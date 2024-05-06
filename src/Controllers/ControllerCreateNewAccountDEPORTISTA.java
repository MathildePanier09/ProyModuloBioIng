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


	private Coach coach;



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
	
    @FXML
    private Button signUpBttn;

    @FXML
    private Button salirBttn;
    @FXML
    void delete(ActionEvent event) {

    }

  


}

