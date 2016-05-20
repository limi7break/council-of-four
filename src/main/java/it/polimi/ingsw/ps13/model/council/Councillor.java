package it.polimi.ingsw.ps13.model.council;

import java.awt.Color;
import java.io.Serializable;

/**
 * A councillor is completely identified by its color.
 * This class is immutable.
 * 
 */
public final class Councillor implements Serializable {

	private static final long serialVersionUID = 0L;
	private final Color color;
	
	/**
	 * Creates a councillor whose color is given as a parameter.
	 * 
	 */
	protected Councillor(Color color) {
		
		if (color != null) {
			this.color = color;
		} else {
			throw new IllegalStateException("The color of a councillor can not be null");
		}
		
	}

	/**
	 * 
	 * @return the color of this councillor.
	 */
	public Color getColor() {
		
		return color;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "[Councillor] "
				+ "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
		
	}
	
}
