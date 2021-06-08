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
                Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueA);
                System.out.println("Filter A");
                Filter.getFilterOBJ().setAorB(true);
                Platform.runLater(platformthread);
                makeNewSqlThreadStart();
                Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueB);
                System.out.println("Filter B");
                Filter.getFilterOBJ().setAorB(false);
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
            if (Filter.getFilterOBJ().getAorB()) {
                Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueA());
                System.out.println("platform A");
            } else {
                Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueB());
                System.out.println("platform B");
            }
        }
    });

    public void makeNewSqlThreadStart(){
        Thread sqlThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (Filter.getFilterOBJ().getAorB()) {
                    System.out.println("SQl A");
                    //SQL Inject FilterOBJ.ValueA
                } else {
                    System.out.println("SQl B");
                    //SQL Inject FilterOBJ.ValueB
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
                    Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueA());
                    wait();
                    Algorithm.getAlgorithmOBJ().populateChart(Filter.getFilterOBJ().getValueB());
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
                    Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueA);
                    t1.notify();
                    t2.notify();
                    System.out.println("notify");
                    Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueB);
                    t1.notify();
                    t2.notify();
                    //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

                }
                SerialPortClass.getSerialPortOBJ().closePort();
        }
    });
}
