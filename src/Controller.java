import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    public LineChart RealTimeLineChart;
    public TextField RealTimeEKGCPR;
    public LineChart SavedDataLineChart;
    public TextField SavedEKGCPR;
    public Label BPMid;

    public void tabChanged() {
        System.out.println("Change Scene");

    }

    public void startRealTimeEKG() {
    }

    public void stopRealTimeEKG() {
    }

    public void findData() {
    }
}
