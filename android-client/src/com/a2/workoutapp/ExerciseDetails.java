package com.a2.workoutapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import app_data.Exercise;

public class ExerciseDetails extends Activity {

	private Exercise e;
	private TextView name, description, muscle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_details);
		MainActivity.SELECTED_TAB = 1;
		e = (Exercise) getIntent().getExtras().getSerializable("exercise");
		
		name = (TextView) findViewById(R.id.exercise_name);
		description = (TextView) findViewById(R.id.exercise_description);
		muscle = (TextView) findViewById(R.id.exercise_muscle);
		
		name.setText(e.getName());
		description.setText(e.getDescription());
		muscle.setText(e.getMuscle());
		
	}
	
	@Override
	public void onBackPressed()
	{
		//Terminate Activity.
		this.finish(); 
	}
}
