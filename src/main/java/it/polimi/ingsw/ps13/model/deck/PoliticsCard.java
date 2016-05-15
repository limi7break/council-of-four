package it.polimi.ingsw.ps13.model.deck;

import java.awt.Color;
import java.io.Serializable;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * A politics card is completely identified by its Color.
 * We decided to represent the multicolored card (jolly) with a null color value.
 * This class is immutable.
 * 
 */
public final class PoliticsCard implements Marketable, Serializable {
	
	private static final long serialVersionUID = 0L;
	private final Color color;
	
	/**
	 * Constructs the PoliticsCard using the passed Color.
	 * 
	 * @param color
	 */
	PoliticsCard(Color color) {
		
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
	
	/**
	 * 
	 * 
	 */
	public void giveTo(Player player) {
		
		// @TODO: implement this method
		// remember: when this method is called, the card has already been removed
		// from the seller's hand. Only need to put the reference in the buyer's hand.
		
	}
}