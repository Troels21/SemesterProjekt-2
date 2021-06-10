import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {
    public static String COMPORT = "COM5";
    int ValueA[] = new int[3950];
    int ValueB[] = new int[3950];//svarer til 5 sekunder
    Boolean AorB = true;

    int d = 0;
    int h = 0;
    String buffer = "";

    private SerialPort sensor = new SerialPort(COMPORT);

    private static SerialPortClass SerialPortOBJ = new SerialPortClass();

    private SerialPortClass() {
    }

    public static SerialPortClass getSerialPortOBJ() {
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

    public int[] getValueA() {
        return ValueA;
    }

    public int[] getValueB() {
        return ValueB;
    }

    public boolean getAorB() {
        return AorB;
    }

    public void setAorB(Boolean bollean) {
        AorB = bollean;
    }

    public void filter3950measurements(int[] intArray) {
        String[] stringArray;
        while (d < 3950) {
            String s = SerialPortClass.getSerialPortOBJ().maaling();
            if (s != null) {
                buffer = buffer + s;
                int i = buffer.indexOf("A");
                if (i > -1) {
                    stringArray = buffer.split("A");
                    if (stringArray != null && stringArray.length > 0) {
                        if (buffer.charAt(buffer.length() - 1) != 65) {
                            buffer = stringArray[stringArray.length - 1];
                            stringArray[stringArray.length - 1] = null;
                        } else {
                            buffer = "";
                        }

                        while (h < stringArray.length - 1 && stringArray.length > 1) {
                            if (stringArray[h] != null) {
                                if ((d + h) >= 3950) {
                                    break;
                                }
                                intArray[d + h] = Integer.parseInt(stringArray[h]);
                                if (d + h == 1500) {
                                    System.out.println("1500");
                                }
                                if (d + h == 3000) {
                                    System.out.println("3000");
                                }
                            }
                            h++;
                        }
                        d = d + h;
                        h = 0;
                    }
                }
            }
        }
        d = 0;

    }

    public void filterMeasurements() {
        String rawdata = SerialPortClass.getSerialPortOBJ().maaling();
        if (rawdata != null) {
            buffer = buffer + rawdata;
            System.out.println(buffer);
        }
    }
}


