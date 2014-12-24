package data_access;

import app_data.Exercise;

import java.sql.*;
import java.util.ArrayList;

public class QueryExercise {
	
	public static ArrayList<Exercise> SelectExercises(Connection connection) throws SQLException {
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		
		String query = "SELECT * FROM sql435250.EXERCISES";
		Statement statement = connection.createStatement();
		
		if (statement.execute(query)) {
			ResultSet resultSet = statement.getResultSet();			
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String muscle = resultSet.getString("muscle");
				int isCustom = Integer.parseInt(resultSet.getString("isCustom"));
				exerciseList.add(new Exercise(name, description, muscle, isCustom));
			}
			resultSet.close();
			statement.close();
		}
		
		return exerciseList;
	}
	
	public static boolean DeleteExercises(Connection connection, String predicate) {
		try {
			String query = "DELETE FROM sql435250.EXERCISES WHERE name='" + predicate + "'";
			Statement statement = connection.createStatement();
			statement.execute(query);
			statement.close();
		}
		catch (SQLException sql) {
			sql.printStackTrace();
		}
	
		
		return true;
	}
	
	public static void UpdateExercise(Connection connection, Exercise e, String oldName) {
		try {
		String query = "UPDATE sql435250.EXERCISES SET name='" + 
				e.getName() + "', description='" + e.getDescription() + "', muscle='" + 
				e.getMuscle() + "' WHERE name='" + oldName + "'";
			Statement statement = connection.createStatement();
			statement.execute(query);
			statement.close();
		}
		catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	public static void AddExercise(Connection connection, Exercise e) {
		try {
			String query = "INSERT INTO sql435250.EXERCISES(name, description, muscle, isCustom) VALUES('" +
				e.getName() + "','" + e.getDescription() + "','" + e.getMuscle() + "'" + ", 1)";
			Statement statement = connection.createStatement();
			statement.execute(query);
			statement.close();
		}
		catch (SQLException sql) {
			sql.printStackTrace();
		}
		
	}
	
}
