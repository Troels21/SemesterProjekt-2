import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    //Label og Linechart til bruge label og linechart fra Controlleren
    private Label label;
    private LineChart lineChart;

    //Executorservice, for at undgå at oprette flere tråde end nødvendigt.
    private final ExecutorService SqlHandler = Executors.newSingleThreadExecutor();

    //Den store tråd der kører alle målinger i programmet
    private final Thread MotherloardThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Platform.runLater(() -> Algorithm.getAlgorithmOBJ().setupChart(lineChart)); //Opsætter LineChart
            SerialPortClass.getSerialPortOBJ().openPort(); //Åbner SeiralPort
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                SerialPortClass.getSerialPortOBJ().filter4000measurements(SerialPortClass.getSerialPortOBJ().getValueA());  //Filtrer Målinger
                System.out.println("Filter A");
                SerialPortClass.getSerialPortOBJ().setAorB(true); //flipper Boolean
                Platform.runLater(platformthread); //Får JavaFX til at køre denne tråd når den kan
                getSqlHandler().execute(sqlThread); //Får Executorservice til at køre denne runnable som en task


                //Her gentager overordnede igen, bare med en flippet boolean, og et nyt array
                SerialPortClass.getSerialPortOBJ().filter4000measurements(SerialPortClass.getSerialPortOBJ().getValueB());
                System.out.println("Filter B");
                SerialPortClass.getSerialPortOBJ().setAorB(false);
                Platform.runLater(platformthread);
                getSqlHandler().execute(sqlThread);
            }
            SerialPortClass.getSerialPortOBJ().closePort(); //slukkerPort
        }
    });

    private final Thread platformthread = new Thread(() -> {
        if (SerialPortClass.getSerialPortOBJ().getAorB()) {
            Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA()); //Udfylder LineChart
            Algorithm.getAlgorithmOBJ().BPMalgo(SerialPortClass.getSerialPortOBJ().getValueA(), label); //Skifter BPM
            System.out.println("platform A");
        } else {
            Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB()); //Udfylder LineChart
            Algorithm.getAlgorithmOBJ().BPMalgo(SerialPortClass.getSerialPortOBJ().getValueB(), label); //Skifter BPM
            System.out.println("platform B");
        }
    });

    private final Thread sqlThread = new Thread(() -> {
        SQL.getSqlOBJ().findMeasurementID(Algorithm.getAlgorithmOBJ().getCPR()); //Henter CPR
        if (SerialPortClass.getSerialPortOBJ().getAorB()) {
            System.out.println("SQl A");
            SQL.getSqlOBJ().writeToMeasurementArray(SerialPortClass.getSerialPortOBJ().getValueA()); //Printer til Database
        } else {
            System.out.println("SQl B");
            SQL.getSqlOBJ().writeToMeasurementArray(SerialPortClass.getSerialPortOBJ().getValueB()); //Printer til Database
        }
    });

    //Getters and Setters
    public ExecutorService getSqlHandler() {
        return SqlHandler;
    }

    public Thread getMotherloardThread() {
        return MotherloardThread;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setLineChart(LineChart lineChart) {
        this.lineChart = lineChart;
    }
}
