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
public class PoliticsCard implements Marketable, Serializable {
	
	private static final long serialVersionUID = 0L;
	public static final Color jollyColor = new Color(6, 6, 6);
	private final Color color;
	private final String colorName;
	
	/**
	 * Constructs the PoliticsCard using the passed Color.
	 * 
	 * @param color
	 */
	protected PoliticsCard(Color color, String colorName) {
		
		this.color = color;
		this.colorName = colorName;
	
	}
	
	/**
	 * @return the Color of the card (or null if the PoliticsCard is multicolored)
	 */
	public Color getColor() {
		
		return this.color;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getColorName() {
		
		return colorName;
		
	}
	
	/**
	 * @return true if the card is a multicolored one
	 */
	public boolean isMultiColored() {
		
		return color.equals(jollyColor);
		
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	public void giveTo(Player player) {
		
		player.receivePoliticsCard(this);
		// remember: when this method is called, the card has already been removed
		// from the seller's hand. Only need to put the reference in the buyer's hand.
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {

		return colorName.toUpperCase();
	}
}