package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Point;

public class Line {

	private final Point firstPoint;
	private final Point secondPoint;
	
	public Line(Point first, Point second) {
		
		this.firstPoint = first;
		this.secondPoint = second;
		
	}
	
	public Point getFirstPoint() {
		
		return firstPoint;
		
	}
	
	public Point getSecondPoint() {
		
		return secondPoint;
		
	}
	
}
