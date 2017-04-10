
/**
* @author Inno Cabije & Michael Siebert
* Customer Accounts is a collection of customers.
*/ 
public class CustomerAccounts {
	
	/**
	 * 7 digits tax ID.
	 */
	private int taxID;
	/**
	 * Complete address, example. 123 North Street, Ames Iowa.
	 */
	public CustomerAccounts( int taxID) {
		this.taxID = taxID;
		// do we want a random generated number for the taxID, or have the user set it?
		//i would say a random number from 1 to 5000. But then again, setting numbers and stuff will go to the main method. we'll see.
	}
	
	public void addCustomerAccounts(String name, String address) throws SQLException
	{
		//Need another java class for this. Look at Homework 5 SimpleDataSource
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("INSERT"))
			{
				prep.setString(1, name);
				prep.setInt(2, taxID);
				prep.setString(3, address);
				prep.execute();
			}
		}
	}
	//Removes a customer via customer's taxID
	public void removeCustomerAccount(int taxID) throws SQLException {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("DELETE FROM idkDB WHERE TAXID = ?"))
			{
				prep.setString(1, taxID);
				prep.excecute();
			}
		}
	}
	public void editCustomerName(int taxID, String editedName) {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("UPDATE CUSTOMERNAME = ? WHERE TAXID = ? "))
			{
				prep.setString(1, name);
				prep.setString(2, taxID);
				prep.executeUpdate();
			}
		}
	}

	
	public String getCustomerName()	throws SQLException throws SQLException {
		
		try (Connection con = ConnectionDB.getConnection()) 
		{
			try (PreparedStatement prep = con.prepareStatement("SOME SQL THINGS / SELECT")) 
			{
				prep.setInt(1, taxID);
				ResultSet result = prep.executeQuery();
				result.next();
				
				return result.getString(1);
			}
		}
	}

}
