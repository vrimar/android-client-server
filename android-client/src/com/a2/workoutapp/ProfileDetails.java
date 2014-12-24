package com.a2.workoutapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app_data.User;

public class ProfileDetails extends android.support.v4.app.Fragment {
	TextView username, fullName, weight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) { 	 
		super.onActivityCreated(savedInstanceState);
		User u;
		u = MainActivity.getProfile();

		username = (TextView)getView().findViewById(R.id.profile_username);
		fullName = (TextView)getView().findViewById(R.id.profile_fullName);
		weight = (TextView)getView().findViewById(R.id.profile_weight);		
		username.setText(u.getUsername());
		fullName.setText(u.getFirstName() + " " + u.getLastName());
		weight.setText(String.valueOf(u.getWeight()) + " lbs.");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.activity_profile, container, false);		
	}
}
