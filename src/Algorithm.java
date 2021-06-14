import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {
    private double BPM;
    private String CPR = "";

    private static Algorithm AlgorithmOBJ = new Algorithm();

    private Algorithm() {
    }

    public static Algorithm getAlgorithmOBJ() {
        return AlgorithmOBJ;
    }

    private XYChart.Series EKGSerie = new XYChart.Series();

    public void setupChart(LineChart lineChart) {
        EKGSerie.getData().clear();
        lineChart.getData().clear();
        EKGSerie.setName("ECG");
        lineChart.getData().add(EKGSerie);
    }

    public void populateChart(int array[]) {
        EKGSerie.getData().clear();
        for (int i = 0; i < (array.length - 1); i++) {
            EKGSerie.getData().add(new XYChart.Data(i, array[i]));
        }
    }

    public void populateChartArraylist(ArrayList arraylist) {
        EKGSerie.getData().clear();
        for (int i = 0; i < arraylist.size() - 1; i++) {
            EKGSerie.getData().add(new XYChart.Data(i, arraylist.get(i)));
        }
    }

    public void BPMalgo(int array[], Label bpmid) {
        double counter = 0;
        int lastPulsePoint = 0;
        int seventyprocent = Arrays.stream(array).max().getAsInt();
        for (int s = 0; s < array.length - 1; s++) {
            if (array[s] > seventyprocent * 0.7 && (lastPulsePoint - s) <= -220) {
                counter++;
                lastPulsePoint = s;
            }
        }
        setBPM(counter / 5 * 60);
        bpmid.setText(String.valueOf((getBPM())));
    }

    public Boolean checkCPR(String string) {
        if (string != "" && string.length() == 10) {
            try {
                int hello = Integer.parseInt(string);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public void textBox(String message) {
        Label alertLabel = new Label();
        StackPane allertLayout = new StackPane();
        Stage allertStage = new Stage();
        Button allertButton = new Button();

        allertButton.setText("OK");
        alertLabel.setText(message);
        allertStage.setTitle("Alert");

        allertButton.setOnAction(p -> allertStage.close());
        allertLayout.getChildren().addAll(allertButton, alertLabel);
        Scene allertScene = new Scene(allertLayout, 200, 100);
        alertLabel.setTranslateY(-25);

        allertStage.setScene(allertScene);
        allertStage.initModality(Modality.APPLICATION_MODAL);
        allertStage.show();
    }

    public double getBPM() {
        return BPM;
    }

    public void setBPM(double BPM) {
        this.BPM = BPM;
    }


    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }
}
