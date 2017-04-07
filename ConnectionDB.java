import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConnectionDB {

	  private static String url = "jdbc:derby:BigJavaDB;create=true";
	  private static String username = " ";
	  private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
	  private static String password = " ";
	  private static Connection connection;
		  
	public static Connection getConnection() throws SQLException 
	{
		try {
			Class.forName(driverName);
			try {
				connection = DriverManager.getConnection(url, username, password);
			}
			catch (SQLException x) {
				System.out.println("Failure to connect to database: call 911");
			}
		} catch (ClassNotFoundException x) {
			System.out.println("Driver not found: Call the president");
		}
		return connection;
	}
		    
		 
}
