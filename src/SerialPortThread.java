import java.util.Arrays;

public class SerialPortThread extends Thread implements SensorObserver {

    @Override
    public void run(){
        SerialPortClass.getSerialPortOBJ().openPort();
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueA);
            //Notify
            Filter.getFilterOBJ().filter3950measurements(Filter.getFilterOBJ().ValueB);
            //notify
            //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

        }
        SerialPortClass.getSerialPortOBJ().closePort();
    }

    @Override
    public void notify(Filter filter) {
      if(filter.getValueA()!=null){
          System.out.println("value != null");

          //gør noget.
      }
    }
}
