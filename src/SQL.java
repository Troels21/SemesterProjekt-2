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
            for (int i = 0; i < array.length - 1; i++) {
                String write_to_database2 = "insert into EKG(EKGValue) values(?)";
                PreparedStatement PP2 = myConn.prepareStatement(write_to_database2);
                PP2.setInt(1,array[i]);
                PP2.execute();
            }
            removeConnectionSQL();
    } catch (SQLException throwables) {
            throwables.printStackTrace();
            removeConnectionSQL();
        }
    }
}
