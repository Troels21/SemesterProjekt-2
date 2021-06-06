import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {
    SerialPort sensor = new SerialPort("COM");

    /*private static final SerialPort sensor = new SerialPort();

    private SerialPortClass(){
    }

    public static SerialPort getSensor(){
        return sensor;
    }*/

    public void openPort() {
        try {
            sensor.openPort();
            sensor.setParams(115200, 8, 1, 0);
            sensor.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            sensor.purgePort(SerialPort.PURGE_TXCLEAR | SerialPort.PURGE_RXCLEAR);
        } catch (SerialPortException ex) {
            System.out.println("FEJL SERIALPORTEXCEPTION");
        }
    }

    public void closePort() {
        try {
            sensor.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public String maaling() {

        try {
            if (sensor.getInputBufferBytesCount() > 0) {
                return sensor.readString();
            } else {
                return null;
            }
        } catch (SerialPortException ex) {
            System.out.println("fejl: " + ex);
        }
        return null;
    }


}
