import javafx.application.Platform;
import javafx.scene.chart.LineChart;

public class ThreadHandler {
    Thread RealTimet, SQLUpdatet, SerialPortt;
    static private Boolean ShouldMyThreadBeRuning;


    public void makeThread(LineChart linechart) {
        /*RealTimeThread RealTimeThreadOBJ= new RealTimeThread(linechart);
        SQLupdateThread sqlThreadOBJ= new SQLupdateThread();
        RealTimet = new Thread(RealTimeThreadOBJ);
        SQLUpdatet = new Thread(sqlThreadOBJ);
        SerialPortThread SerialPortThreadOBJ = new SerialPortThread();
        SerialPortt = new Thread(SerialPortThreadOBJ);*/

        Threads threads = new Threads(linechart);
        SerialPortt=threads.t3;
        RealTimet =threads.t2;
        SQLUpdatet=threads.t1;
    }

    public void threadStart() {
        setShouldMyThreadBeRuning(true);
        SerialPortt.start();
        RealTimet.start();
        SQLUpdatet.start();
    }

    /*public void threadJoin(){
        try {
            SerialPortt.join();
            RealTimet.join();
            SQLUpdatet.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/

    public void ifThreadaliveClose(Thread thread) {
        if (thread.isAlive()) {
            thread.interrupt();
        }

    }

    public static Boolean getShouldMyThreadBeRuning() {
        return ShouldMyThreadBeRuning;
    }

    public static void setShouldMyThreadBeRuning(Boolean shouldMyThreadBeRuning) {
        ShouldMyThreadBeRuning = shouldMyThreadBeRuning;
    }


}
