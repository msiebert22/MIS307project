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

	/**
	 * The main method of the program. Tests every method from InvoiceDB, CustomerAccounts classes.
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {	
		
		System.out.println("INVOICE");
		System.out.println("BEFORE EDIT");
		//initializes the invoice database
		createInvoiceDB();
		//creates an invoicedb
		InvoiceDB iv = new InvoiceDB(5004);
		//adds the invoice with taxid amount dates.
		iv.addInvoice(1004, 10.50, "10/10/10", "11/10/10");
		//Print the database
		for(int i = 0; i < iv.getInvoiceNumbers().size(); i++) {
			String checkifPaid = iv.checkifPaid(iv.getInvoiceNumbers().get(i));
			iv = new InvoiceDB(iv.getInvoiceNumbers().get(i));		
			System.out.println(iv.toString());		
			System.out.println("Yes/No: " + "Paid or Not Paid: InvoiceNumber = " +  iv.getInvoiceNumbers().get(i) + ": " + checkifPaid);
		}
		//Edited part.
		System.out.println();
		System.out.println("EDITS");
		//Payment
		iv.amountsReceived(5004, 10.50);	
		//Print the contents of the database
		for(int i = 0; i < iv.getInvoiceNumbers().size(); i++) {
			String checkifPaid = iv.checkifPaid(iv.getInvoiceNumbers().get(i));
			iv = new InvoiceDB(iv.getInvoiceNumbers().get(i));	
		
			System.out.println(iv.toString());		
			System.out.println("Yes/No: " + "Paid or Not Paid: InvoiceNumber = " +  iv.getInvoiceNumbers().get(i) + ": " + checkifPaid);
		}


		System.out.println();
		System.out.println("CUSTOMERDB");
		//Initializes customer's database
		createCustomerDB();
		CustomerAccounts c = new CustomerAccounts(1004);
		c.addCustomerAccounts("Tobi Wan", "2222 Three Street", "Four", "WA");
		System.out.println("BEFORE EDIT");
		//prints the contents of the database
		for(int i = 0; i < c.getTaxID().size(); i++) {
			String name = c.getCustomerName(c.getTaxID().get(i));
			String address = c.getCustomerAddress(c.getTaxID().get(i));
			int taxID = c.getTaxID().get(i);
			System.out.println(taxID + " " + name + " " + address);
		}	
		System.out.println();
		System.out.println("EDITS");
		//Remove a customer
		c.removeCustomerAccount(1001);
		c.editAddress(1003, "6666 Never Poor St.", "Seattle", "WA");
		//Edits a customer
		c.editCustomerName(1002, "Burning-God");
		//prints the contents of the database
		for(int i = 0; i < c.getTaxID().size(); i++) {
			String name = c.getCustomerName(c.getTaxID().get(i));
			String address = c.getCustomerAddress(c.getTaxID().get(i));
			int taxID = c.getTaxID().get(i);			
			System.out.println(taxID + " " + name + " " + address);
		}	
	}
	/**
	 * Creates/initializes the customer database.
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
	 * Creates/initializes the invoice database. 
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
