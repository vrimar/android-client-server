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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import app_data.Exercise;

public class ExerciseEdit extends Activity {

	private Exercise e;
	private TextView errorMessage;
	private EditText name, description;
	private Button updateButton;
	private Spinner muscleGroup;
	String muscle, oldName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_edit);
	
		MainActivity.SELECTED_TAB = 1;
		
		e = (Exercise) getIntent().getExtras().getSerializable("exercise");
		oldName = e.getName();
		
		name = (EditText) findViewById(R.id.exercise_edit_name);
		description = (EditText) findViewById(R.id.exercise_edit_description);
		muscleGroup = (Spinner) findViewById(R.id.exercise_edit_spinner);
		updateButton = (Button) findViewById(R.id.button_exercise_edit);
		name.setText(e.getName());
		description.setText(e.getDescription());
		
		muscleGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        muscle = (String)item;
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	
		updateButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				String n = name.getText().toString();
				String d = description.getText().toString();
				
				if (n != "" && d != "")
				{
					e = new Exercise(n, d, muscle, 1);										
					AddExerciseAsync addAsync  = new AddExerciseAsync();
					addAsync.execute(e);
				}
				else {
					errorMessage.setText("Please fill out the fields");
				}
			}
		});
	}
	
	private class AddExerciseAsync extends AsyncTask<Exercise, Void, Object>{

		ProgressDialog progressDialog;
		
			@Override
	      protected void onPreExecute() {
             if (progressDialog == null) {
                    progressDialog = new ProgressDialog(ExerciseEdit.this);
                    progressDialog.setMessage("Updating Exercise...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                }   
	      }
		
		@Override
		protected Object doInBackground(Exercise...params) {
			Object o = null;
			ServerRequest s = new ServerRequest();
			o = s.ExerciseUpdate(params[0], oldName);
			return o;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object o) {
			try{
				
				if (progressDialog.isShowing()) {
                    progressDialog.dismiss();     
    			}
				ArrayList<Exercise> ae = (ArrayList<Exercise>)o;
				MainActivity.setExercises(ae);
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

