import javafx.application.Platform;

import java.util.LinkedList;
import java.util.List;

public class ConsumerThread implements Runnable {

    private LinkedList<Integer> que;

    public ConsumerThread(LinkedList<Integer> que) {
        this.que = que;
    }

    @Override
    public void run() {
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            consume();
        }
    }

    private void consume() {
        synchronized (que) {

            while (que.isEmpty()) {
                try {
                    System.out.println("waiting");
                    que.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueA);
            Platform.runLater(() -> Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueA()));
            que.removeFirst();
            while (que.isEmpty()) {
                try {
                    System.out.println("waiting");
                    que.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Done Waiting");
            //SQL.getSqlOBJ().writeTodatabaseArray(SerialPortClass.getSerialPortOBJ().ValueA);
            Platform.runLater(() -> Algorithm.getAlgorithmOBJ().populateChart(SerialPortClass.getSerialPortOBJ().getValueB()));
            que.removeFirst();
        }
    }
}