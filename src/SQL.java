import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class SQL extends Main {
    private SQL() {
    }

    static private SQL sqlOBJ = new SQL();

    static public SQL getSqlOBJ() {
        return sqlOBJ;
    }

    private String url = "jdbc:mysql://localhost:3306/semesterprojekt2";
    private String user = "root";
    private String password = "";
    private Connection myConn;
    private Statement myStatement;
    private int measurementID;
    private ArrayList<String> DateOfmeasurementonsameCPER = new ArrayList<>();

    private ArrayList<Integer> dataArray = new ArrayList<>();

    public void makeConnectionSQL() throws SQLException {
        myConn = DriverManager.getConnection(getUrl(), getUser(), getPassword());
        myStatement = myConn.createStatement();
    }

    public void removeConnectionSQL() {
        try {
            if (!myConn.isClosed()) {
                myConn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void makePatientMeasurement(String string) {
        try {
            makeConnectionSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            String write_to_measurementNumber = "insert into MeasurementNumber(CPR) values(?);";
            PreparedStatement PP = myConn.prepareStatement(write_to_measurementNumber);
            PP.setInt(1, Integer.parseInt(string));
            PP.execute();
            System.out.println("SQL Made patient");

            removeConnectionSQL();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }

    public void findMeasurementID(String CPRstring) {
        try {
            makeConnectionSQL();
            String findmeasurementIDFromCPR = "SELECT * FROM MeasurementNumber" +
                    " WHERE CPR = " + CPRstring + ";";
            ResultSet rs;

            System.out.println(findmeasurementIDFromCPR);

            rs = myStatement.executeQuery(findmeasurementIDFromCPR);
            while (rs.next()) {
                setMeasurementID(rs.getInt(1));
            }
            removeConnectionSQL();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }

    public void FindMeasureIDWhereCPRRead(String CPRstring) throws IOException, SQLException {
        int counter = 0;
        int counter2 = 0;
        makeConnectionSQL();
        String findmeasurementIDFromCPR2 = "SELECT * FROM MeasurementNumber" +
                " WHERE CPR = " + CPRstring + ";";

        ResultSet rs1 = myStatement.executeQuery(findmeasurementIDFromCPR2);

        while (rs1.next()) {
            counter++;
        }
        ResultSet rs2 = myStatement.executeQuery(findmeasurementIDFromCPR2);
        if (counter > 1) {
            while (rs2.next()) {
                setNumberOfMeasurementsOnSameCPR(rs2.getString(3), counter2);
                counter2++;
            }
            openStage(MultipleMeasurementStage, "Multiple Measurements", "MultipleMeasurements", 220, 180);
        } else {
            rs2 = myStatement.executeQuery(findmeasurementIDFromCPR2);
            rs2.next();
            setMeasurementID(rs2.getInt(1));
            Algorithm.getAlgorithmOBJ().textBox("Patient Found");
        }
        removeConnectionSQL();
    }

    public void readToDataArray() {
        try {
            int counter = 0;
            makeConnectionSQL();
            String ReadDatatoarray = "SELECT * FROM EKG" +
                    " WHERE MeasurementID=" + measurementID + ";";
            ResultSet rs;

            rs = myStatement.executeQuery(ReadDatatoarray);
            while (rs.next()) {
                setDataArray(rs.getInt(2), counter);
                counter++;
            }
            removeConnectionSQL();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }

    }

    public void getIdWhereData(String date) {
        try {
            makeConnectionSQL();
            String findmeasurementIDFromDate = "SELECT * FROM MeasurementNumber" +
                    " WHERE mearsurementStartedAt='" + date + "';";
            ResultSet rs;
            System.out.println(findmeasurementIDFromDate);

            rs = myStatement.executeQuery(findmeasurementIDFromDate);
            while (rs.next()) {
                setMeasurementID(rs.getInt(1));
            }
            removeConnectionSQL();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }


    public void writeToMeasurementArray(int[] array) {
        try {
            makeConnectionSQL();
            for (int i = 0; i < array.length - 1; i += 25) {
                String write_to_measurement = "insert into EKG(EKGValue, MeasurementId) values" +
                        "(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ")" +
                        ",(?," + getMeasurementID() + ");";
                PreparedStatement PP = myConn.prepareStatement(write_to_measurement);
                PP.setInt(1, array[i]);
                PP.setInt(2, array[i + 1]);
                PP.setInt(3, array[i + 2]);
                PP.setInt(4, array[i + 3]);
                PP.setInt(5, array[i + 4]);
                PP.setInt(6, array[i + 5]);
                PP.setInt(7, array[i + 6]);
                PP.setInt(8, array[i + 7]);
                PP.setInt(9, array[i + 8]);
                PP.setInt(10, array[i + 9]);
                PP.setInt(11, array[i + 10]);
                PP.setInt(12, array[i + 11]);
                PP.setInt(13, array[i + 12]);
                PP.setInt(14, array[i + 13]);
                PP.setInt(15, array[i + 14]);
                PP.setInt(16, array[i + 15]);
                PP.setInt(17, array[i + 16]);
                PP.setInt(18, array[i + 17]);
                PP.setInt(19, array[i + 18]);
                PP.setInt(20, array[i + 19]);
                PP.setInt(21, array[i + 20]);
                PP.setInt(22, array[i + 21]);
                PP.setInt(23, array[i + 22]);
                PP.setInt(24, array[i + 23]);
                PP.setInt(25, array[i + 24]);

                PP.execute();
            }
            System.out.println("Done SQL");
            removeConnectionSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }

    public int getMeasurementID() {
        return measurementID;
    }

    public void setMeasurementID(int measurementID) {
        this.measurementID = measurementID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getNumberOfMeasurementsOnSameCPR() {
        return DateOfmeasurementonsameCPER;
    }

    public void setNumberOfMeasurementsOnSameCPR(String string, int plads) {
        this.DateOfmeasurementonsameCPER.add(plads, string);
    }

    public ArrayList<Integer> getDataArray() {
        return dataArray;
    }

    public void setDataArray(int data, int plads) {
        this.dataArray.add(plads, data);
    }
}
