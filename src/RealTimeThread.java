import javafx.scene.chart.LineChart;

public class RealTimeThread extends Thread {
    LineChart t;

    RealTimeThread(LineChart linechart){
        t=linechart;
    }

    @Override
    public void run() {
        Algorithm.getAlgorithmOBJ().setupChart(t);
        while(ThreadHandler.getShouldMyThreadBeRuning())
        try {
            wait();
            Algorithm.getAlgorithmOBJ().populateChart(Filter.ValueA);
            wait();
            Algorithm.getAlgorithmOBJ().populateChart(Filter.ValueB);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

