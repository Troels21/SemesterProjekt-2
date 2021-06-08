import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {

    //bruges som datagenerator - henter værdier fra porte og overføres til DTO/Filter klassen
    public static String COMPORT="COM3";

    private SerialPort sensor = new SerialPort(COMPORT);

    private static SerialPortClass SerialPortOBJ = new SerialPortClass();

    private SerialPortClass(){
    }

    public static SerialPortClass getSerialPortOBJ(){
        return SerialPortOBJ;
    }

    public void openPort() {
        try {
            sensor.openPort();
            sensor.setParams(57600, 8, 1, 0);
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
