import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountsReceivable {
		
public static void main(String[] args) {
	//Some test stuff. checking if db is working 
	//to create the table
	//createcustomerDB();
	
	//to delete/drop the table
	//droptable();
	
	Connection con = ConnectionDB.getConnection();
	Statement s = con.createStatement();
	ResultSet r = s.executeQuery("SELECT TaxID, CustomerName, CustomerAddressState FROM CustomerDB");
	ResultSetMetaData rs = r.getMetaData();

	int col = rs.getColumnCount();

	while(r.next()) {
		for(int i = 1; i <=col; i++) {
			System.out.println(r.getString(i) + " ");
		}
			System.out.println();
	}
}


public static void createCustomerDB() throws SQLException {  // Void is invalid type, & Only FINAL permitted 
		
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				s.execute("CREATE TABLE CustomerDB (TaxID INTEGER, CustomerName VARCHAR(40), CustomerAddressStreet VARCHAR(40), "
						+ "CustomerAddressCity VARCHAR(40), CustomerAddressState VARCHAR(2))");
				
				int taxID[] = {1001,1002,1003};
				String CustomerNames[] = {"Gabe Newell", "Dwyane Wade", "Bill Clinton"};
				String CustomerStreet[] = {"1121 North St", "0002 Bulls St.", "1111 Waht St."};
				String CustomerCity[] = {"Seattle", "Chicago", "New York City"};
				String CustomerState[] = {"WA", "IL", "NY"};
				
				for(int i = 0;  i < taxID.length; i++)	{
					CustomerAccounts CA = new CustomerAccounts(taxID[i]);
					CA.addCustomerAccounts(CustomerNames[i], CustomerStreet[i], CustomerCity[i], CustomerState[i]);
				}
			}
		}
	}
	
	/**
	 * Drops the database and if it's empty, throws an exception
	 * @throws SQLException
	 */
public static void droptable() throws SQLException {  //AnnotationName expected after "droptable"
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				try {
					s.execute("DROP TABLE CustomerDB");
				} catch(SQLException e) {
					System.out.println("EMPTY TABLE");
				}
			}
		}
	}
}
