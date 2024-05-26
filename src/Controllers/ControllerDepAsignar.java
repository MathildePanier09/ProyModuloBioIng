package Controllers;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import BBDD.create.Create;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import users.Coach;
import users.Deportista;

public class ControllerDepAsignar implements Initializable{

	@FXML
	private Label metricasLbl;

	@FXML
	private Label deportistaIdLbl;

	@FXML
	private Label typeLbl;

	@FXML
	private ComboBox<String> typeCombo;

	@FXML
	private Button asignarBttn;

	Coach coach; 

	public ControllerDepAsignar(Coach coach) {
		//super();
		this.coach = coach;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDeportistas();
	}

	private void loadDeportistas() {
		List<String> deportistas = Create.getAllDeportistas();
		typeCombo.setItems(FXCollections.observableArrayList(deportistas));
	}
	@FXML
	void asignarAction(ActionEvent event) {
		String selectedDep = typeCombo.getValue();
		Deportista  deportista = Create.getDeportistaByName(selectedDep);
		String idDepStr = deportista.getName();
		String idCoachStr = coach.getId();
		int idDep = Integer.parseInt(idDepStr);
		int idCoach = Integer.parseInt(idCoachStr);
		Create.relateCoachToDeportista( idCoach, idDep);
	}


}



