package data_access;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataAccess {

	public static Connection establishDBConnection() {
		String connectionString = "jdbc:mysql://sql4.freesqldatabase.com:3306?user=sql435250&password=qX7%25qD7!";
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionString);	
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return connection;
	}
}


