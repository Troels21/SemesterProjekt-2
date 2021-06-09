public class SQLupdateThread extends Thread implements SensorObserver {

    @Override
    public void run() {
        synchronized (this) {
            while (ThreadHandler.getShouldMyThreadBeRuning()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void notify(SerialPortClass serialportclass) {

    }
}
