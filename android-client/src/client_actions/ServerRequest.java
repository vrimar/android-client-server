package client_actions;

import java.io.*;
import java.net.*;
import app_data.*;

public class ServerRequest {
	private static final int portNumber = 8000;
	static ObjectOutputStream toServer;
	static ObjectInputStream fromServer;
	static Socket clientSocket;
	static Object o;
	
	public ServerRequest() {
		try {
			clientSocket = new Socket(InetAddress.getByName("10.0.2.2"), portNumber);
			toServer = new ObjectOutputStream(clientSocket.getOutputStream());
			fromServer = new ObjectInputStream(clientSocket.getInputStream());	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void CloseConnection() {
		try
		{
			toServer.close();
			fromServer.close();
			clientSocket.close();
		}
		catch(Exception e) {
			CloseConnection();
		}
	}
	
	public Object FetchInitialData(String username) {
		try {
			toServer.writeObject(new Action(username));
			toServer.flush();
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch(Exception e) {
			CloseConnection();
		}
		return o;
	}

	public Object Login(UserAction user)
	{
		try
		{	
			toServer.writeObject(user);
			toServer.flush();		
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		return o;
	}
	
	public Object WorkoutAdd(Workout w) {
		try
		{
			WorkoutAction workout = new WorkoutAction(w, "Add");
			toServer.writeObject(workout);
			toServer.flush();	
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		
		return o;
	}
	public Object WorkoutDelete(Workout w) {
		try
		{
			WorkoutAction workout = new WorkoutAction(w, "Delete");
			toServer.writeObject(workout);
			toServer.flush();	
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		
		return o;
	}
	
	public Object WorkoutList(String actionString) 
	{
		try
		{
			ExerciseAction action = new ExerciseAction(actionString);
			toServer.writeObject(action);
			toServer.flush();
			
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		return o;
	}
	
	public Object ExerciseDelete(Exercise ea) {
		try
		{
			ExerciseAction action = new ExerciseAction("Delete");
			action.setExercise(ea);
			toServer.writeObject(action);
			toServer.flush();			
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		
		return o;
	}
	
	public Object ExerciseAdd(Exercise ex) {
		try
		{
			ExerciseAction action = new ExerciseAction("Add");
			action.setExercise(ex);
			toServer.writeObject(action);
			toServer.flush();
			
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		return o;
	}
	public Object ExerciseUpdate(Exercise ex, String oldName) {
		try
		{
			ExerciseAction action = new ExerciseAction("Edit");
			action.setOldName(oldName);
			action.setExercise(ex);
			toServer.writeObject(action);
			toServer.flush();
			
			o = (Object)fromServer.readObject();
			CloseConnection();
		}
		catch (Exception e)
		{
			CloseConnection();
		}
		return o;
	}
}
