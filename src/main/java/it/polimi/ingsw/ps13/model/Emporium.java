package it.polimi.ingsw.ps13.model;

import java.io.Serializable;
import java.awt.Color;

/**
 * An Emporium is identified with its owner and color.
 * 
 */
public class Emporium implements Serializable{

	private static final long serialVersionUID = 0L;
	private final Color color;
	private final Player owner;
	
	/**
	 * Create an object Emporium which belongs to the Player given as owner.
	 * The color of the emporium is taken directly from the owner's.
	 * 
	 */
	public Emporium(Player owner){
		
		this.color = owner.getColor();
		this.owner = owner;
		
	}
	
	/**
	 * 
	 * @return the color of this emporium, which is also the color of its owner.
	 */
	public Color getColor(){
		
		return color;
		
	}

	/**
	 * 
	 * @return the owner of this emporium.
	 */
	public Player getOwner(){
		
		return owner;
		
	}
	
}