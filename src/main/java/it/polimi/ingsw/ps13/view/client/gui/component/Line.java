package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Point;

public class Line {

	private final Point firstPoint;
	private final Point secondPoint;
	
	protected Line(Point first, Point second) {
		
		this.firstPoint = first;
		this.secondPoint = second;
		
	}
	
	protected Point getFirstPoint() {
		
		return firstPoint;
		
	}
	
	protected Point getSecondPoint() {
		
		return secondPoint;
		
	}
	
}
