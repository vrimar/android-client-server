package com.a2.workoutapp;

import java.util.ArrayList;

import client_actions.ServerRequest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import app_data.Exercise;
import app_data.Workout;

public class WorkoutAdd extends Activity {
	private Spinner e1, e2;
	Button addWorkout;
	String exercise1, exercise2;
	EditText name;
	int exercise1Pos, exercise2Pos;
	private ArrayList<Exercise> exercises;
	private String[] exercisesList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_add);
	
		MainActivity.SELECTED_TAB = 0;
		
		name = (EditText)findViewById(R.id.workout_add_name);
		e1 = (Spinner) findViewById(R.id.workout_add_e1);
		e2 = (Spinner) findViewById(R.id.workout_add_e2);
		addWorkout = (Button)findViewById(R.id.button_workout_add);
		
		exercises = MainActivity.getExercises();	
		exercisesList = new String[exercises.size()];
		for(int i = 0; i < exercises.size(); i++) {
			exercisesList[i] = exercises.get(i).getName();
		}
		
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
	            this, android.R.layout.simple_spinner_item, exercisesList);
	    spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

	    e1.setAdapter(spinnerArrayAdapter);
	    e2.setAdapter(spinnerArrayAdapter);

		e1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        exercise1Pos = pos;
		        exercise1 = (String)item;
		    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {			
			}
		});
		e2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        exercise2 = (String)item;
		        exercise1Pos = pos;
		    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {		
			}
		});
		addWorkout.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				ArrayList<Exercise> ae = new ArrayList<Exercise>();
				ae.add(exercises.get(exercise1Pos));
				ae.add(exercises.get(exercise2Pos));
				Workout w = new Workout(name.getText().toString(), ae);
				AddWorkoutAsync async = new AddWorkoutAsync();
				async.execute(w);
				
			}
		});
	}
	
	private class AddWorkoutAsync extends AsyncTask<Workout, Void, Object>{

		ProgressDialog progressDialog;
		
			@Override
	      protected void onPreExecute() {
             if (progressDialog == null) {
                    progressDialog = new ProgressDialog(WorkoutAdd.this);
                    progressDialog.setMessage("Saving to server...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                }   
	      }
		
		@Override
		protected Object doInBackground(Workout...params) {
			Object o = null;
			ServerRequest s = new ServerRequest();
			o = s.WorkoutAdd(params[0]);
			return o;
		}

		@SuppressWarnings({ "unchecked" })
		@Override
		protected void onPostExecute(Object o) {
			try{
				
				if (progressDialog.isShowing()) {
                    progressDialog.dismiss();     
    			}
            	ArrayList<Workout> aw = (ArrayList<Workout>)o;
				MainActivity.setWorkouts(aw);
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    		startActivity(intent);
				finished();
			}
			catch(Exception ex){			
			}			
		} 	
    }
	
	@Override
	public void onBackPressed()
	{
		this.finish(); 
	}
	
	public void finished() {
		this.finish();
	}
}
