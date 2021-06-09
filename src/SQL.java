import java.sql.*;

public class SQL {

    static String url = "jdbc:mysql://localhost:3306/andet navn her";
    static String user = "root";
    static String password = "";
    static Connection myConn;
    static Statement myStatement;

    public void makeConnectionSQL(){
        try {
            myConn = DriverManager.getConnection(url, user, password);
            myStatement = myConn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeConnectionSQL(){
        try {
            if (!myConn.isClosed()){
                myConn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void writeTodatabaseArray(int[] anArray) {
        anArray = myConn.createArrayOf("VARCHAR", northEastRegion);
        PreparedStatement pstmt = con.prepareStatement(
                "insert into REGIONS (region_name, zips) " + "VALUES (?, ?)");
        pstmt.setValue(1, "CPR");
        pstmt.setArray(2, anArray);
        pstmt.executeUpdate();
    }

}
