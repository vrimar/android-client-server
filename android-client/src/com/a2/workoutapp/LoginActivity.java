package com.a2.workoutapp;

import client_actions.InitialAction;
import client_actions.ServerRequest;
import client_actions.UserAction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app_data.User;

public class LoginActivity extends Activity  {

	EditText username, password;
	Button login, register;
	ProgressDialog progressDialog;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_login);       
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    	login = (Button) findViewById(R.id.login);
    	register = (Button)findViewById(R.id.button_register);
    	
    	progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        
    	login.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				Login loginAsyncTask = new Login();
				loginAsyncTask.execute();
			}
    		
    	});
    	register.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) 
			{
				Class<?> intentClass;
				try {
					intentClass = Class.forName("com.a2.workoutapp.RegisterActivity");
					Intent openStartingPoint = new Intent(LoginActivity.this, intentClass);
					startActivity(openStartingPoint);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
    		
    	});
    }
	
	private class InitialData extends AsyncTask<String, Void, Object> {
		@Override
		protected Object doInBackground(String... params) {
			ServerRequest server = new ServerRequest();
			Object o = server.FetchInitialData(params[0]);
			return o;
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Fetching initial data....");
		}   
 
		@Override
		protected void onPostExecute(Object o) {
			Class<?> intentClass;
			try {
				intentClass = Class.forName("com.a2.workoutapp.MainActivity");
				Intent openStartingPoint = new Intent(LoginActivity.this, intentClass);
				openStartingPoint.putExtra("initialData", (InitialAction)o);
                progressDialog.dismiss();
				startActivity(openStartingPoint);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class Login extends AsyncTask<Void, Void, Object>{

		@Override
		protected Object doInBackground(Void...voids) {
			User user = new User(username.getText().toString(), password.getText().toString());
			UserAction ua = new UserAction(user, "Login");
			ServerRequest server = new ServerRequest();
			Object o = server.Login(ua);
			return o;
		}
		
		@Override
	      protected void onPreExecute() {
	        progressDialog.setMessage("Authenicating...");
        	progressDialog.show();
	      }

		@Override
		protected void onPostExecute(Object o) {
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
			try{
				
				String loginStatus = (String)o;
				if (loginStatus.equals("success"))
				{
					InitialData init = new InitialData();
					init.execute(username.getText().toString());
				}
				else 
				{
					progressDialog.hide();
			        builder.setTitle("An error has occurred.")
			        .setMessage("Invalid username/password. Please try again.")
			        .setCancelable(false)
			        .setNegativeButton("Okay",new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			            }
			        });
			        AlertDialog alert = builder.create();
			        alert.show();
			    }
			}
			catch(Exception ex){
				progressDialog.dismiss();
				builder.setTitle("An error has occurred.")
		        .setMessage("A network error has occured.")
		        .setCancelable(false)
		        .setNegativeButton("Okay",new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		            }
		        });
		        AlertDialog alert = builder.create();
		        alert.show();
			}			
		} 	
    }
}
