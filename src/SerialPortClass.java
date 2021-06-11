import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {
    public static String COMPORT = "COM4";
    private int ValueA[] = new int[3950];
    private int ValueB[] = new int[3950];//svarer til 5 sekunder
    private Boolean AorB = true;


    private int d = 0;
    private int h = 0;
    private String buffer = "";

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

    public void filter3950measurements(int[] intArray) {
        String[] stringArray;
        while (d < 3950) {
            String s = SerialPortClass.getSerialPortOBJ().maaling();
            if (s != null) {
                setBuffer(getBuffer()+s);
                int i = getBuffer().indexOf("A");
                if (i > -1) {
                    stringArray = getBuffer().split("A");
                    if (stringArray != null && stringArray.length > 0) {
                        if (getBuffer().charAt(getBuffer().length() - 1) != 65) {
                            setBuffer(stringArray[stringArray.length - 1]);
                            stringArray[stringArray.length - 1] = null;
                        } else {
                            setBuffer("");
                        }

                        while (getH() < stringArray.length - 1 && stringArray.length > 1) {
                            if (stringArray[getH()] != null) {
                                if ((getD() + getH()) >= 3950) {
                                    break;
                                }
                                intArray[getD() + getH()] = Integer.parseInt(stringArray[h]);
                                if (getD() + getH() == 1500) {
                                    System.out.println("1500");
                                }
                                if (getD() + getH() == 3000) {
                                    System.out.println("3000");
                                }
                            }
                            h++;
                        }
                        setD(getD() + getH());
                        setH(0);
                    }
                }
            }
        }
        setD(0);

    }

    public void filterMeasurements() {
        String rawdata = SerialPortClass.getSerialPortOBJ().maaling();
        if (rawdata != null) {
            buffer = buffer + rawdata;
            System.out.println(buffer);
        }
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

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }
}


