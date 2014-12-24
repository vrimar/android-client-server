package a2_server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

import app_data.Exercise;
import app_data.User;
import app_data.Workout;
import client_actions.*;
import data_access.*;

public class HandleClientThread extends Thread {
	private Socket socket;
	private Connection dataConnection;
	private int count;
	ObjectOutputStream dosToClient = null;
	ObjectInputStream disFromClient = null;
	
	public HandleClientThread(Socket socket)
	{
		count = 1;
		this.socket = socket;
	}
	
	public void run() {
		try {	
			dosToClient = new ObjectOutputStream(socket.getOutputStream());			
			disFromClient = new ObjectInputStream(socket.getInputStream());			
				
			while(true)
			{
				System.out.println("Client thread #" + count + " connected & running...");	
				count++;
				System.out.println("Client thread #" + count + " database connection opened");	
				dataConnection = DataAccess.establishDBConnection();
				Action clientRequest = (Action)disFromClient.readObject();;
				String a = clientRequest.getAction();
				
				if (!a.equals("Add") && !a.equals("Edit") 
						&& !a.equals("Delete") && !a.equals("Login") && !a.equals("Register")) {
					ArrayList<Exercise> e;
					ArrayList<Workout>	w;
					User u;
					e = QueryExercise.SelectExercises(dataConnection);
					u = QueryUser.SelectUser(dataConnection, clientRequest.getAction());
					w = QueryWorkout.SelectWorkouts(dataConnection);
					
					InitialAction ia = new InitialAction("", w, e, u);
					dosToClient.writeObject(ia);
					dosToClient.flush();
					System.out.println("Querying data for initial app start...");
				}
				else if (clientRequest instanceof UserAction) {
					UserAction action = (UserAction)clientRequest;
					User action_user = action.getUser();
					String loginStatus;
					
					if (action.getAction().equals("Login")){
						boolean isAuthenicated = QueryUser
								.authenicateUser(dataConnection, action_user.getUsername(), action_user.getPassword());
												
						if (isAuthenicated) {
							loginStatus = "success";
							System.out.println(action_user.getUsername() + " has logged in.");
						}
						else loginStatus = "failed";
						
						dosToClient.writeObject(loginStatus);
						dosToClient.flush();	
					}
					else if(action.getAction().equals("Register")) {
						ArrayList<Exercise> e;
						ArrayList<Workout>	w;
						QueryUser.RegisterUser(dataConnection, action.getUser());
						e = QueryExercise.SelectExercises(dataConnection);
						w = QueryWorkout.SelectWorkouts(dataConnection);
						
						InitialAction ia = new InitialAction("", w, e, action.getUser());
						dosToClient.writeObject(ia);
						dosToClient.flush();
						System.out.println("Querying data for initial app start...");
					}
				}
				else if(clientRequest instanceof ExerciseAction) {
					ExerciseAction action = (ExerciseAction)clientRequest;
					String e_action = action.getAction();
					Exercise exercise = action.getExercise();
					if(e_action.equals("Add")) {
						QueryExercise.AddExercise(dataConnection, exercise);
						System.out.println("New exercise '" + exercise.getName() + "'  added to database");
					}
					else if (e_action.equals("Edit")) {	
						QueryExercise.UpdateExercise(dataConnection, exercise, action.getOldName());
						System.out.println("'" + action.getOldName() + "' edited in database");
					}
					else if(e_action.equals("Delete")) {
						QueryExercise.DeleteExercises(dataConnection, exercise.getName());
						System.out.println("Exercise '" + exercise.getName() + "' deleted from database.");
					}
					ArrayList<Exercise> e = new ArrayList<Exercise>();
					e = QueryExercise.SelectExercises(dataConnection);
					
					dosToClient.writeObject(e);
					dosToClient.flush();
					System.out.println("Exercise list returned to client.");					
				}
				else if (clientRequest instanceof WorkoutAction)
				{
					WorkoutAction action = (WorkoutAction)clientRequest;
					String e_action = action.getAction();
					
					if(e_action.equals("Add")) {
						QueryWorkout.AddWorkout(dataConnection, action.getWorkout());
						System.out.println("New workout '" + action.getWorkout().getName() + "'  added to database");
					}
					else if(e_action.equals("Delete")) {
						QueryWorkout.DeleteWorkout(dataConnection, action.getWorkout());
						System.out.println("Workout '" + action.getWorkout().getName() + "'  deleted from database");
					}
					
					ArrayList<Workout> w = new ArrayList<Workout>();
					w = QueryWorkout.SelectWorkouts(dataConnection);
					
					dosToClient.writeObject(w);
					dosToClient.flush();
					System.out.println("Workout list returned to client.");
					
				}
				System.out.println("Client thread #" + count + " database connection closed.");
				System.out.println("Client thread #" + count + " closed.");
				dataConnection.close();
			}
		}
		catch(Exception e) { }
	}
}
