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
	private final String colorName;
	
	/**
	 * Creates a councillor whose color is given as a parameter.
	 * 
	 */
	protected Councillor(Color color, String colorName) {
		
		this.color = color;
		this.colorName = colorName;
		
	}

	/**
	 * 
	 * @return the color of this councillor.
	 */
	public Color getColor() {
		
		return color;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getColorName() {
		
		return colorName;
		
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
