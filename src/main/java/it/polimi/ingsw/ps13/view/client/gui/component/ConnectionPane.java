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

/**
 * This panel contains every city-connecting ling on the GUI.
 * 
 * This panel is placed at the bottom layer of a layered pane, so that
 * shows through the transparent main pane which is in the top layer.
 * It is separated from everything else because cities from different
 * regions can be connected, but cities belonging to different regions
 * belong to different panels as well.
 * This is a simple way to draw lines passing through different panels.
 *
 */
public class ConnectionPane extends GUIPanel {
	
	private static final long serialVersionUID = 0L;
	private final transient List<Line> lines;
	
	/**
	 * Creates the connection pane setting the lines to draw.
	 * 
	 * @param lines the lines to draw
	 */
	protected ConnectionPane(Collection<Line> lines) {
		
		this.lines = new ArrayList<>();
		this.lines.addAll(lines);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
	}

	/**
	 * Draws the lines on the panel.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		for (Line line : lines) {
			Point first = line.getFirstPoint();
			Point second = line.getSecondPoint();
			g2.draw(new Line2D.Double(first.getX(), first.getY(), second.getX(), second.getY()));
		}
		
	}

}
