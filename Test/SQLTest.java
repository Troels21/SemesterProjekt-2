import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SQLTest{
    final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    void setUptest() {
        System.setOut(new PrintStream(out));
    }

    @Test
    void makePatientMeasurementtest() {
        SQL.getSqlOBJ().makePatientMeasurement("1234567890");
        SQL.getSqlOBJ().findMeasurementID("1234567890");
        assertTrue(SQL.getSqlOBJ().getMeasurementID()>0);
    }

    @Test
    void readToDataArraytest() {
        SQL.getSqlOBJ().findMeasurementID("1111111111");
        SQL.getSqlOBJ().readToDataArray();
        int counter=0;

        for(int i=0;i<SQL.getSqlOBJ().getDataArray().size()-1;i++){
            counter+= SQL.getSqlOBJ().getDataArray().get(i);
        }
        assertNotEquals(0,counter);
    }

    @Test
    void getIdWhereDatetest() {
        SQL.getSqlOBJ().getIdWhereDate("2021-06-14 11:36:55");
        assertTrue(SQL.getSqlOBJ().getMeasurementID()>0);
    }

    @Test
    void writeToMeasurementArraytest() throws InterruptedException {
        int[] array = new int[200];

        for(int i=0;i<200;i++){
            array[i]=1;
        }
        SQL.getSqlOBJ().makePatientMeasurement("1111111111");
        SQL.getSqlOBJ().findMeasurementID("1111111111");
        SQL.getSqlOBJ().writeToMeasurementArray(array);
        Thread.sleep(2000);
        SQL.getSqlOBJ().readToDataArray();
        int counter=0;

        for(int i=0;i<SQL.getSqlOBJ().getDataArray().size()-1;i++){
            counter+= SQL.getSqlOBJ().getDataArray().get(i);
        }
        assertNotEquals(0,counter);


    }
}