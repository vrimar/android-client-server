package com.a2.workoutapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import app_data.Workout;

public class WorkoutDetails  extends Activity  {
	private Workout w;
	private TextView workoutName, e1Name, e2Name, e1Desc, e2Desc, e1Muscle, e2Muscle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_details);
	
		w = (Workout) getIntent().getExtras().getSerializable("workout");
		
		workoutName = (TextView) findViewById(R.id.workout_name);
		e1Name = (TextView) findViewById(R.id.first_exercise_name);
		e1Desc = (TextView) findViewById(R.id.first_exercise_description);
		e1Muscle = (TextView) findViewById(R.id.first_exercise_muscle);
		e2Name = (TextView) findViewById(R.id.second_exercise_name);
		e2Desc = (TextView) findViewById(R.id.second_exercise_description);
		e2Muscle = (TextView) findViewById(R.id.second_exercise_muscle);
		
		if (w.getSetExercises().size() > 1) {
			workoutName.setText(w.getName());
			e1Name.setText(w.getSetExercises().get(0).getName());
			e1Desc.setText(w.getSetExercises().get(0).getDescription());
			e1Muscle.setText(w.getSetExercises().get(0).getMuscle());
			e2Name.setText(w.getSetExercises().get(1).getName());
			e2Desc.setText(w.getSetExercises().get(1).getDescription());
			e2Muscle.setText(w.getSetExercises().get(1).getMuscle());
		}
	}
	
	@Override
	public void onBackPressed()
	{
		//Terminate Activity.
		this.finish(); 
	}
}
