package com.a2.workoutapp;

import java.util.ArrayList;

import client_actions.ServerRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

@SuppressWarnings("unused")
public class ExerciseAdd extends Activity {

	private Exercise e;
	private TextView errorMessage;
	private EditText name, description;
	private Button addButton;
	private Spinner muscleGroup;

	String muscle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_add);
	
		MainActivity.SELECTED_TAB = 1;
		
		name = (EditText) findViewById(R.id.exercise_name_add);
		description = (EditText) findViewById(R.id.exercise_description_add);
		addButton = (Button) findViewById(R.id.button_add_exercise);
		muscleGroup = (Spinner) findViewById(R.id.spinner1);
		errorMessage = (TextView)findViewById(R.id.add_exercise_error);
		
		muscleGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        muscle = (String)item;
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	
		addButton.setOnClickListener(new Button.OnClickListener(){
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
                    progressDialog = new ProgressDialog(ExerciseAdd.this);
                    progressDialog.setMessage("Saving to server...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                }   
	      }
		
		@Override
		protected Object doInBackground(Exercise...params) {
			Object o = null;
			ServerRequest s = new ServerRequest();
			o = s.ExerciseAdd(params[0]);
			return o;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object o) {
			AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseAdd.this);
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
