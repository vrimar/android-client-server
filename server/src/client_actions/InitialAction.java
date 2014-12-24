package client_actions;

import java.util.ArrayList;
import app_data.*;

public class InitialAction extends Action {

	private static final long serialVersionUID = -7894526205054776114L;
	private ArrayList<Exercise> exercises;
	private ArrayList<Workout> workouts;
	private User user;
	
	public InitialAction() {
		super("Fetch");
	}
	public InitialAction(String s, ArrayList<Workout> w, ArrayList<Exercise> e,User u ) {
		super(s);
		exercises = e;
		workouts = w;
		user = u;
	}
	
	public ArrayList<Exercise> getExercises() {
		return exercises;
	}
	
	public ArrayList<Workout> getWorkouts() {
		return workouts;
	}
	
	public User getUser() {
		return user;
	}

}
