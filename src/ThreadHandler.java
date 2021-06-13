import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

import java.util.LinkedList;

public class ThreadHandler {
    private static ThreadHandler ThreadHandlerOBJ = new ThreadHandler();

    private ThreadHandler() {
    }

    public static ThreadHandler getThreadHandlerOBJ() {
        return ThreadHandlerOBJ;
    }




    LineChart lineChart;
    Label label;

    private Thread RealTimet, SQLUpdatet, SerialPortt;
    static private Boolean ShouldMyThreadBeRuning;
    private LinkedList<Integer> que = new LinkedList<Integer>();
    private Thread produce, consume;




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


        produce = new Thread(new ProducerThread(que));
        consume = new Thread(new ConsumerThread(que));


    }

    public void threadStart() {
        //getSerialPortt().start();
        //consume.setPriority(Thread.MAX_PRIORITY);
        //produce.setPriority(Thread.NORM_PRIORITY);
        produce.start();
        consume.start();
    }
/*
    public void threadJoin(){
        try {
            getProduce().join();
            getConsume().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/

    public void startthreadifclose(Thread thread) {
        if (thread.isAlive()) {
        }
        else {
            thread.start();
        }
    }

    public static Boolean getShouldMyThreadBeRuning() {
        return ShouldMyThreadBeRuning;
    }

    public static void setShouldMyThreadBeRuning(Boolean shouldMyThreadBeRuning) {
        ShouldMyThreadBeRuning = shouldMyThreadBeRuning;
    }


    public Thread getRealTimet() {
        return RealTimet;
    }

    public void setRealTimet(Thread realTimet) {
        RealTimet = realTimet;
    }

    public Thread getSQLUpdatet() {
        return SQLUpdatet;
    }

    public void setSQLUpdatet(Thread SQLUpdatet) {
        this.SQLUpdatet = SQLUpdatet;
    }

    public Thread getSerialPortt() {
        return SerialPortt;
    }

    public void setSerialPortt(Thread serialPortt) {
        SerialPortt = serialPortt;
    }

    public LinkedList<Integer> getQue() {
        return que;
    }

    public void setQue(LinkedList<Integer> que) {
        this.que = que;
    }

    public Thread getProduce() {
        return produce;
    }

    public void setProduce(Thread produce) {
        this.produce = produce;
    }

    public Thread getConsume() {
        return consume;
    }

    public void setConsume(Thread consume) {
        this.consume = consume;
    }

}
