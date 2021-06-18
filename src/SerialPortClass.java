import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortClass {
    private String COMPORTname = "COM4";  //COMPORT
    private int[] ValueA = new int[4000];
    private int[] ValueB = new int[4000];//svarer til 5 sekunder
    private Boolean AorB = true;  //Boolean der kontrollere hvilket scenarie vi skal printe til linechart og SQL

    //Counters og buffere der bruges til filtering
    private int d = 0;
    private int h = 0;
    private String buffer = "";

    public SerialPort getSensor() {
        return sensor;
    }

    //Singleton SensorObjekt
    private final SerialPort sensor = new SerialPort(getCOMPORTname());
    private static SerialPortClass SerialPortOBJ = new SerialPortClass();
    private SerialPortClass() {}
    public static SerialPortClass getSerialPortOBJ() {
        return SerialPortOBJ;
    }

    //Metode der åbner port med en baudrate på 57600, og prøver at purge resterende data fra andre overførselser.
    public void openPort() {
        try {
            sensor.openPort();
            sensor.setParams(57600, 8, 1, 0);
            sensor.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            sensor.purgePort(SerialPort.PURGE_TXCLEAR | SerialPort.PURGE_RXCLEAR);
        } catch (SerialPortException ex) {
            System.out.print("FEJL SERIALPORTEXCEPTION\n");
        }
    }

    public void closePort() {
        try {
            sensor.closePort();
        } catch (SerialPortException e) {
            System.out.print("FEJL SERIALPORTEXCEPTION\n");
        }
    }

    //Metode der tager en måling og returnere dataen, der er data og retunere null hvis ikke
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

    //Metode der filtrer 4000 målinger aka. 5 sekunders målinger, og allokere dem til et array
    public void filter4000measurements(int[] intArray) {
        String[] stringArray; //Buffer array
        while (d < 4000) {
            String s = SerialPortClass.getSerialPortOBJ().maaling();
            if (s != null) {  //Ser bort fra null værdierne
                setBuffer(getBuffer() + s);
                int i = getBuffer().indexOf("A"); //får hvilken plads A er på
                if (i > -1) {//Og kontrollere om A eksisterede i målingen
                    stringArray = getBuffer().split("A"); //Hvis A eksisterede splitter vi ved A
                    if (stringArray != null && stringArray.length > 0) { //Kontrol om at der blev splittet
                        if (getBuffer().charAt(getBuffer().length() - 1) != 65) { //Kontrol om at målingen sluttede på A
                            setBuffer(stringArray[stringArray.length - 1]); //Når måling ikke sluttede på A, har vi en
                            // halv værdi på sidste plads, denne gemmes nu i buffer.
                            stringArray[stringArray.length - 1] = null; //Sletter sidste måling i array, da den er en halv måling
                        } else {
                            setBuffer("");//Hvis den sidste måling sluttede på A, vil den blive efterfulgt af en ny måling, derfor tømmes buffer
                        }

                        //Løkke der parser vores buffer array til vores måling array
                        while (getH() < stringArray.length - 1 && stringArray.length > 1) {
                            if (stringArray[getH()] != null) {
                                if ((getD() + getH()) >= 4000) {
                                    break;
                                }
                                try { //Hvis Serialporten har været brugt til noget andet, skal vi fjerne værdier der ikke kan bruges
                                    int bufferint = Integer.parseInt(stringArray[h]); //Værdier der kan bruges kan laves til en int
                                    intArray[getD() + getH()] = bufferint;
                                } catch (NumberFormatException e) {
                                    System.out.println("Numberformat exception");
                                    intArray[getD() + getH()] = 0;  //Hvis vi ikke kan bruge værdien, indsættes der 0 istedet
                                }

                                if (getD() + getH() == 1999) {
                                    System.out.println("Halfway"); //vi er halvejs i filteringen
                                }
                            }
                            setH(getH() + 1); //Tæller H op
                        }
                        setD(getD() + getH()); //Tæller D op
                        setH(0); //Nulstiller H
                    }
                }
            }
            try {  //Hvis der er blevet registret et null, venter vi indtil vores Serialport cache er fyldt lidt mere
                Thread.sleep(80); //Her sker ventet
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setD(0); //D nulstilles
    }

    // Getters and Setters til alle attributter, pga. indkapsling
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

    public String getCOMPORTname() {
        return COMPORTname;
    }

    public void setCOMPORTname(String COMPORTname) {
        this.COMPORTname = COMPORTname;
    }
}


