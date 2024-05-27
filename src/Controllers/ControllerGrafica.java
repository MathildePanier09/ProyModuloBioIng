package Controllers;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ControllerGrafica implements Initializable{

    @FXML
    private Button backBtn;

    @FXML
    private LineChart<?, ?> upperChart;

    @FXML
    private CategoryAxis upperXAxis;

    @FXML
    private NumberAxis upperYAxis;

    @FXML
    private Label paramLbl;

    @FXML
    private TextField paramTxtFld;
    
    Coach coach;
   

    public ControllerGrafica(String option) {
		super();
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	 for (XYChart.Series<?, ?> series : upperChart.getData()) {
             for (XYChart.Data<?, ?> data : series.getData()) {
                 Node node = data.getNode();
                 if (node != null) { // Asegurarse de que el nodo no sea nulo
                     node.setOnMouseClicked(event -> showYValuePopup(data, event));
                 }
             }
         }
		
	}
    void showFrecuenciaCardiaca() {
        upperChart.setTitle("FRECUENCIA CARDIACA");
        paramLbl.setText("FRECUENCIA CARDIACA (lpm)");

        List<Double> data = generateRandomData(50, 200, 20);

        double lastValue = data.get(data.size() - 1);

        paramTxtFld.setText(String.format("%.2f", lastValue) + " lpm");

        XYChart.Series series = new XYChart.Series();
        series.setName("Frecuencia Cardiaca");

        for (int i = 0; i < data.size(); i++) {
            double frecuenciaCardiaca = data.get(i);
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), frecuenciaCardiaca));
        }

        upperChart.getData().clear();
        upperChart.getData().add(series);

        upperYAxis.setAutoRanging(false);
        upperYAxis.setLowerBound(50);
        upperYAxis.setUpperBound(200);

        // Asignar color a la serie
        series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #008000;");
    }
    
    
    private void showYValuePopup(XYChart.Data<?, ?> data, MouseEvent event) {
        // Obtener el valor del eje Y del punto
        Object yValue = data.getYValue();

        // Formatear el valor del eje Y con dos decimales
        String formattedYValue = String.format("%.2f", yValue);

        // Crear una tooltip con el valor del eje Y
        Tooltip tooltip = new Tooltip("Y: " + formattedYValue);

        // Mostrar la tooltip cerca del punto en la gráfica
        tooltip.show(upperChart, event.getScreenX(), event.getScreenY());

        // Cerrar la tooltip después de un breve período de tiempo
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(evt -> tooltip.hide());
        delay.play();
    }
    
    
    private List<Double> generateRandomData(double minValue, double maxValue, int count) {
        Random random = new Random();
        List<Double> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double value = minValue + random.nextDouble() * (maxValue - minValue);
            data.add(value);
        }
        return data;
    }

    @FXML
    void backToUser(ActionEvent event) {
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

