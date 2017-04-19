import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountsReceivable {

public static void main(String[] args) throws SQLException {	
	
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

public static void createCustomerDB() throws SQLException {  
		
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				try {
					s.execute("DROP TABLE CustomerDB") ;
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				s.execute("CREATE TABLE CustomerDB (TaxID INTEGER, CustomerName VARCHAR(40), CustomerAddressStreet VARCHAR(40), CustomerAddressCity VARCHAR(40), CustomerAddressState VARCHAR(2))");
				
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
public static void createInvoiceDB() throws SQLException {
	try(Connection con = ConnectionDB.getConnection()) {
		try(Statement s = con.createStatement()) {
			try {
				s.execute("DROP TABLE InvoiceDB");		
			} catch (SQLException e ) {
				System.out.println(e.getMessage());
			}
			
			s.execute("CREATE TABLE InvoiceDB (InvoiceNum INTEGER, InvoiceAmount DOUBLE, InvoiceDate VAR(10), Invoice_DueDate VAR(10)");
			
			int invoiceNum[] = {2001, 2002, 2003};
			int taxID[] = {1001, 1002,1003};
			double invoiceAmount[] = {23.56, 112.50, 500.60};
			String invoiceDates[] = {"20/02/08", "10/01/09", "05/12/10"};
			String invoice_DueDates[] = {"30/12/08", "30/11/10", "05/12/11"};
			
			for(int i = 0; i < invoiceNum.length; i++ ){
				InvoiceDB x = new InvoiceDB(invoiceNum[i]);
				x.addInvoice(taxID[i],invoiceAmount[i], invoiceDates[i], invoice_DueDates[i]);
			}
			
		}
	}
}


}
