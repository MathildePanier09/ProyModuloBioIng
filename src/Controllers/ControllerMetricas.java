package Controllers;

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
import users.Deportista;

public class ControllerMetricas {

    @FXML
    private Label metricasLbl;

    @FXML
    private Label deportistaLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private Button entrarBttn;
    
    private Deportista deportista;
    

    public ControllerMetricas(Deportista deportista) {
        super();
        this.deportista = deportista;
    }

    @FXML
    private void initialize() {
        // Inicializar el ComboBox con las opciones
        typeCombo.getItems().addAll("tipo1", "tipo2", "tipo3");
        deportistaLbl.setText("Nombre : " + deportista.getId());

        // Establecer el botón como invisible inicialmente
        entrarBttn.setVisible(false);

        // Añadir listener al ComboBox
        typeCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            // Hacer visible el botón solo si se ha seleccionado un elemento
            entrarBttn.setVisible(newValue != null);
        });
    }

    @FXML
    void loginAction(ActionEvent event) {
        String option = typeCombo.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/Graficas.fxml"));
            ControllerGrafica controlLogin = new ControllerGrafica(option);

            Stage actualStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
