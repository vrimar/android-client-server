package data_access;

import java.sql.*;

import app_data.*;

public class QueryUser {
	
	public static boolean authenicateUser(Connection connection, String username, String password) 
			throws Exception, SQLException {
		boolean authenicated = false;
		ResultSet resultSet = null;
		
		try {
			Statement statement = (Statement) connection.createStatement();
			resultSet = statement
				.executeQuery("select * from sql435250.USERS WHERE username= '" 
						+ username + "' AND password = '" + password + "' LIMIT 1");
			
			if (!resultSet.isBeforeFirst() ) {    
				authenicated = false;
			} 
			else authenicated = true;
			
			resultSet.close();
			statement.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return authenicated;
	}
	
	public static User SelectUser(Connection connection, String username) {
		User u = null;
		try {
			Statement statement = (Statement) connection.createStatement();
			ResultSet  resultSet = statement
				.executeQuery("select * from sql435250.USERS WHERE username='" + username + "' limit 1");
			
			while(resultSet.next()) {
				String name = resultSet.getString("username");
				String password = resultSet.getString("password");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				int weight = resultSet.getInt("weight");
				u = new User(name, password, firstName, lastName, weight);
			}	
			
			resultSet.close();
			statement.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return u;
	}
	
	public static void RegisterUser(Connection connection, User user) {
		try {
			String query = "INSERT INTO sql435250.USERS(username, password, firstName, lastName, weight) VALUES('" +
					user.getUsername() + "','" + user.getPassword() + "','" + 
					user.getFirstName() + "','" + user.getLastName() + 	"','" +	user.getWeight() + "')";
			Statement statement = connection.createStatement();
			statement.execute(query);
			
			statement.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
