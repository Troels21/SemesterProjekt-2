import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller extends Threads{

    @FXML
    public LineChart RealTimeLineChart;
    public LineChart SavedDataLineChart;
    public Label BPMID;
    public TextField CPRid2;
    public TextField CPRid1;
    int hello;


    public void tabChanged() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void startRealTimeEKG() {
        if(CPRid1.getText() != "" && CPRid1.getLength()==10){
            try{
                hello= Integer.parseInt(CPRid1.getText());
                setLabel(getBPMID());
                setLineChart(getRealTimeLineChart());
                ThreadHandler.setShouldMyThreadBeRuning(true);
                ThreadHandler.getThreadHandlerOBJ().startthreadifclose(getMotherloardThread());
            } catch (NumberFormatException e) {
                System.out.println("Skriv kun tal");
            }
            System.out.println("Skriv et CPR");
        }

    }

    public void stopRealTimeEKG() {
        ThreadHandler.setShouldMyThreadBeRuning(false);
    }

    public void findData() {
    }

    public void onEnter1() {
        System.out.println("hello");
    }

    public void onEnter2() {
        System.out.println("hello");
    }

    public TextField getCPRid2() {
        return CPRid2;
    }

    public void setCPRid2(TextField CPRid2) {
        this.CPRid2 = CPRid2;
    }

    public TextField getCPRid1() {
        return CPRid1;
    }

    public void setCPRid1(TextField CPRid1) {
        this.CPRid1 = CPRid1;
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
