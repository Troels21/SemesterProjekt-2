import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerEKG extends Threads {

    @FXML
    private Label MeasurementID;
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
    static private volatile int dataPicked = 0;

    public void tabChanged() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
        setCPRid1(Algorithm.getAlgorithmOBJ().getCPR());
        setCPRid2(Algorithm.getAlgorithmOBJ().getCPR());
    }

    public void startRealTimeEKG() {
        if (!ThreadHandler.getShouldMyThreadBeRuning()) {
            if (Algorithm.getAlgorithmOBJ().checkCPR(Algorithm.getAlgorithmOBJ().getCPR())) {
                //Der er ret mange metodekald ? kommentarer pls :D
                SQL.getSqlOBJ().makePatientMeasurement(Algorithm.getAlgorithmOBJ().getCPR());
                setLabel(getBPMID());
                setLineChart(getRealTimeLineChart());
                ThreadHandler.setShouldMyThreadBeRuning(true);
                ThreadHandler.getThreadHandlerOBJ().makeNewThreadIfClosed(getMotherloardThread());
            } else {
                Algorithm.getAlgorithmOBJ().textBox("Syntax Error in :CPR:   Try pressing Enter");
            }
        }
    }

    public void stopRealTimeEKG() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void findData() throws IOException {
        try {
            if (SQL.getSqlOBJ().doesPatientExsist(Algorithm.getAlgorithmOBJ().getCPR())) {
                if (Algorithm.getAlgorithmOBJ().checkCPR(Algorithm.getAlgorithmOBJ().getCPR())) {
                    try {
                        SQL.getSqlOBJ().FindMeasureIDWhereCPRRead(Algorithm.getAlgorithmOBJ().getCPR());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    Algorithm.getAlgorithmOBJ().textBox("Syntax Error    try pressing Enter");
                }
            } else {
                Algorithm.getAlgorithmOBJ().textBox("Patient does not exist");
            }
        } catch (SQLException throwables) {
            Algorithm.getAlgorithmOBJ().textBox("Patient does not exist");
        }

    }

    public void showData() {
        SQL.getSqlOBJ().readToDataArray();
        Algorithm.getAlgorithmOBJ().setupChart(getSavedDataLineChart());
        Algorithm.getAlgorithmOBJ().populateChartArraylist(SQL.getSqlOBJ().getDataArray());
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
