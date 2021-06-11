import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads{
    private Label label;
    private LineChart lineChart;

    private ExecutorService SqlHandler = Executors.newSingleThreadExecutor();

    private Thread MotherloardThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Platform.runLater(()->Algorithm.getAlgorithmOBJ().setupChart(lineChart));
            SerialPortClass.getSerialPortOBJ().openPort();
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().getValueA());
                System.out.println("Filter A");
                SerialPortClass.getSerialPortOBJ().setAorB(true);
                Platform.runLater(platformthread);
                SqlHandler.execute(sqlThread);

                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().getValueB());
                System.out.println("Filter B");
                SerialPortClass.getSerialPortOBJ().setAorB(false);
                Platform.runLater(platformthread);
                SqlHandler.execute(sqlThread);

                //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoderz
            }
            SerialPortClass.getSerialPortOBJ().closePort();
        }
    });

    private Thread platformthread = new Thread(() -> {
        if (SerialPortClass.getSerialPortOBJ().getAorB()) {
            Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA());
            Algorithm.getAlgorithmOBJ().BPMalgo(SerialPortClass.getSerialPortOBJ().getValueA(), label);
            System.out.println("platform A");
        } else {
            Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB());
            Algorithm.getAlgorithmOBJ().BPMalgo(SerialPortClass.getSerialPortOBJ().getValueB(), label);
            System.out.println("platform B");
        }
    });

    private Thread sqlThread = new Thread(() -> {
        if (SerialPortClass.getSerialPortOBJ().getAorB()) {
            System.out.println("SQl A");
            SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().getValueA());
        } else {
            System.out.println("SQl B");
            SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().getValueB());
        }
    });


/*
    Thread t1 = new Thread(() -> {
        synchronized (obj) {
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                try {
                    obj.wait();
                    SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().getValueA());
                    obj.wait();
                    SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().getValueB());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    Thread t2 = new Thread(() -> {
        synchronized (obj) {
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                try {
                    obj.wait();
                    Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA());
                    obj.wait();
                    Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread t3 = new Thread(() -> {
        synchronized (obj) {
            SerialPortClass.getSerialPortOBJ().openPort();
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueA);
                obj.notifyAll();
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueB);
                obj.notifyAll();
                //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder
            }
            SerialPortClass.getSerialPortOBJ().closePort();
        }
    });*/

    public ExecutorService getSqlHandler() {
        return SqlHandler;
    }

    public void setSqlHandler(ExecutorService sqlHandler) {
        SqlHandler = sqlHandler;
    }

    public Thread getMotherloardThread() {
        return MotherloardThread;
    }

    public void setMotherloardThread(Thread motherloardThread) {
        MotherloardThread = motherloardThread;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public LineChart getLineChart() {
        return lineChart;
    }

    public void setLineChart(LineChart lineChart) {
        this.lineChart = lineChart;
    }
}
