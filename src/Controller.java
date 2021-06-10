import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller extends ThreadHandler {


    @FXML
    public LineChart RealTimeLineChart;
    public LineChart SavedDataLineChart;
    public Label BPMID;



    public void tabChanged() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void startRealTimeEKG() {
        Algorithm.getAlgorithmOBJ().setupChart(RealTimeLineChart);
        makeThread();
        threadStart();

    }
    public void stopRealTimeEKG() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void findData() {
    }
}
