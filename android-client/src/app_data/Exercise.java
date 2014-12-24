package app_data;

import java.io.Serializable;

public class Exercise implements Serializable {
	private static final long serialVersionUID = 7464021012520849535L;
	
	private String name;
	private String description;
	private String muscle;
	private int isCustom;
	
	public Exercise(String name, String description, String muscle, int isCustom)
	{
		this.name = name;
		this.description = description;
		this.muscle = muscle;
		this.isCustom = isCustom;
	}
	
	public String getName(){return name;}
	public String getDescription(){return description;}
	public String getMuscle(){return muscle;}
	public int isCustom(){return isCustom;}
}
