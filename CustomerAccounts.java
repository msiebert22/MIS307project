import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
* @author Inno Cabije & Michael Siebert
* Customer Accounts is a collection of customers. 
* CustomerAccounts class is the class that stores all of the customer's identification to the database
*/ 
public class CustomerAccounts {
	
	/**
	 * 4 digits tax ID.
	 */
	private int taxID;
	/**
	 * The constructor of the class. Initializes with the taxID of a customer.
	 * @param taxID - taxID is the primary key.
	 */
	public CustomerAccounts( int taxID) {
		this.taxID = taxID;
	}
	/**
	 * This method adds a single customer to the database.
	 * @param name - The customer's name. First then last name.
	 * @param street - The street and house number of the customer. eg. 123 North St.
	 * @param city - City where the customer lives
	 * @param state - State which the customer lives. Two letters. eg. "IA" for Iowa
	 * @throws SQLException
	 */
	public void addCustomerAccounts(String name, String street, String city, String state) throws SQLException
	{
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("INSERT INTO CustomerDB 
					+ "(TaxID, CustomerName, CustomerAddressStreet,"
					+ "CustomerAddressCity, CustomerAddressState) VALUES(?,?,?,?,?"))
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
	/**
	 * This method removes a customer from the database by using the taxID.
	 * @param taxID - uses taxID to find which customer should be removed.
	 * @throws SQLException
	 */
	public void removeCustomerAccount(int taxID) throws SQLException {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("DELETE FROM CustomerDB WHERE TaxID = ?"))
			{
				prep.setString(1, taxID);
				prep.excecute();
			}
		}
	}
	/**
	 * This method edits a customer's name.
	 * @param taxID -uses taxID to find which customer will be edited
	 * @param editedName - the updated name of a customer. First name then last name.
	 * @throws SQLException
	 */	
	public void editCustomerName(int taxID, String editedName) {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(try(PreparedStatement prep = con.prepareStatement("UPDATE CustomerDB SET CustomerName = ? 
									      + "WHERE TaxID = ? "))		{
				prep.setString(1, taxID);
				prep.setString(2, editedName);
				prep.executeUpdate();
			}
		}
	}
	/**
	 * This method gets a customer name from the database
	 * @return - returns the customer name.
	 * @throws SQLException
	 */
	
	public String getCustomerName()	throws SQLException {		
		String name;
		try (Connection con = ConnectionDB.getConnection()) {
			try (PreparedStatement prep = con.prepareStatement("SELECT CustomerName FROM CustomerDB WHERE TaxID = ?")) {
				prep.setInt(1, taxID);
				ResultSet result = prep.executeQuery();
				result.next();			
				
				name = result.getString(2);
			}
		} catch (SQLException e) {
			name = "ERROR!: " + e.getMessage();
		}
		return name;	
	}
	/**
	 * This method gets the street, city then state or the complete address of the customer.
	 * @return the completed address of a customer.
	 * @throws SQLException
	 */
	public String getCustomerAddress() throws SQLException {
		String address;
		try(Connection con = ConnectionDB.getConnection()){
			try (PreparedStatement prep = con.prepareStatement("SELECT CustomerAddressStreet, 
					 + "CustomerAddressCity, CustomerAddressState"
					 + "FROM CustomerDB WHERE TaxID = ?"))			{
				prep.setInt(1, taxID);
				ResultSet r = prep.executeQuery();
				r.next();
				address = r.getString(1) + "" + r.getString(2) + "" + r.getString(3);
			}					
		} catch (SQLException e) {
			address = "ERROR!: " + e.getMessage();
		} 
		return address;
}
