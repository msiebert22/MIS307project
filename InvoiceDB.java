import java.util.*;
import java.sql.*;

public class InvoiceDB {
	private int invoiceNum; // random assigned invoiceNum for identification
	private double amount;  // amount customer owes us
	private int taxID; // TaxID customer identifies with
	private String dueDate; // When amount is due
	private String dates;   // dates for when amount is charged, and due back and over due.
	
	InvoiceDB(int invoiceNum, double amount, String date, String duedate, int taxID) {

	}
	
	//Connect to driver manager and get all information (invoiceNum, amount, taxID, dates, dueDates)
	public void getInvoice() throws SQLException { 	
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement
			    + ("SELECT invoice FROM invoiceDB WHERE invoiceNum = ?"))
			{
				prep.setInt(1, invoiceNum);
				prep.setDouble(2, amount);
				prep.setInt(3, taxID);
				prep.setString(4, dueDate);
				prep.setString(5, dates);
				prep.execute();
			}
	   	}
	}
	//Cannot Edit invoices, must delete and add new. 
	public void removeInvoice(int invoiceNum) throws SQLException {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement
			    + ("DELETE invoice FROM invoiceDB WHERE INVOICENUM = ?"))
			{
				prep.setInt(1, invoiceNum);
				prep.setInt(3, taxID);
				prep.execute();
			}
		}
	}
	
	public double getAmount() { 
		return amount; 
	  }  
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDates(String dates) { 
		return dates;
	  }
	public void setDates(String dates) {
		this.dates = dates;
	}
	public void setDueDate(String dueDate) { 
		this.dueDate = dueDates;
	}

}
