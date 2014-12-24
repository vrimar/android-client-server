package app_data;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {

	private static final long serialVersionUID = 371341602701801251L;
	private String name;
	private ArrayList<Exercise> exercises;
	
	public Workout(String name, ArrayList<Exercise> e) {
		exercises = e;
		this.name = name;
	}
	
	public ArrayList<Exercise> getSetExercises() {
		return exercises;
	}
	
	public void setExercises(ArrayList<Exercise> e) {
		exercises = e;
	}
	
	public String getName() {
		return name;
	}
}
