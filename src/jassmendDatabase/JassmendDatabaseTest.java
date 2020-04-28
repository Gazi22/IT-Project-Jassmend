package jassmendDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
import com.mysql.cj.jdbc.JdbcConnection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JassmendDatabaseTest {
	
	public static void main(String[] args) {
	
	final String url = "jdbc:mysql://localhost:3306/projectjassmend";
	final String user = "root";
	final String pw = "AdminProject123";
	  
	/*{ try {
		// 1. Get a connection to database
		Connection myConnection = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", user, pw);
		// 2. Create a statement
		Statement myStmt = myConnection.createStatement();
		// 3. Execute SQL Query
		// ResultSet myRs = myStmt.executeQuery("Select * from id");
		
		String sqlStatement1 = "insert into UserInformation"
				+ " (username, userpassword)"
				+ " values ('User1', 'adminpw123')";
		
		myStmt.executeUpdate(sqlStatement1);
		
		// Console Check if Statement was executed
		System.out.println("Statement Complete.");
		
	}
	
	catch (Exception exc) {
		exc.printStackTrace();
	} }
	
	String query = "INSERT INTO `supplier`(`Company Name`, `Contact`, `Address`, `Postcode`, `Phone`) "
		    + "VALUES (?, ?)";
	Connection myConnection = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Basel", user, pw);
		try (PreparedStatement pstm = myConnection.prepareStatement(query)) {
		    pstm.setString(1, jTextField_SupplierCompany.getText());
		    pstm.setString(2, jTextField_SupplierContact.getText());
		    pstm.setString(3, jTextField_SupplierAddress.getText());
		    pstm.setString(4, jTextField_SupplierPostcode.getText());
		    pstm.setString(5, jTextField_SupplierPhone.getText());
			
			// 2. Create a statement
			Statement myStmt = myConnection.createStatement();
			// 3. Execute SQL Query
			// ResultSet myRs = myStmt.executeQuery("Select * from id");
			
			String sqlStatement1 = "insert into UserInformation"
					+ " (username, userpassword)"
					+ " values ('User1', 'adminpw123')";
			
			myStmt.executeUpdate(sqlStatement1);
			
			// Console Check if Statement was executed
			System.out.println("Statement Complete.");

		    pstm.executeUpdate();
		}
*/
}
}