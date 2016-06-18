package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Point;

/**
 * This class represents a straight line passing through two points.
 *
 */
public class Line {

	private final Point firstPoint;
	private final Point secondPoint;
	
	/**
	 * Creates the line passing through the given points.
	 * 
	 * @param first the first point through which the line passes
	 * @param second the second point through which the line passes
	 */
	protected Line(Point first, Point second) {
		
		this.firstPoint = first;
		this.secondPoint = second;
		
	}
	
	/**
	 * Returns the first point through which the line passes.
	 * 
	 * @return the first point through which the line passes.
	 */
	protected Point getFirstPoint() {
		
		return firstPoint;
		
	}
	
	/**
	 * Returns the second point through which the line passes.
	 * 
	 * @return the second point through which the line passes.
	 */
	protected Point getSecondPoint() {
		
		return secondPoint;
		
	}
	
}
