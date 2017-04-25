import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * CustomerAccounts class is the class that stores all of the customer's identification to the database
 * @author Inno & Michael
 * @date 4/19/2017
 *
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
			try(PreparedStatement prep = con.prepareStatement("INSERT INTO CustomerDB (TaxID, CustomerName, CustomerAddressStreet, "
					+ "CustomerAddressCity, CustomerAddressState) VALUES(?,?,?,?,?)"))
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
	 * @param taxID -  uses taxID to find which customer should be removed.
	 * @throws SQLException
	 */
	public void removeCustomerAccount(int taxID) throws SQLException {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("DELETE FROM CustomerDB WHERE TaxID = ?"))
			{
				prep.setInt(1, taxID);
				prep.execute();
			}
		}
	}
	/**
	 * This method edits a customer's name.
	 * @param taxID - uses taxID to find which customer will be edited
	 * @param editedName - the updated name of a customer. First name then last name.
	 * @throws SQLException
	 */
	public void editCustomerName(int tax_ID, String editedName)throws SQLException {
		try (Connection con = ConnectionDB.getConnection())
		{
			try(PreparedStatement prep = con.prepareStatement("UPDATE CustomerDB SET CustomerName = ? WHERE TaxID = ? "))
			{
				prep.setString(1, editedName);
				prep.setInt(2, tax_ID);
				prep.executeUpdate();
			}
		}
	}	
	/**
	 * Edits the address of someone with certain taxID
	 * @param tax_ID - Primary key
	 * @param editedStreet - the String of the street you want to edit
	 * @param editedCity - String of the city you want to edit
	 * @param editedState String of the State you want to edit
	 * @throws SQLException
	 */
	public void editAddress(int tax_ID, String editedStreet, String editedCity, String editedState) throws SQLException {
		try(Connection c = ConnectionDB.getConnection()) {
			try(PreparedStatement p = c.prepareStatement("UPDATE CustomerDB SET CustomerAddressStreet = ?, CustomerAddressCity = ?, CustomerAddressState = ? "
					+ "WHERE TaxID = ?")) {
			
				p.setString(1, editedStreet);
				p.setString(2, editedCity);
				p.setString(3, editedState);
				p.setInt(4, tax_ID);
				p.executeUpdate();
			}
		}
	}
	/**
	 * This method gets a customer name from the database
	 * @return - returns the customer name.
	 * @throws SQLException
	 */
	public String getCustomerName(int tax_ID)	throws SQLException  {
		String name;
		try (Connection con = ConnectionDB.getConnection()) 
		{
			try (PreparedStatement prep = con.prepareStatement("SELECT CustomerName FROM CustomerDB WHERE TaxID = ?")) 
			{
				prep.setInt(1, tax_ID);
				ResultSet result = prep.executeQuery();
				result.next();
				
				name = result.getString(1);
			}
		}
		catch (SQLException e) {
			name = "ERROR!: " + e.getMessage();
		}
		return name;
	}
	/**
	 * This method gets the street, city then state or the complete address of the customer.
	 * @return the completed address of a customer.
	 * @throws SQLException
	 */
	public String getCustomerAddress(int tax_ID) throws SQLException {
		String address;
		try(Connection con = ConnectionDB.getConnection())
		{
			try (PreparedStatement prep = con.prepareStatement("SELECT CustomerAddressStreet, CustomerAddressCity, CustomerAddressState FROM CustomerDB WHERE TaxID = ?"))
			{
				prep.setInt(1, tax_ID);
				ResultSet r = prep.executeQuery();
				
				r.next();
				address = r.getString(1) + " " + r.getString(2) + ", " + r.getString(3); 
			}
					
		} catch (SQLException e) {
			address = "ERROR!: " + e.getMessage();
		} 
		return address;
	}
	
	/**
	 * Creates an arraylist of integers from the taxIDs of CustomerDB.
	 * @return an Arraylist of taxID
	 * @throws SQLException
	 */
	public ArrayList<Integer> getTaxID()throws SQLException {
		ArrayList<Integer> tax = new ArrayList<Integer>();
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				ResultSet rs = s.executeQuery("SELECT TaxID FROM CustomerDB");
						while(rs.next()) {
							tax.add(rs.getInt(1));
						}
			}
		}
		return tax;
	}

}

