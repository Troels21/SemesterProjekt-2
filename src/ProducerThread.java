import java.util.LinkedList;
import java.util.List;

public class ProducerThread implements Runnable {
    int counter = 0;

    private LinkedList<Integer> que = new LinkedList<>();

    public ProducerThread(LinkedList<Integer> que) {
        this.que = que;
    }

    @Override
    public void run() {
        SerialPortClass.getSerialPortOBJ().openPort();
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            produce();
        }
        SerialPortClass.getSerialPortOBJ().closePort();
    }

    public void produce() {
        synchronized (que) {

            SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().getValueA());
            que.add(counter++);
            que.notifyAll();
            System.out.println("Notify");
            SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().getValueB());
            que.add(counter++);
            que.notifyAll();
            System.out.println("Notify");
            //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder
        }
    }
}