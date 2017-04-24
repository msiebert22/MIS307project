import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
public class AccountsReceivable {
	private static InvoiceDB ii;
	public static void main(String[] args) throws SQLException {	
	
		createInvoiceDB();
		
		InvoiceDB iv = new InvoiceDB(5004);
		
		iv.addInvoice(1004, 10.50, "10/10/10", "11/10/10");
		iv.amountsReceived(10.50);
		
		for(int i = 0; i < iv.getInvoiceNumbers().size(); i++) {
			String checkifPaid = iv.checkifPaid(iv.getInvoiceNumbers().get(i));
			ii = new InvoiceDB(iv.getInvoiceNumbers().get(i));		
			System.out.println(ii.toString());
			System.out.println("Y if paid and N for not yet paid: " + iv.getInvoiceNumbers().get(i) + " " + checkifPaid);
		}
		System.out.println();
		
		
		for(int i = 0; i < iv.getInvoiceNumbers().size(); i++) {
			
		}
	
		
	

		
		
//		Connection con = ConnectionDB.getConnection();
//		Statement s = con.createStatement();
//		ResultSet r = s.executeQuery("SELECT InvoiceNum, TaxID, InvoiceAmount, InvoiceDate, Invoice_DueDate FROM InvoiceDB");
//		ResultSetMetaData rs = r.getMetaData();
//		
//		int col = rs.getColumnCount();
//		
//				while(r.next()) {
//					for(int i = 1; i <=col; i++) {
//						System.out.println(r.getString(i) + " ");
//					}
//						System.out.println();
//				}
	
	}
	/**
	 * Creates the customer database.
	 * @throws SQLException
	 */
	public static void createCustomerDB() throws SQLException {  
			
			try(Connection con = ConnectionDB.getConnection()) {
				try(Statement s = con.createStatement()) {
					try {
						s.execute("DROP TABLE CustomerDB") ;
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					s.execute("CREATE TABLE CustomerDB (TaxID INTEGER PRIMARY KEY, CustomerName VARCHAR(40), CustomerAddressStreet VARCHAR(40), CustomerAddressCity VARCHAR(40), CustomerAddressState VARCHAR(2))");
					
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
			//System.out.println("CREATED");
		}

	/**
	 * Creates the invoice database. 
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
				s.execute("CREATE TABLE InvoiceDB (InvoiceNum INTEGER, TaxID INTEGER, InvoiceAmount DECIMAL(10,2), InvoiceDate VARCHAR(10), Invoice_DueDate VARCHAR(10))");
				
				int invoiceNum[] = {5001, 5002, 5003};
				int taxID[] = {1001, 1002,1003};
				double invoiceAmount[] = {23.56, 112.50, 500.60};
				String invoiceDates[] = {"09/02/10", "10/01/10", "05/12/10"};
				String invoice_DueDates[] = {"10/12/10", "11/11/10", "05/25/10"};
				
				for(int i = 0; i < invoiceNum.length; i++ ){
					InvoiceDB x = new InvoiceDB(invoiceNum[i]);
					x.addInvoice(taxID[i],invoiceAmount[i], invoiceDates[i], invoice_DueDates[i]);
				}
				
			}
		}
		//System.out.println("CREATED DATABASE FOR INVOICE");
	}

}
