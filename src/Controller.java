import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller extends ThreadHandler {

    @FXML
    public LineChart RealTimeLineChart;
    public TextField RealTimeEKGCPR;
    public LineChart SavedDataLineChart;
    public TextField SavedEKGCPR;
    public Label BPMid;



    public void tabChanged() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void startRealTimeEKG() {
        makeThread(RealTimeLineChart);
        threadStart();
    }
    public void stopRealTimeEKG() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void findData() {
    }
}
