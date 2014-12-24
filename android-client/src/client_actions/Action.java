package client_actions;

import java.io.Serializable;

/**
 * @summary An action requested by the client
 */
public class Action implements Serializable{
	private static final long serialVersionUID = 3627392075971177604L;
	private String action;
	
	public Action(String s)
	{
		action = s;
	}
	
	public String getAction()
	{
		return action;
	}
}
