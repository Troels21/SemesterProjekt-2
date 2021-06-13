import java.sql.*;

public class SQL {

    //SQL Database setup
    //Use semesterprojekt2;
    //
    //DROP TABLE EKG;
    //CREATE TABLE EKG(
    //Measurement INT NOT NULL auto_increment,
    //EKGValue DOUBLE NOT NULL,
    //PRIMARY KEY(Measurement)
    //);

    private SQL() {
    }

    static private SQL sqlOBJ = new SQL();

    static public SQL getSqlOBJ() {
        return sqlOBJ;
    }

    static String url = "jdbc:mysql://localhost:3306/semesterprojekt2";
    static String user = "root";
    static String password = "";
    static Connection myConn;
    static Statement myStatement;

    public void makeConnectionSQL() {
        try {
            myConn = DriverManager.getConnection(url, user, password);
            myStatement = myConn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        makeConnectionSQL();
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

    public void writeToMeasurementArray(int[] array, String string) {
        try {
            makeConnectionSQL();
            for (int i = 0; i < array.length - 26; i += 25) {
                String write_to_measurement = "insert into EKG(EKGValue, CPR) values" +
                        "(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?)" +
                        ",(?,?);";
                PreparedStatement PP = myConn.prepareStatement(write_to_measurement);
                PP.setInt(1, array[i]);
                PP.setInt(2, Integer.parseInt(string));
                PP.setInt(3, array[i + 1]);
                PP.setInt(4, Integer.parseInt(string));
                PP.setInt(5, array[i + 2]);
                PP.setInt(6, Integer.parseInt(string));
                PP.setInt(7, array[i + 3]);
                PP.setInt(8, Integer.parseInt(string));
                PP.setInt(9, array[i + 4]);
                PP.setInt(10, Integer.parseInt(string));
                PP.setInt(11, array[i + 5]);
                PP.setInt(12, Integer.parseInt(string));
                PP.setInt(13, array[i + 6]);
                PP.setInt(14, Integer.parseInt(string));
                PP.setInt(15, array[i + 7]);
                PP.setInt(16, Integer.parseInt(string));
                PP.setInt(17, array[i + 8]);
                PP.setInt(18, Integer.parseInt(string));
                PP.setInt(19, array[i + 9]);
                PP.setInt(20, Integer.parseInt(string));
                PP.setInt(21, array[i + 10]);
                PP.setInt(22, Integer.parseInt(string));
                PP.setInt(23, array[i + 11]);
                PP.setInt(24, Integer.parseInt(string));
                PP.setInt(25, array[i + 12]);
                PP.setInt(26, Integer.parseInt(string));
                PP.setInt(27, array[i + 13]);
                PP.setInt(28, Integer.parseInt(string));
                PP.setInt(29, array[i + 14]);
                PP.setInt(30, Integer.parseInt(string));
                PP.setInt(31, array[i + 15]);
                PP.setInt(32, Integer.parseInt(string));
                PP.setInt(33, array[i + 16]);
                PP.setInt(34, Integer.parseInt(string));
                PP.setInt(35, array[i + 17]);
                PP.setInt(36, Integer.parseInt(string));
                PP.setInt(37, array[i + 18]);
                PP.setInt(38, Integer.parseInt(string));
                PP.setInt(39, array[i + 19]);
                PP.setInt(40, Integer.parseInt(string));
                PP.setInt(41, array[i + 20]);
                PP.setInt(42, Integer.parseInt(string));
                PP.setInt(43, array[i + 21]);
                PP.setInt(44, Integer.parseInt(string));
                PP.setInt(45, array[i + 22]);
                PP.setInt(46, Integer.parseInt(string));
                PP.setInt(47, array[i + 23]);
                PP.setInt(48, Integer.parseInt(string));
                PP.setInt(49, array[i + 24]);
                PP.setInt(50, Integer.parseInt(string));

                PP.execute();
            }
            System.out.println("Done SQL");
            removeConnectionSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }
}
