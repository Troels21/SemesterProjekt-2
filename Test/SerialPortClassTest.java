import com.mysql.cj.exceptions.AssertionFailedException;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SerialPortClassTest {
    Exception exception;
    String string;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();



    @BeforeEach
    void setUptest() {
        System.setOut(new PrintStream(out));
    }

    @Test
    void openPorttest() {
        SerialPortClass.getSerialPortOBJ().setCOMPORTname("COM4");
        SerialPortClass.getSerialPortOBJ().openPort();
        assertNotEquals("FEJL SERIALPORTEXCEPTION\n" , out.toString());

        SerialPortClass.getSerialPortOBJ().closePort();
    }

    @Test
    void closePorttest() {
        SerialPortClass.getSerialPortOBJ().openPort();
        SerialPortClass.getSerialPortOBJ().setCOMPORTname("COM4");
        SerialPortClass.getSerialPortOBJ().closePort();
        assertNotEquals("FEJL SERIALPORTEXCEPTION\n" , out.toString());
    }

    @Test
    void maalingtest() {
        SerialPortClass.getSerialPortOBJ().setCOMPORTname("COM4");
        SerialPortClass.getSerialPortOBJ().openPort();

        String maling =null;
        for (int i = 0;i<50;i++){
            maling = maling+SerialPortClass.getSerialPortOBJ().maaling();
        }
        assertNotEquals(null,maling);

        SerialPortClass.getSerialPortOBJ().closePort();
    }

    @Test
    void filter4000measurementstest() {
        SerialPortClass.getSerialPortOBJ().setCOMPORTname("COM4");
        SerialPortClass.getSerialPortOBJ().openPort();
        int[] array = new int[4000];
        int counter =0;

        SerialPortClass.getSerialPortOBJ().filter4000measurements(array);

        for(int i=0;i<4000;i++){
            counter+=array[i];
        }
        assertNotEquals(0,counter);
    }
}