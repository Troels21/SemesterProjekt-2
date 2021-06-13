import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller extends Threads {

    @FXML
    private LineChart RealTimeLineChart;
    @FXML
    private LineChart SavedDataLineChart;
    @FXML
    private Label BPMID;
    @FXML
    private TextField CPRid2;
    @FXML
    private TextField CPRid1;


    public void tabChanged() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
        setCPRid1(Algorithm.getAlgorithmOBJ().getCPR());
        setCPRid2(Algorithm.getAlgorithmOBJ().getCPR());
    }

    public void startRealTimeEKG() {
        if (Algorithm.getAlgorithmOBJ().checkCPR(Algorithm.getAlgorithmOBJ().getCPR())) {
            SQL.getSqlOBJ().makePatientMeasurement(Algorithm.getAlgorithmOBJ().getCPR());
            setLabel(getBPMID());
            setLineChart(getRealTimeLineChart());
            ThreadHandler.setShouldMyThreadBeRuning(true);
            ThreadHandler.getThreadHandlerOBJ().startthreadifclose(getMotherloardThread());
        } else {
            Algorithm.getAlgorithmOBJ().textBox("Syntax Error in :CPR:");
        }
    }

    public void stopRealTimeEKG() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void findData() {
    }

    public void onEnter1() {
        Algorithm.getAlgorithmOBJ().setCPR(getCPRid1().getText());
        Algorithm.getAlgorithmOBJ().textBox("CPR saved");
    }

    public void onEnter2() {
        Algorithm.getAlgorithmOBJ().setCPR(getCPRid2().getText());
        Algorithm.getAlgorithmOBJ().textBox("CPR saved");
    }

    public TextField getCPRid2() {
        return CPRid2;
    }

    public void setCPRid2(String CPRid2) {
        this.CPRid2.setText(CPRid2);
    }

    public TextField getCPRid1() {
        return CPRid1;
    }

    public void setCPRid1(String CPRid1) {
        this.CPRid1.setText(CPRid1);
    }

    public LineChart getRealTimeLineChart() {
        return RealTimeLineChart;
    }

    public void setRealTimeLineChart(LineChart realTimeLineChart) {
        RealTimeLineChart = realTimeLineChart;
    }

    public LineChart getSavedDataLineChart() {
        return SavedDataLineChart;
    }

    public void setSavedDataLineChart(LineChart savedDataLineChart) {
        SavedDataLineChart = savedDataLineChart;
    }

    public Label getBPMID() {
        return BPMID;
    }

    public void setBPMID(Label BPMID) {
        this.BPMID = BPMID;
    }
}
