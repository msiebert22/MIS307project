import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
* @author Inno Cabije & Michael Siebert
* Customer Accounts is a collection of customers.
*/ 
public class CustomerAccounts {
	
	/**
	 * 7 digits tax ID.
	 */
	private int taxID;

	public CustomerAccounts( int taxID) {
		this.taxID = taxID;
	}
	
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
	//Removes a customer via customer's taxID
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
