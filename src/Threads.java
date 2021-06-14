import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private Label label;
    private LineChart lineChart;
    private ExecutorService SqlHandler = Executors.newSingleThreadExecutor();

    private Thread MotherloardThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Platform.runLater(() -> Algorithm.getAlgorithmOBJ().setupChart(lineChart));
            SerialPortClass.getSerialPortOBJ().openPort();
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter4000measurements(SerialPortClass.getSerialPortOBJ().getValueA());
                System.out.println("Filter A");
                SerialPortClass.getSerialPortOBJ().setAorB(true);
                Platform.runLater(platformthread);
                SqlHandler.execute(sqlThread);

                SerialPortClass.getSerialPortOBJ().filter4000measurements(SerialPortClass.getSerialPortOBJ().getValueB());
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
        SQL.getSqlOBJ().findMeasurementID(Algorithm.getAlgorithmOBJ().getCPR());
        if (SerialPortClass.getSerialPortOBJ().getAorB()) {
            System.out.println("SQl A");
            SQL.getSqlOBJ().writeToMeasurementArray(SerialPortClass.getSerialPortOBJ().getValueA());
        } else {
            System.out.println("SQl B");
            SQL.getSqlOBJ().writeToMeasurementArray(SerialPortClass.getSerialPortOBJ().getValueB());
        }
    });

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
