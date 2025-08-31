import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbintiation {
  public Connection getConnection(){
    String url = "jdbc:mysql://localhost:3306/LIBRARY";
        String user = "root"; 
        String password = "Aditya@123";
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful!");
            
        } 
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } 
        catch (SQLException e) {
            System.out.println("Connection failed! Check console output.");
            e.printStackTrace();
        } 
        
        return conn;
  }
}
