import javafx.application.Platform;
import javafx.scene.chart.LineChart;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Threads {
    ReentrantLock lockA = new ReentrantLock();
    ReentrantLock lockB = new ReentrantLock();
    LineChart t;

    public Threads(LineChart linechart) {
        t = linechart;
    }

    Thread MotherloardThread = new Thread(new Runnable() {
        @Override
        public void run() {
            SerialPortClass.getSerialPortOBJ().openPort();
            Platform.runLater(()->Algorithm.getAlgorithmOBJ().setupChart(t));
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueA);
                System.out.println("Filter A");
                SerialPortClass.getSerialPortOBJ().setAorB(true);
                Platform.runLater(platformthread);
                makeNewSqlThreadStart();
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueB);
                System.out.println("Filter B");
                SerialPortClass.getSerialPortOBJ().setAorB(false);
                Platform.runLater(platformthread);
                makeNewSqlThreadStart();

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

    public void makeNewSqlThreadStart() {
        Thread sqlThread = new Thread(() -> {
            if (SerialPortClass.getSerialPortOBJ().getAorB()) {
                System.out.println("SQl A");
                SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueA);
            } else {
                System.out.println("SQl B");
                SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueB);
            }
        });

        sqlThread.start();
    }


    Thread t1 = new Thread(() -> {
        {
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                if (lockB.isLocked()) {
                    System.out.println("lockA virkede");
                    //SQL Inject FilterOBJ.ValueA
                }
                if (lockA.isLocked()) {
                    System.out.println("lockB virkede");
                    //SQL Inject FilterOBJ.ValueA
                }
            }
        }
    });


    Thread t2 = new Thread(() -> {
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            if (lockB.isLocked()) {
                System.out.println("lockA virkedet1");
                Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA());
            }
            if (lockA.isLocked()) {
                System.out.println("lockB virkedet2");
                Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB());
            }
        }
    });

    Thread t3 = new Thread(() -> {
        SerialPortClass.getSerialPortOBJ().openPort();
        lockA.lock();
        lockB.lock();
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueA);
            lockA.unlock();
            lockB.lock();
            SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueB);
            lockB.unlock();
            lockA.lock();
            //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

        }
        SerialPortClass.getSerialPortOBJ().closePort();
    });
}
