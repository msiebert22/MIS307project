import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class InvoiceDB {
	
	private int invoiceNum;
	
	public InvoiceDB(int invoice_Num) throws SQLException {
		invoiceNum = invoice_Num;
	}
	
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
	 * @throws SQLException
	 */
	public void amountsReceived(double amounts) throws SQLException {
		try(Connection con = ConnectionDB.getConnection()) {
			try(PreparedStatement prep = con.prepareStatement("UPDATE InvoiceDB SET InvoiceAmount = ?  "
					+ "WHERE InvoiceNum = ?")) {
				prep.setDouble(1,  - amounts);
				prep.setInt(2, invoiceNum);
				prep.executeUpdate();
			}
		}
	}

	public ArrayList<Integer> getInvoiceAmounts() throws SQLException {
		ArrayList<Integer> a = new ArrayList<Integer>();
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				ResultSet re = s.executeQuery("SELECT InvoiceAmount FROM InvoiceDB");
				while(re.next()) {
					a.add(re.getInt(1));		
				}
			}
		}
		return a;
		
	}
	/**
	 * Check whether a certain taxID is already paid or not.
	 * If the amount is zero then it is paid, otherwise not paid.
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
					if(rs.getDouble(1) > 0) {
						status = "N";
					} else {
						status = "Y";
					}
				}
			}		
		}
		
		return status;
	}
	
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
