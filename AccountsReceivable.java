
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountsReceivable {
	private static int taxID;
public static void main(String[] args) {
  
	
	//Generates random number, and assigns it to the taxID.
    // Problem, void is invalid type, only FINAL permitted <<<

	int taxID1 =  RandomTaxID();
	System.out.println(taxID1);
    Scanner in = new Scanner(System.in); 
    
}
/**
 * If new, get a random Tax ID.
 * @param tax_ID
 * @return tax id
 */
public static int RandomTaxID() {
	
	int minNum = 1; //Random number generator smallest possible number = 1
	int maxNum = 5000; //Random number generator largest possible 50000 = 50000;
	taxID = minNum + (int)(Math.random() * ((maxNum - minNum)+ 1));
	
	return taxID;
	
}

public void createCustomerDB() throws SQLException {  // Void is invalid type, & Only FINAL permitted 
		
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				s.execute("CREATE TABLE CustomerDB (TaxID INT, CustomerName VARCHAR(40), CustomerAddressStreet VARCHAR(40), "
						+ "CustomerAddressCity VARCHAR(40), CustomerAddressState VARCHAR(2))");
				
				int taxID[] = {0001,0002,0003};
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
public void droptable() throws SQLException {  //AnnotationName expected after "droptable"
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				try {
					s.execute("DROP THE TABLE SQL THING");
				} catch(SQLException e) {
					System.out.println("EMPTY TABLE");
				}
			}
		}
	}
}
