import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerEKG extends Threads {

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

    //Metode der slukker den kørende tråd efter aktuelle måling, og overfører CPR mellem scener
    public void tabChanged() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
        setCPRid1(Algorithm.getAlgorithmOBJ().getCPR());
        setCPRid2(Algorithm.getAlgorithmOBJ().getCPR());
    }


    public void startRealTimeEKG() {
        if (!ThreadHandler.getShouldMyThreadBeRuning()) { //Hvis trådene kører må de ikke kunne startes igen
            if (Algorithm.getAlgorithmOBJ().checkCPR(Algorithm.getAlgorithmOBJ().getCPR())) { //CPRCheck
                SQL.getSqlOBJ().makePatientMeasurement(Algorithm.getAlgorithmOBJ().getCPR()); // Laver Patient
                setLabel(getBPMID()); //Overfører Label fra FXML til tråd klassen
                setLineChart(getRealTimeLineChart()); //Overfører Linechart fra FXML til tråd klassen
                ThreadHandler.setShouldMyThreadBeRuning(true);
                ThreadHandler.getThreadHandlerOBJ().makeNewThreadIfClosed(getMotherloardThread()); //Laver en ny tråd af motherloadThread og starter den
            } else {
                Algorithm.getAlgorithmOBJ().textBox("Syntax Error in :CPR:   Try pressing Enter"); //Fejl boks
            }
        }
    }

    public void stopRealTimeEKG() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void findData() throws IOException {
        try {
            if (SQL.getSqlOBJ().doesPatientExsist(Algorithm.getAlgorithmOBJ().getCPR())) { //Checker om patienten eksistere
                if (Algorithm.getAlgorithmOBJ().checkCPR(Algorithm.getAlgorithmOBJ().getCPR())) { //CPR Check
                    try {
                        SQL.getSqlOBJ().FindMeasureIDWhereCPRRead(Algorithm.getAlgorithmOBJ().getCPR()); //Finder Measurement hvor CPR er
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    Algorithm.getAlgorithmOBJ().textBox("Syntax Error    try pressing Enter"); //Fejl boks
                }
            } else {
                Algorithm.getAlgorithmOBJ().textBox("Patient does not exist"); //Fejl boks
            }
        } catch (SQLException throwables) {
            Algorithm.getAlgorithmOBJ().textBox("Patient does not exist"); //Fejl boks
        }

    }

    public void showData() {
        SQL.getSqlOBJ().readToDataArray();  //Læser til dataarray
        Algorithm.getAlgorithmOBJ().setupChart(getSavedDataLineChart()); //Indstiller Linechart
        Algorithm.getAlgorithmOBJ().populateChartArraylist(SQL.getSqlOBJ().getDataArray()); //Opdatere Grafen
    }

    //Metoder til at klikke enter på textbokst
    public void onEnter1() {
        Algorithm.getAlgorithmOBJ().setCPR(getCPRid1().getText());
        Algorithm.getAlgorithmOBJ().textBox("CPR saved");
    }

    public void onEnter2() {
        Algorithm.getAlgorithmOBJ().setCPR(getCPRid2().getText());
        Algorithm.getAlgorithmOBJ().textBox("CPR saved");
    }

    //Getters and Setters
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
