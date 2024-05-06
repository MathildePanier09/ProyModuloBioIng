
package Controllers;
import java.io.IOException;

import BBDD.delete.Delete;
import javafx.beans.binding.Bindings;
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
    void delete(ActionEvent event) {
        int deportistaId = Integer.parseInt(idDep.getText());
        Delete.deleteDeportista(deportistaId);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/CoachView.fxml"));
            ControllerCoachView controllerWelcome = new ControllerCoachView(coach);
            loader.setController(controllerWelcome);
            Parent root = loader.load();

            // Obtener la escena actual y cerrar la ventana actual
            Stage stage = (Stage) salirBttn.getScene().getWindow();
            stage.close();

            // Abrir la nueva ventana
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void salir(ActionEvent event) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        deleteBttn.disableProperty().bind(
            sureLabel.selectedProperty().not() // Botón deshabilitado si el CheckBox no está seleccionado
            .or(Bindings.createBooleanBinding(() -> idDep.getText().isEmpty(), idDep.textProperty())) // Botón deshabilitado si el TextField está vacío
        );
    }
}
