import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {

    private SerialPort sensor = new SerialPort("COM4");

    private static SerialPortClass SerialPortOBJ = new SerialPortClass();

    private SerialPortClass(){
    }

    public static SerialPortClass getSerialPortOBJ(){
        return SerialPortOBJ;
    }

    public void openPort() {
        try {
            sensor.openPort();
            sensor.setParams(9600, 8, 1, 0);
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
