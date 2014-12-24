package com.a2.workoutapp;

import java.util.ArrayList;

import client_actions.ServerRequest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import app_data.Workout;

public class WorkoutList extends ListFragment {

	private String workoutStringList[];
	private ArrayList<Workout> workouts;
	ProgressDialog progressDialog;
	
	public WorkoutList() {
		workouts = new ArrayList<Workout>();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MainActivity.SELECTED_TAB = 0;
		
		progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        
		workouts = MainActivity.getWorkouts();
		workoutStringList = new String[workouts.size()];	
		for(int i = 0; i < workouts.size(); i++) 
			workoutStringList[i] = workouts.get(i).getName();
		
		ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, workoutStringList);
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
	        	Intent intent = new Intent(getActivity().getApplicationContext(), WorkoutAdd.class);
	    		 startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.workout_list, container, false);		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) { 
		registerForContextMenu(getListView());	 
		super.onActivityCreated(savedInstanceState);	 
	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent intent = new Intent(getActivity().getBaseContext(), WorkoutDetails.class);
		intent.putExtra("workout", workouts.get(position));
		 startActivity(intent);
    }	
	// Context menu
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
		        ContextMenuInfo menuInfo) {
		    super.onCreateContextMenu(menu, v, menuInfo);
		    menu.add(0, 0, 0, "Delete");
		}
		
		@Override
		public boolean onContextItemSelected(android.view.MenuItem item) {
	        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		    switch (item.getItemId()) {
		    case 0:
		    	DeleteAsync task = new DeleteAsync();
		    	task.execute(String.valueOf(info.position));
		    	return true;
		    }
		    return super.onContextItemSelected(item);
		}

		private class DeleteAsync extends AsyncTask<String, Void, Object> {
			@Override
			protected Object doInBackground(String... params) {
				Object o;
				ServerRequest server = new ServerRequest();
				o = server.WorkoutDelete(workouts.get(Integer.parseInt(params[0])));
				return o;	
			}
			@Override
			protected void onPreExecute() {
				progressDialog.setMessage("Deleting workout...");
				progressDialog.show();
			}
			
			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(Object o) {
							
				workouts = (ArrayList<Workout>)o;
				MainActivity.setWorkouts(workouts);
				workoutStringList = new String[workouts.size()];	
				for(int i = 0; i < workouts.size(); i++) 
					workoutStringList[i] = workouts.get(i).getName();
								
				progressDialog.hide();
				ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, workoutStringList);
				setListAdapter(listAdapter);
			}
		}
		
}
