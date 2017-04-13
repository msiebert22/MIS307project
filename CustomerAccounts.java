
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
	
	public void addCustomerAccounts(String name, String street, String city, String state) throws SQLException
	{
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("INSERT INTO CustomerDB (TaxID, CustomerName, CustomerAddressStreet, "
					+ "CustomerAddressCity, CustomerAddressState) VALUE(?,?,?,?,?"))
			{
				prep.setInt(1, taxID);
				prep.setString(2, name);
				prep.setString(3, street);
				prep.setString(4, city);
				prep.setString(5, state);
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
				prep.setString(1, taxID);
				prep.setString(2, name);
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
