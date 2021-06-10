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

    private SQL(){
    }

    static private SQL sqlOBJ= new SQL();

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

    public void writeTodatabaseArray(int[] array) {
        try{
            makeConnectionSQL();
            for (int i = 0; i < array.length - 26; i+=25) {
                String write_to_database2 = "insert into EKG(EKGValue) values" +
                        "(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?)"+
                        ",(?);"
                        ;
                PreparedStatement PP2 = myConn.prepareStatement(write_to_database2);
                PP2.setInt(1,array[i]);
                PP2.setInt(2,array[i+1]);
                PP2.setInt(3,array[i+2]);
                PP2.setInt(4,array[i+3]);
                PP2.setInt(5,array[i+4]);
                PP2.setInt(6,array[i+5]);
                PP2.setInt(7,array[i+6]);
                PP2.setInt(8,array[i+7]);
                PP2.setInt(9,array[i+8]);
                PP2.setInt(10,array[i+9]);
                PP2.setInt(11,array[i+10]);
                PP2.setInt(12,array[i+11]);
                PP2.setInt(13,array[i+12]);
                PP2.setInt(14,array[i+13]);
                PP2.setInt(15,array[i+14]);
                PP2.setInt(16,array[i+15]);
                PP2.setInt(17,array[i+16]);
                PP2.setInt(18,array[i+17]);
                PP2.setInt(19,array[i+18]);
                PP2.setInt(20,array[i+19]);
                PP2.setInt(21,array[i+20]);
                PP2.setInt(22,array[i+21]);
                PP2.setInt(23,array[i+22]);
                PP2.setInt(24,array[i+23]);
                PP2.setInt(25,array[i+24]);

                PP2.execute();
            }
            System.out.println("Done SQL");
            removeConnectionSQL();
    } catch (SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }
}
