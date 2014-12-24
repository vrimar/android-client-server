package client_actions;

import app_data.Workout;

public class WorkoutAction extends Action {
	
	private static final long serialVersionUID = 5674612609886223549L;
	private Workout workout;
	
	public WorkoutAction(Workout w, String s) {
		super(s);
		workout = w;
	}
	
	public Workout getWorkout() {
		return workout;
	}
	
	public void setWorkout(Workout w) {
		workout = w;
	}
		
}
