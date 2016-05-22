package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;

/**
 * An Emporium is identified with its color.
 * An Emporium and its owned have the same color.
 * This class is immutable.
 * 
 */
public final class Emporium implements Serializable{

	private static final long serialVersionUID = 0L;
	private final Color color;
	
	/**
	 * Creates an emporium and assigns it the passed color.
	 * 
	 */
	protected Emporium(Color color){
		
		this.color = color;
		
	}
	
	/**
	 * 
	 * @return the color of this emporium, which is also the color of its owner.
	 */
	public Color getColor(){
		
		return color;
		
	}
	
}