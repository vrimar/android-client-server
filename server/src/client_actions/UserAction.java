package client_actions;

import app_data.User;

public class UserAction extends Action {

	private static final long serialVersionUID = 3822002114941690837L;
	private User user;
	
	public UserAction(User u, String action) {
		super(action);
		user = u;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
