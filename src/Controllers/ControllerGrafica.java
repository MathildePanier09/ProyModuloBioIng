package Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import users.Coach;

public class ControllerGrafica implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private LineChart<String, Number> upperChart;

    @FXML
    private CategoryAxis upperXAxis;

    @FXML
    private NumberAxis upperYAxis;

    @FXML
    private Label paramLbl;

    @FXML
    private TextField paramTxtFld;

    private Coach coach;

    public ControllerGrafica(String option) {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String filePath = "/Users/benoitpanier/Documents/GitHub/ProyModuloBioIng/src/Datos /signal.txt"; // Ajusta esta ruta según sea necesario
        List<Double> data = readDataFromFile(filePath);
        plotData(data);
    }

    private List<Double> readDataFromFile(String filePath) {
        List<Double> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerEnded = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("# EndOfHeader")) {
                    headerEnded = true;
                    continue;
                }
                if (headerEnded && !line.startsWith("#")) {
                    String[] values = line.split("\t");
                    data.add(Double.parseDouble(values[5])); // Asumiendo que el valor de A3 está en la sexta columna
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void plotData(List<Double> data) {
        upperChart.setTitle("Datos de miSeñal");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("A3");

        for (int i = 0; i < data.size(); i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), data.get(i)));
        }

        upperChart.getData().clear();
        upperChart.getData().add(series);

        // Asignar eventos a los nodos de la gráfica
        for (XYChart.Data<String, Number> dataPoint : series.getData()) {
            Node node = dataPoint.getNode();
            if (node != null) {
                node.setOnMouseClicked(event -> showYValuePopup(dataPoint, event));
            }
        }
    }

    private void showYValuePopup(XYChart.Data<String, Number> data, MouseEvent event) {
        Number yValue = data.getYValue();
        String formattedYValue = String.format("%.2f", yValue.doubleValue());
        Tooltip tooltip = new Tooltip("Y: " + formattedYValue);
        tooltip.show(upperChart, event.getScreenX(), event.getScreenY());

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(evt -> tooltip.hide());
        delay.play();
    }

    @FXML
    void backToUser(ActionEvent event) {
        try {
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/Windows/CoachView.fxml"));
            ControllerCoachView controlLogin = new ControllerCoachView(coach);

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
