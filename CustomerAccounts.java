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
	}
	
	public void addCustomerAccounts(String name, String address) throws SQLException
	{
		//Need another java class for this. Look at Homework 5 SimpleDataSource
		try (Connection con = DriverManager.getConnection("", "", ""))
		{
			try(PreparedStatement prep = con.prepareStatement("SOME SQL THINGS"))
			{
				prep.setString(1, name);
				prep.setInt(2, taxID);
				prep.setString(3, address);
				prep.execute();
			}
		}
	}
	//Removes a customer via customer name or taxID?
	public void removeCustomerAccount(String name) throws SQLException {
		
	}
	//edit a customer. if x is 1 for customername, x is 2 for customer address
	public void editCustomer(int x) {
		
	}

	
	public String getCustomerName()	throws SQLException throws SQLException {
		
		try (Connection con = DriverManager.getConnection("","","")) 
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
	
	public int getTaxID() {
		return taxID;
	}
	
	public String getAddress() throws SQLException{
		//I think this is unnecessary. Just leave it here for now.
	}
}
