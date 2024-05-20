package Controllers;

import BBDD.create.Create;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import users.Coach;

public class ControllerCreateNewAccountDEPORTISTA {

    private Coach coach;
    private boolean isRegistered = false; // Variable para verificar si el usuario ya se ha registrado

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

    @FXML
    private Button salirBttn;

    public ControllerCreateNewAccountDEPORTISTA(Coach coach) {
        this.coach = coach;
    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/GestionDeportistas.fxml"));
            ControllerGestionDeportistas controlLogin = new ControllerGestionDeportistas(coach);

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

    @FXML
    private void signUp(ActionEvent event) {
        if (!isRegistered) {
            String name = nameTxtField.getText();
            String pass = passTxtField.getText();

            Create.createTablesIfNotExist();

            int idDep = Create.createDeportista(name, pass);
            int coachId = Integer.parseInt(coach.getId());
            Create.relateCoachToDeportista(coachId, idDep);
            String idNuevo = "D" + idDep;
            nuevoIdLbl.setVisible(true);
            nuevoId.setText(idNuevo);
            nuevoId.setVisible(true);

            isRegistered = true;
            signUpBttn.setVisible(false);
        } else {
            System.out.println("Ya has sido registrado. No puedes registrarte nuevamente.");
        }
    }
}
