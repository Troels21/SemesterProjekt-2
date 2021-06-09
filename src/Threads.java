import javafx.application.Platform;
import javafx.scene.chart.LineChart;

public class Threads {
    LineChart t;

    public Threads(LineChart linechart) {
        t = linechart;
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
                makeNewSqlThreadStart();
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueB);
                System.out.println("Filter B");
                SerialPortClass.getSerialPortOBJ().setAorB(false);
                Platform.runLater(platformthread);
                makeNewSqlThreadStart();

                //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

            }
            SerialPortClass.getSerialPortOBJ().closePort();

        }
    });

    Thread platformthread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (SerialPortClass.getSerialPortOBJ().getAorB()) {
                Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA());
                System.out.println("platform A");
            } else {
                Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB());
                System.out.println("platform B");
            }
        }
    });

    public void makeNewSqlThreadStart() {
        Thread sqlThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (SerialPortClass.getSerialPortOBJ().getAorB()) {
                    System.out.println("SQl A");
                    SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueA);
                } else {
                    System.out.println("SQl B");
                    SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueB);
                }
            }
        });

        sqlThread.start();
    }


    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            {
                while (ThreadHandler.getShouldMyThreadBeRuning()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("wait virkede");
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
    });


    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            Algorithm.getAlgorithmOBJ().setupChart(t);
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                try {
                    wait();
                    Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA());
                    wait();
                    Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB());
                } catch (InterruptedException g) {
                    g.printStackTrace();
                }
            }
        }
    });

    Thread t3 = new Thread(new Runnable() {
        @Override
        public void run() {
            SerialPortClass.getSerialPortOBJ().openPort();
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueA);
                t1.notify();
                t2.notify();
                System.out.println("notify");
                SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().ValueB);
                t1.notify();
                t2.notify();
                //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

            }
            SerialPortClass.getSerialPortOBJ().closePort();
        }
    });
}
