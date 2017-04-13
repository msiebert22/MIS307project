import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AccountsReceivable {
	
	public void create() throws SQLException {
		
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				s.execute("CREATE TABLE ");
			}
		}
	}
	
	/**
	 * Drops the database and if it's empty, throws an exception
	 * @throws SQLException
	 */
	public void droptable() throws SQLException {
		try(Connection con = ConnectionDB.getConnection()) {
			try(Statement s = con.createStatement()) {
				try {
					s.execute("DROP THE TABLE SQL THING");
				} catch(SQLException e) {
					System.out.println("EMPTY TABLE");
				}
			}
		}
	}
	
}
