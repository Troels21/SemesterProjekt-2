import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.Arrays;

public class Algorithm {
    private int timer = 0;
    private double BPM;

    private static Algorithm AlgorithmOBJ = new Algorithm();

    private Algorithm() {
    }

    public static Algorithm getAlgorithmOBJ() {
        return AlgorithmOBJ;
    }

    private XYChart.Series EKGSerie = new XYChart.Series();

    public void setupChart(LineChart lineChart) {
        EKGSerie.getData().clear();
        EKGSerie.setName("ECG");
        lineChart.getData().clear();
        lineChart.getData().add(EKGSerie);
    }

    public void populateChart(int array[]) {
        EKGSerie.getData().clear();
        for (int i = 0; i < (array.length - 1); i++) {
            EKGSerie.getData().add(new XYChart.Data(i, array[i]));
            setTimer((getTimer() + 1));
        }
    }

    public void BPMalgo(int array[], Label bpmid){
        double counter=0;
        int lastPulsePoint=0;
        int seventyprocent= Arrays.stream(array).max().getAsInt();
        for (int s=0; s<array.length-1;s++){
            if (array[s]> seventyprocent*0.7 && (lastPulsePoint-s)<=-220){
                counter++;
                lastPulsePoint=s;
            }
        }
        setBPM(counter/5*60);
        bpmid.setText(String.valueOf((getBPM())));

    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }


    public double getBPM() {
        return BPM;
    }

    public void setBPM(double BPM) {
        this.BPM = BPM;
    }
}
