import com.mysql.cj.protocol.Message;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.awt.*;

public class Algorithm {
    private int timer = 0;

    private static Algorithm AlgorithmOBJ = new Algorithm();

    private Algorithm() {
    }

    public static Algorithm getAlgorithmOBJ() {
        return AlgorithmOBJ;
    }

    static XYChart.Series EKGSerie = new XYChart.Series();

    public void setupChart(LineChart linechart) {
        EKGSerie.getData().clear();
        EKGSerie.setName("ECG");
        linechart.getData().add(EKGSerie);
    }

    public void populateChart(int array[]) {
        EKGSerie.getData().clear();
        for (int i = 0; i < (array.length - 1); i++) {
            EKGSerie.getData().add(new XYChart.Data(i, array[i]));
            setTimer((getTimer() + 1));
        }
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    //BPM algoritme

}
