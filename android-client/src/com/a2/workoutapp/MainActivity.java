package com.a2.workoutapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import app_data.*;

import java.util.ArrayList;
import client_actions.InitialAction;

public class MainActivity extends FragmentActivity implements TabListener {
	private static boolean RUN_ONCE = true;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	public static int SELECTED_TAB = 0;
	private static ArrayList<Exercise> exercises;
	private static ArrayList<Workout> workouts;
	private static User profile;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		if (RUN_ONCE) {
		    RUN_ONCE = false;
		    InitialAction ia = new InitialAction();
			ia = (InitialAction)getIntent().getExtras().getSerializable("initialData");
			exercises = ia.getExercises();
			workouts = ia.getWorkouts();
			profile = ia.getUser();  
		}
		// Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
               
        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText("WorkOuts").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Exercises").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Profile").setTabListener(this));   
        
        getActionBar().setSelectedNavigationItem(SELECTED_TAB);
	}

	@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
    }
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		if (tab.getPosition() == 0) {			
			WorkoutList workoutList = new WorkoutList();
    		getSupportFragmentManager().beginTransaction().replace(R.id.container, workoutList).commit();
    	} 
    	else if (tab.getPosition() == 1) {
    		ExerciseList exerciseList = new ExerciseList();
    		getSupportFragmentManager().beginTransaction().replace(R.id.container, exerciseList).commit();
		}    	
    	else if (tab.getPosition() == 2) {
    		ProfileDetails profile = new ProfileDetails();
    		getSupportFragmentManager().beginTransaction().replace(R.id.container, profile).commit();
		}    
    }
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static ArrayList<Exercise> getExercises() {	
		return exercises;
	}
	
	public static void setExercises(ArrayList<Exercise> e) {
		exercises = e;
	}

	public static ArrayList<Workout>getWorkouts() {
		return workouts;
	}
	
	public static void setWorkouts(ArrayList<Workout> w) {
		workouts = w;
	}
	
	public static User getProfile() {
		return profile;
	}

}
