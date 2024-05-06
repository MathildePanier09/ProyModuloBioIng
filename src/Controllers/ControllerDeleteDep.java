package Controllers;
import java.io.IOException;

import BBDD.delete.Delete;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import users.Coach;

public class ControllerDeleteDep {

    public ControllerDeleteDep(Coach coach) {
        this.coach = coach;
    }

    private Coach coach;

    @FXML
    private Label createNewAcountLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField idDep;

    @FXML
    private Label questionLbl;

    @FXML
    private CheckBox sureLabel;

    @FXML
    private Button deleteBttn;

    @FXML
    private Button salirBttn;

  

    @FXML
    void initialize() {
        deleteBttn.setDisable(true); // El botón se inicializa deshabilitado

        // Agregamos un ChangeListener al TextField para habilitar/deshabilitar el botón "deleteBttn"
        idDep.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                deleteBttn.setDisable(newValue.trim().isEmpty()); // Deshabilita el botón si el TextField está vacío
            }
        });
    }
    private void loadCoachView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CoachView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) idDep.getScene().getWindow(); // idDep es el control que tienes en tu escena actual
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        int deportistaId = Integer.parseInt(idDep.getText());
        Delete.deleteDeportista(deportistaId);
        loadCoachView(); // Después de eliminar, cargar la ventana CoachView
    }
}
