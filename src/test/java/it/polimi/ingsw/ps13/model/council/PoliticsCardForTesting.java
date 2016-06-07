package it.polimi.ingsw.ps13.model.council;

import java.awt.*;

/**
 *	created just for testing reasons
 */
final class PoliticsCardForTesting extends it.polimi.ingsw.ps13.model.deck.PoliticsCard {

	private final static long serialVersionUID = 0L;

	protected PoliticsCardForTesting(Color color, String colorName) {
		
		super(color, colorName);
	
	}
	

	public Color getColor() {
		
		return super.getColor();
		
	}
	

	public boolean isMultiColored() {
		
		return super.getColor() == null;
		
	}
	

	@Override
	public String toString() {
		
		if (this.isMultiColored()) {
			return "[PoliticsCard] (j, o, l, l, y)";
		} else {
			return "[PoliticsCard] "
					+ "(" + super.getColor().getRed() + ", " + super.getColor().getGreen() + ", " + super.getColor().getBlue() + ")";
		}
		
	}
}