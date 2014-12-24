package data_access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app_data.Exercise;
import app_data.Workout;

public class QueryWorkout {
	public static void AddWorkout(Connection connection, Workout w) {
		try {
		String query = "INSERT INTO sql435250.WORKOUTS(name, username, exercise1, exercise2) VALUES('" +
			w.getName() + "','vasil','" + w.getSetExercises().get(0).getName() + "','" + w.getSetExercises().get(1).getName() + "')";
		Statement statement = connection.createStatement();
		statement.execute(query);
		statement.close();
		}
		catch (SQLException sql) {
			sql.printStackTrace();
		}
		
	}
	public static void DeleteWorkout(Connection connection, Workout w) {
		try {
			String query = "DELETE FROM sql435250.WORKOUTS WHERE name='" + w.getName() + "'";
			Statement statement = connection.createStatement();
			statement.execute(query);
			statement.close();
		}
		catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	public static ArrayList<Workout> SelectWorkouts(Connection connection) {
		ArrayList<Workout> workoutList = new ArrayList<Workout>();

		try {
		String query = "SELECT * FROM sql435250.WORKOUTS";
		Statement statement = connection.createStatement();
		
		if (statement.execute(query)) {
			ResultSet resultSet = statement.getResultSet();			
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				String exercise1 = resultSet.getString("exercise1");
				String exercise2 = resultSet.getString("exercise2");
				ArrayList<Exercise> e = new ArrayList<Exercise>();
				
				String query2 = "SELECT * FROM sql435250.EXERCISES WHERE name='" + exercise1 + "' OR name='" + exercise2 +"'";
				Statement statement2 = connection.createStatement();
				statement2.execute(query2);
				ResultSet resultSet2 = statement2.getResultSet();		
				while(resultSet2.next()) {
					String name2 = resultSet2.getString("name");
					String description = resultSet2.getString("description");
					String muscle = resultSet2.getString("muscle");
					int isCustom = Integer.parseInt(resultSet2.getString("isCustom"));
					e.add(new Exercise(name2, description, muscle, isCustom));
				}
				resultSet2.close();
				statement2.close();
				workoutList.add(new Workout(name, e));
			}
			resultSet.close();
			statement.close();
		}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return workoutList;
	}
}
