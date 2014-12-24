package com.a2.workoutapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import client_actions.*;
import app_data.*;

public class RegisterActivity extends FragmentActivity {
	
	EditText username, password, retypePassword, firstName, lastName, weight;
	Button registerPost;
	
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		username = (EditText)findViewById(R.id.register_username);
		password = (EditText)findViewById(R.id.register_password);
		firstName = (EditText)findViewById(R.id.register_firstname);
		lastName = (EditText)findViewById(R.id.register_lastname);
		weight = (EditText)findViewById(R.id.register_weight);
		
		registerPost = (Button)findViewById(R.id.button_registerpost);
		
		registerPost.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				Register registerAsync = new Register();
				registerAsync.execute();
			}
    	});
	}
	private class Register extends AsyncTask<Void, Void, Object>{

		ProgressDialog progressDialog;
		
		@Override
		protected Object doInBackground(Void...voids) {
			User user = new User(username.getText().toString(), 
					password.getText().toString(),
					firstName.getText().toString(),
					lastName.getText().toString(),
					Integer.parseInt(weight.getText().toString()));
			
			UserAction ua = new UserAction(user, "Register");
			ServerRequest server = new ServerRequest();
			Object o = server.Login(ua);
			return o;
		}
		
		@Override
	      protected void onPreExecute() {
                 if (progressDialog == null) {
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Registering...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
            }   
	      }

		@Override
		protected void onPostExecute(Object o) {
			try{				
				if (progressDialog.isShowing()) {
                    progressDialog.dismiss();                
				}
				Class<?> intentClass;
				try {
					intentClass = Class.forName("com.a2.workoutapp.MainActivity");
					Intent openStartingPoint = new Intent(RegisterActivity.this, intentClass);
					openStartingPoint.putExtra("initialData", (InitialAction)o);
					startActivity(openStartingPoint);
					finish();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			}
			catch(Exception ex){
			}			
		} 	
	}
}
