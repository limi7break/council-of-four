package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.border.EmptyBorder;

public class ConnectionPane extends GUIPanel {
	
	private static final long serialVersionUID = 0L;
	private final List<Line> lines;
	
	public ConnectionPane(Collection<Line> lines) {
		
		this.lines = new ArrayList<>();
		this.lines.addAll(lines);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
	}

	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		for (Line line : lines) {
			Point first = line.getFirstPoint();
			Point second = line.getSecondPoint();
			g2.draw(new Line2D.Double(first.getX(), first.getY(), second.getX(), second.getY()));
		}
		
	}

}
