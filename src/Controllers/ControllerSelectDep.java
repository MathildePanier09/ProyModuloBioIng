package Controllers;

import BBDD.create.Create;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import users.Coach;
import users.Deportista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSelectDep implements Initializable {

    @FXML
    private Label metricasLbl;

    @FXML
    private Label deportistaIdLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private Button visualizeBttn;

    private Deportista selectedDeportista;
    @FXML
    private Button salirBttn;

    Coach coach; 
    
    
    public ControllerSelectDep(Coach coach) {
		super();
		this.coach = coach;
	}

	@Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDeportistas();
    }

    private void loadDeportistas() {
    	int coachId = Integer.parseInt(coach.getId());
        List<String> deportistas = Create.getDeportistasByCoach(coachId);
        typeCombo.setItems(FXCollections.observableArrayList(deportistas));
    }

    @FXML
    void visualizeAction(ActionEvent event) {
        String selectedName = typeCombo.getValue();
       Deportista  deportista = Create.getDeportistaByName(selectedName);
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

    
    public Deportista getSelectedDeportista() {
        return selectedDeportista;
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
       
    }

    
}
