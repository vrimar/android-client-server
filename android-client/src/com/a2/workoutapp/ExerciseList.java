package com.a2.workoutapp;

import java.util.ArrayList;

import client_actions.ServerRequest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import app_data.Exercise;

public class ExerciseList extends ListFragment{
	private ArrayList<Exercise> exercises;
	private String[] exercisesList;
	ProgressDialog progressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        
		exercises = MainActivity.getExercises();	
		exercisesList = new String[exercises.size()];
		
		for(int i = 0; i < exercises.size(); i++) {
			exercisesList[i] = exercises.get(i).getName();
		}
		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,exercisesList);
		setListAdapter(listAdapter);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.activity_main, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_add_exercise:
	        	Intent intent = new Intent(getActivity().getApplicationContext(), ExerciseAdd.class);
	    		 startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) { 
		registerForContextMenu(getListView());	 
		super.onActivityCreated(savedInstanceState);	 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.exercise_list, container, false);		
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent intent = new Intent(getActivity().getBaseContext(), ExerciseDetails.class);
		intent.putExtra("exercise", exercises.get(position));
		startActivity(intent);
    }

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, 0, 0, "Edit");
	    menu.add(0, 1, 0, "Delete");
	}
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	    case 0:
	    	Intent intent = new Intent(getActivity().getBaseContext(), ExerciseEdit.class);
			intent.putExtra("exercise", exercises.get(info.position));
			startActivity(intent);
	    	return true;
	    case 1:
	    	DeleteAsync task = new DeleteAsync();
	    	task.execute(String.valueOf(info.position));
	    }
	    return true;
	}
	
	private class DeleteAsync extends AsyncTask<String, Void, Object> {
		@Override
		protected Object doInBackground(String... params) {
			Object o = null;
			ServerRequest server = new ServerRequest();
			o = server.ExerciseDelete(exercises.get(Integer.parseInt(params[0])));
			return o;	
		}
		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Deleting exercise...");
			progressDialog.show();
		}
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Object o) {
			exercises = (ArrayList<Exercise>)o;
			MainActivity.setExercises(exercises);
			
			String[] exerciseStringList = new String[exercises.size()];
			for(int i = 0; i < exercises.size(); i++) {
				exerciseStringList[i] = exercises.get(i).getName();
			}
			progressDialog.hide();
			ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, exerciseStringList);
			setListAdapter(listAdapter);
		}
	}
}

