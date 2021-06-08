public class SerialPortThread extends Thread {

    @Override
    public void run(){
        SerialPortClass.getSerialPortOBJ().openPort();
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            Filter.getFilterOBJ().filter3950measurements(Filter.ValueA);
            //Notify
            Filter.getFilterOBJ().filter3950measurements(Filter.ValueB);
            //notify
        }
        SerialPortClass.getSerialPortOBJ().closePort();
    }
}
