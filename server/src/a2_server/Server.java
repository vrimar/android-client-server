package a2_server;

import java.io.*;
import java.net.*;

public class Server {

	private static final int portNumber = 8000;
	
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket;
		System.out.println("*** WorkoutApp Multi-Threaded Server Started ***");
		
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Listening for a connection...");
			
			while(true) {
				Socket socketConnection = serverSocket.accept();
				Thread thread = new HandleClientThread(socketConnection);
				thread.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
