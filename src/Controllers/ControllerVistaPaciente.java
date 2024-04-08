package Controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import users.Paciente;

public class ControllerVistaPaciente {
	

    public ControllerVistaPaciente( Paciente paciente) {
		super();
		this.idPaciente = idPaciente;
		this.visualizeBttn = visualizeBttn;
		this.salirBttn = salirBttn;
		this.paciente = paciente;
	}

	@FXML
    private Label idPaciente;

    @FXML
    private Button visualizeBttn;

    @FXML
    private Button salirBttn;
    
    private Paciente paciente;

    @FXML
    void salirAction(ActionEvent event) {
    	Stage previousStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
		previousStage.close();
    }

    @FXML
    void visualizeAction(ActionEvent event) {

    }
    public void initialize(URL arg0, ResourceBundle arg1) {
		idPaciente.setText(paciente.getId());
		
}
}

