package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.io.Serializable;

/**
 * A councillor is completely identified by its color.
 */
public class Councillor implements Serializable {

	private static final long serialVersionUID = 0L;
	private final Color color;
	
	/**
	 * Creates a councillor whose color is given as a parameter.
	 */
	public Councillor(Color color){
		
		this.color = color;
		
	}

	/**
	 * 
	 * @return the color of this councillor.
	 */
	public Color getColor(){
		
		return color;
		
	}
	
}
