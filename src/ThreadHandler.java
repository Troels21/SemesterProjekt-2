import javafx.application.Platform;
import javafx.scene.chart.LineChart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadHandler {
    Thread RealTimet, SQLUpdatet, SerialPortt;
    static private Boolean ShouldMyThreadBeRuning;
    LinkedList<Integer> que = new LinkedList<Integer>();
    Thread produce, consume;


    public void makeThread() {
        setShouldMyThreadBeRuning(true);

        /*RealTimeThread RealTimeThreadOBJ= new RealTimeThread(linechart);
        SQLupdateThread sqlThreadOBJ= new SQLupdateThread();
        RealTimet = new Thread(RealTimeThreadOBJ);
        SQLUpdatet = new Thread(sqlThreadOBJ);
        SerialPortThread SerialPortThreadOBJ = new SerialPortThread();
        SerialPortt = new Thread(SerialPortThreadOBJ);*/
/*
        Threads threads = new Threads();
        SerialPortt = threads.t3;
        RealTimet =threads.t2;
        SQLUpdatet = threads.t1;*/

        Threads threads = new Threads();
        SerialPortt = threads.MotherloardThread;

        /*produce = new Thread(new ProducerThread(que));
        consume = new Thread(new ConsumerThread(que));*/


    }

    public void threadStart() {
        SerialPortt.start();
       /* consume.setPriority(Thread.MAX_PRIORITY);
        produce.setPriority(Thread.NORM_PRIORITY);
        produce.start();
        consume.start();*/
    }

    public void threadJoin(){
        try {
            produce.join();
            consume.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

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
