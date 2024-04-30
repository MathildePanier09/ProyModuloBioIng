package Controllers;
import BBDD.delete.Delete;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    void delete(ActionEvent event) {
        int deportistaId = Integer.parseInt(idDep.getText());
        Delete.deleteDeportista(deportistaId);
    }
}
