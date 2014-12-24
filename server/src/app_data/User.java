package app_data;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 8661109038099838411L;
	String username, password, firstName, lastName;
	int weight;
	
	public User() { }
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public User(String u, String p, String f, String l, int w){
		username = u;
		password = p;
		firstName = f;
		lastName = l;
		weight = w;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getWeight() {
		return weight;
	}
}
