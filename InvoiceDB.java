import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Invoice Database class.
 * @author Inno & Michael
 *	@date 4/21/2017
 */

public class InvoiceDB {
	
	/**
	 * Invoice Number
	 */
	private int invoiceNum;
	
	/**
	 * Invoice class constructor. Initializes a invoice number.
	 * @param invoice_Num - primary key
	 * @throws SQLException
	 */
	public InvoiceDB(int invoice_Num) throws SQLException {
		invoiceNum = invoice_Num;
	}
	
	/**
	 * Adds the invoice's to the database.
	 * @param taxID - taxID of the customer who did the invoice
	 * @param amount - The amount that the invoice has
	 * @param date - The date when the invoice was received
	 * @param dueDate - the due date of the invoice. the date where the customer has to pay.
	 * @throws SQLException
	 */
	public void addInvoice(int taxID, double amount, String date, String dueDate)
				throws SQLException  
	{
		try(Connection con = ConnectionDB.getConnection()) {
			try(PreparedStatement prep = con.prepareStatement("INSERT INTO InvoiceDB (InvoiceNum, TaxID, InvoiceAmount, "
					+ "InvoiceDate, Invoice_DueDate) VALUES(?,?,?,?,?)")) {
				prep.setInt(1, invoiceNum);
				prep.setInt(2, taxID);
				prep.setDouble(3, amount);
				prep.setString(4, date);
				prep.setString(5, dueDate);
				prep.execute();				
			}
		}
	}
	/**
	 * Removes a certain invoice from the database
	 * @param invoice_Num - The primary key
	 * @throws SQLException
	 */
	public void removeInvoice(int invoice_Num) throws SQLException {
		try(Connection con = ConnectionDB.getConnection()) {
			try(PreparedStatement prep = con.prepareStatement("DELETE FROM InvoiceDB WHERE InvoiceNum = ?")) {
				prep.setInt(1, invoice_Num);
				prep.executeUpdate();
			}
		}
	}
	/**
	 * Additional payments from a customer.
	 * Amounts received from customers for invoice 
	 * @amount - the additional amount a customer paid
	 * @invoice_Num - Invoice Number
	 * @throws SQLException
	 */
	public void amountsReceived(int invoice_Num, double amounts) throws SQLException {
		try(Connection con = ConnectionDB.getConnection()) {
			try(PreparedStatement prep = con.prepareStatement("UPDATE InvoiceDB SET InvoiceAmount = InvoiceAmount - ?  "
					+ "WHERE InvoiceNum = ?")) {
				
				prep.setDouble(1,  amounts);
				prep.setInt(2, invoice_Num);
				
				prep.executeUpdate();
			}
		}
	}
	/**
	 * Check whether a certain taxID is already paid or not.
	 * If the amount is zero then it is paid, otherwise not paid.
	 * Uses the removeInvoice if it is already paid.
	 * @param taxID - taxID of a customer.
	 * @return Y for already paid. N for not yet paid
	 * @throws SQLException
	 */
	public String checkifPaid(int invoice_Num) throws SQLException {
		String status = "";
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement prep = con.prepareStatement("SELECT InvoiceAmount FROM InvoiceDB WHERE InvoiceNum = ?")) {
				
				prep.setInt(1, invoice_Num);
				ResultSet rs = prep.executeQuery();
				while(rs.next()) {
					if(rs.getDouble(1) <= 0) {
						status = "Yes";					
					} else {
						status = "No";	
					}
				}
			}		
		}		
		return status;
	}
	/**
	 * Creates an Arraylist of Invoice Numbers from the database.
	 * @return - an arraylist of integer/invoicenumbers
	 * @throws SQLException
	 */
	public ArrayList<Integer> getInvoiceNumbers() throws SQLException {
		ArrayList<Integer> invoiceNums = new ArrayList<Integer>();
		try (Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				ResultSet result = s.executeQuery("SELECT InvoiceNum FROM InvoiceDB");
				while(result.next()) {
					invoiceNums.add(result.getInt(1));
				}
			}
		}
		return invoiceNums;
	}

	/**
	 * Creates a string of the Invoice Database.
	 */
	public String toString() 
	{
		String string ;
		try(Connection con = ConnectionDB.getConnection()) 
		{		
			try(PreparedStatement s = con.prepareStatement("SELECT TaxID, InvoiceAmount, InvoiceDate, Invoice_DueDate FROM InvoiceDB WHERE InvoiceNum = ?"))
			{
				s.setInt(1, invoiceNum);
				ResultSet r = s.executeQuery();
				r.next();
				string = String.format("Invoice: %d TaxID: %d Amount: %.2f Date: %s DueDate: %s", invoiceNum, r.getInt(1), r.getDouble(2), r.getString(3), r.getString(4));
				
			}
		}
		catch (SQLException e) {
			string = "SQLException!: " + e.getMessage();
		}
		return string;
	}


}
