public class SerialPortThread extends Thread {
    Filter filterOBJ = new Filter();
    Thread thread;

    SerialPortThread(Thread tt){
        thread = tt;
        }

    @Override
    public void run() {
        SerialPortClass.getSerialPortOBJ().openPort();
        while (ThreadHandler.ShouldMyThreadBeRuning) {
            filterOBJ.filter3950measurements(Filter.ValueA);
            thread.notify();
            filterOBJ.filter3950measurements(Filter.ValueB);
            thread.notify();
        }
        SerialPortClass.getSerialPortOBJ().closePort();
    }
}
