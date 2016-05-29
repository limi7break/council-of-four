package it.polimi.ingsw.ps13.model.council;

import java.awt.*;

/**
 *	created just for testing reasons
 */
final class PoliticsCard extends it.polimi.ingsw.ps13.model.deck.PoliticsCard {

	private final static long serialVersionUID = 0L;

	protected PoliticsCard(Color color) {
		
		super(color);
	
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