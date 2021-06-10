import javafx.application.Platform;
import javafx.scene.chart.LineChart;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private final Object obj = new Object();
    LineChart t;
    ExecutorService SqlHandler = Executors.newSingleThreadExecutor();

    public Threads() {
    }

    Thread MotherloardThread = new Thread(new Runnable() {
        @Override
        public void run() {
            SerialPortClass.getSerialPortOBJ().openPort();
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueA);
                System.out.println("Filter A");
                SerialPortClass.getSerialPortOBJ().setAorB(true);
                Platform.runLater(platformthread);
                SqlHandler.execute(sqlThread);
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueB);
                System.out.println("Filter B");
                SqlHandler.execute(sqlThread);
                SerialPortClass.getSerialPortOBJ().setAorB(false);
                Platform.runLater(platformthread);


                //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoderz
            }
            SerialPortClass.getSerialPortOBJ().closePort();
        }
    });

    Thread platformthread = new Thread(() -> {
        if (SerialPortClass.getSerialPortOBJ().getAorB()) {
            Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA());
            System.out.println("platform A");
        } else {
            Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB());
            System.out.println("platform B");
        }
    });

        Thread sqlThread = new Thread(() -> {
            if (SerialPortClass.getSerialPortOBJ().getAorB()) {
                System.out.println("SQl A");
                SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueA);
            } else {
                System.out.println("SQl B");
                SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueB);
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
}
