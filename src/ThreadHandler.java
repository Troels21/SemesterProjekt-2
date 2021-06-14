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

    private Thread RealTimet, SQLUpdatet, SerialPortt;
    static private Boolean ShouldMyThreadBeRuning = false;
    private LinkedList<Integer> que = new LinkedList<Integer>();
    private Thread produce, consume;

    public void makeNewThreadIfClosed(Thread thread) {
        if(!thread.isAlive()){
            Thread t = new Thread(thread);
            t.start();
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
