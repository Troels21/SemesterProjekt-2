import java.util.Arrays;

public class SerialPortThread extends Thread implements SensorObserver {

    @Override
    public void run(){
        SerialPortClass.getSerialPortOBJ().openPort();
        while (ThreadHandler.getShouldMyThreadBeRuning()) {
            SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().getValueA());
            //Notify
            SerialPortClass.getSerialPortOBJ().filter3950measurements(SerialPortClass.getSerialPortOBJ().getValueA());
            //notify
            //generelt - undgå at accesse -DIREKTE fra klassernes attributter og felter . brug get og set metoder

        }
        SerialPortClass.getSerialPortOBJ().closePort();
    }

    @Override
    public void notify(SerialPortClass serialportclass) {
      if(serialportclass.getValueA()!=null){
          System.out.println("value != null");

          //gør noget.
      }
    }
}
