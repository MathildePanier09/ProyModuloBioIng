package Controllers;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import BBDD.create.Create;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import users.Coach;
import users.Deportista;

public class ControllerDepAsignar implements Initializable {

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

	@FXML
	private Button salirBttn;

	private Coach coach;

	public ControllerDepAsignar(Coach coach) {
		this.coach = coach;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDeportistas();

		// Botón deshabilitado si no hay deportista seleccionado en el ComboBox
		asignarBttn.disableProperty().bind(typeCombo.getSelectionModel().selectedItemProperty().isNull());
	}

	private void loadDeportistas() {
		List<String> deportistas = Create.getAllDeportistas();
		typeCombo.setItems(FXCollections.observableArrayList(deportistas));
	}

	@FXML
	void asignarAction(ActionEvent event) {
		String selectedDep = typeCombo.getValue();
		Deportista deportista = Create.getDeportistaByName(selectedDep);
		if (deportista != null) {
			String idDepStr = deportista.getName(); 
			String idCoachStr = coach.getId();
			int idDep = Integer.parseInt(idDepStr);
			int idCoach = Integer.parseInt(idCoachStr);
			Create.relateCoachToDeportista(idCoach, idDep);

			// Después de relacionar el deportista, ocultamos el botón
			asignarBttn.setVisible(false);
		}
	}
	@FXML
	void salirAction(ActionEvent event) {
		try {
			FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/CoachView.fxml"));
			ControllerCoachView controlLogin = new ControllerCoachView(coach);

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
	}}
