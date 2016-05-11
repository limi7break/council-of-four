package it.polimi.ingsw.ps13.model.deck;

import java.awt.Color;
import java.io.Serializable;

/**
 * A PoliticsCard is completely represented by its Color.
 * 
 * We decided to represent the multicolored card (jolly) with a NULL color value.
 * 
 */
public class PoliticsCard implements Serializable {
	
	private static final long serialVersionUID = 0L;
	private final Color color;
	
	/**
	 * Constructs the PoliticsCard using the passed Color.
	 * 
	 * @param color
	 */
	public PoliticsCard(Color color) {
		
		this.color = color;
	
	}
	
	/**
	 * @return the Color of the card (or null if the PoliticsCard is multicolored)
	 */
	public Color getColor() {
		
		return this.color;
		
	}
	
	/**
	 * @return true if the card is a multicolored one
	 */
	public boolean isMultiColored() {
		
		return (color == null) ? true : false;
		
	}
}