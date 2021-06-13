import javafx.application.Platform;
import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {
    public static String COMPORT = "COM4";
    private int ValueA[] = new int[4000];
    private int ValueB[] = new int[4000];//svarer til 5 sekunder
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

    public void filter4000measurements(int[] intArray) {
        String[] stringArray;
        while (d < 4000) {
            String s = SerialPortClass.getSerialPortOBJ().maaling();
            if (s != null) {
                setBuffer(getBuffer() + s);
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
                                if ((getD() + getH()) >= 4000) {
                                    break;
                                }
                                intArray[getD() + getH()] = Integer.parseInt(stringArray[h]);
                                if (getD() + getH() == 1999) {
                                    //Platform.runLater(()->Algorithm.getAlgorithmOBJ().populateChart2000(intArray));
                                    System.out.println("Halfway");
                                }
                            }
                            h++;
                        }
                        setD(getD() + getH());
                        setH(0);
                    }
                }
            }
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setD(0);

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

   /* public static void main(String[] args) {
        SerialPortOBJ.openPort();
        SerialPortOBJ.filter4000measurements(SerialPortOBJ.getValueA());
        int[] array = getSerialPortOBJ().getValueA();
        for (int i = 0; i < array.length - 1; i++) {
            System.out.println(array[i]);
        }
        getSerialPortOBJ().closePort();

    }*/
}


