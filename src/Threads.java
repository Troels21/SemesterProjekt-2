import javafx.application.Platform;
import javafx.scene.chart.LineChart;

public class Threads {

    LineChart t;

    public Threads(LineChart linechart) {
        t = linechart;
    }

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            {
                synchronized (this) {
                    while (ThreadHandler.getShouldMyThreadBeRuning()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //SQL Inject FilterOBJ.ValueA
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //SQL Inject FilterOBJ.ValueB
                    }
                }
            }
        }
    });


    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        Algorithm.getAlgorithmOBJ().setupChart(t);
                        while (ThreadHandler.getShouldMyThreadBeRuning()) {
                            try {
                                wait();
                                Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueA());
                                wait();
                                Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueB());
                            } catch (InterruptedException g) {
                                g.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    });


    Thread t3 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
                SerialPortClass.getSerialPortOBJ().openPort();
                while (ThreadHandler.getShouldMyThreadBeRuning()) {
                    Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueA);
                    notifyAll();
                    Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueB);
                    notifyAll();
                    //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

                }
                SerialPortClass.getSerialPortOBJ().closePort();
            }
        }
    });
}
