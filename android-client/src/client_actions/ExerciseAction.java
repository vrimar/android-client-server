package client_actions;

import app_data.Exercise;

public class ExerciseAction extends Action {
	
	private static final long serialVersionUID = 2330189378169761251L;
	private Exercise e;
	private String oldName;
	
	public ExerciseAction(Exercise e, String action, String oldName) {
		super(action);
		this.setExercise(e);
		this.oldName = oldName;
	}
	
	public ExerciseAction(Exercise e, String action) {
		super(action);
		this.setExercise(e);
	}
	
	public ExerciseAction(String action) {
		super(action);
	}

	public Exercise getExercise() {
		return e;
	}

	public void setExercise(Exercise e) {
		this.e = e;
	}
	
	public String getOldName() {
		return oldName;
	}
	
	public void setOldName(String s) {
		oldName = s;
	}
	
}
