package it.polimi.ingsw.ps13.model.board;

import java.awt.Color;
import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * An Emporium is identified with its owner, which in turn defines the emporium's color.
 * This class is immutable.
 * 
 */
public final class Emporium implements Serializable{

	private static final long serialVersionUID = 0L;
	private final Color color;
	private final Player owner;
	
	/**
	 * Creates an emporium which belongs to the Player given as owner.
	 * The color of the emporium is taken directly from the owner.
	 * 
	 */
	Emporium(Player owner){
		
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