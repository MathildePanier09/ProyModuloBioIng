package Controllers;

import java.io.IOException;

import Repository.RepoCredentialsPaciente;
import application.controller.signUp.ControllerSignUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import users.Deportista;

public class controllerLogin {


	@FXML
	private TextField id;

	@FXML
	private TextField password;

	@FXML
	private Button entrarBttn;

	@FXML
	void login(ActionEvent event) {
	    try {
	        // Recuperar nombre de usuario y contraseña ingresados por el usuario
	        String nombreUsuario = id.getText(); // Reemplaza "usernameTextField" con el nombre de tu campo de texto de usuario en la interfaz gráfica
	        String contrasena = password.getText(); // Reemplaza "passwordTextField" con el nombre de tu campo de texto de contraseña en la interfaz gráfica
	        
	        // Buscar al paciente por su nombre de usuario
	        Deportista paciente = RepoCredentialsPaciente.findPacienteByName(nombreUsuario);
	        
	        if (paciente != null && paciente.getPassword().equals(contrasena)) {
	            // Las credenciales son válidas, puedes proceder con la acción deseada
	            
	        	try {
	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/PacienteView.fxml"));

	    			ControllerVistaPaciente controllerSignUp = new ControllerVistaPaciente(paciente);
	    			loader.setController(controllerSignUp);

	    			Parent root = loader.load();

	    			Stage stage = new Stage();
	    			stage.setScene(new Scene(root));

	    			stage.initModality(Modality.WINDOW_MODAL);
	    			stage.initOwner(((Node) (event.getSource())).getScene().getWindow());

	    			stage.show();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	        } else {
	            // Las credenciales son inválidas, muestra un mensaje de error o realiza alguna acción apropiada
	            System.out.println("Nombre de usuario o contraseña incorrectos.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
