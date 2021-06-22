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
    //Singleton af Algorithme Objekt
    private static final Algorithm AlgorithmOBJ = new Algorithm();
    private Algorithm() {}
    public static Algorithm getAlgorithmOBJ() {
        return AlgorithmOBJ;
    }

    //Placeholders til at gemme BPM og CPR
    private double BPM;
    private String CPR = "";

    //Laver en XYChartSerie
    private final XYChart.Series EKGSerie = new XYChart.Series();

    //Opsætning af Linechart, Sletter Gamle Værdier og tilføjer nye
    public void setupChart(LineChart lineChart) {
        EKGSerie.getData().clear();
        lineChart.getData().clear();
        EKGSerie.setName("ECG");
        lineChart.getData().add(EKGSerie);
    }

    //Metode til at udfylde LineChart i intervaller, sletter gamle værdier og tilføjer nye
    public void populateChart(int[] array) {
        EKGSerie.getData().clear();
        for (int i = 0; i < (array.length - 1); i++) {
            EKGSerie.getData().add(new XYChart.Data(i, array[i]));
        }
    }

    //Metode til at udfylde et LineChart på en gang, sletter gamle værdier og tilføjer nye
    public void populateChartArraylist(ArrayList arraylist) {
        EKGSerie.getData().clear();
        for (int i = 0; i < arraylist.size() - 1; i++) {
            EKGSerie.getData().add(new XYChart.Data(i, arraylist.get(i)));
        }
    }

    //Algoritme til at udregne BPM
    //Registrer et punkt over 70% af max som et puls slag, men sørger for at der skal være gået minimum 220 målinger
    // mellem registrede pulsslag
    public void BPMalgo(int[] array, Label bpmid) {
        double counter = 0;
        int lastPulsePoint = 0;
        int seventyprocent = Arrays.stream(array).max().getAsInt();
        for (int s = 0; s < array.length - 1; s++) {
            if (array[s] > seventyprocent * 0.7 && (lastPulsePoint - s) <= -220) {
                counter++;
                lastPulsePoint = s;
            }
        }
        setBPM(counter / 5 * 60);  //Upscaler pulsslag til en estimeret BPM
        bpmid.setText(String.valueOf((getBPM()))); //Opdaterer værdien af BPM på sin label
    }

    //Metode til at kontrollere om CPR er et 10 cifret tal
    public Boolean checkCPR(String string) {
        if (!string.equals("") && string.length() == 10 && string.matches("[0-9]+")) {
            try {
                //int hello = Integer.parseInt(string);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    //Imperativ JavaFX programmering, der laver en Popup boks med en string og en OK knap
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

    //Getters and Setters
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
