import javafx.application.Platform;
import javafx.scene.chart.LineChart;

public class RealTimeThread extends Thread implements SensorObserver {
    LineChart t;

    RealTimeThread(LineChart linechart){
        t=linechart;
    }

    @Override
    public void run() {
            //Platform.runLater();
            Algorithm.getAlgorithmOBJ().setupChart(t);
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                //SensorObserver.wait();
                Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueA());
                //wait();
                Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueB());
            }
    }

    @Override
    public void notify(Filter filter) {

    }
}

