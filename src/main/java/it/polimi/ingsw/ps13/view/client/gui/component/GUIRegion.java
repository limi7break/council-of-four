package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.region.Region;

public class GUIRegion extends GUIPanel {

	private static final long serialVersionUID = 0L;
	
	private final GUIPanel cityPane;
	private final String name;

	public GUIRegion(Region region) {
		
		super(new BorderLayout());
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		
		this.name = region.getName();
		
		add(new JLabel(region.getName(), SwingConstants.CENTER), BorderLayout.NORTH);
		
		cityPane = new GUIPanel(new GridLayout(0, 2, 50, 50));
		cityPane.setTransparent(true);
		add(cityPane, BorderLayout.CENTER);
		
		GUIPanel tilesAndBalcony = new GUIPanel(new GridLayout(2, 1));
		tilesAndBalcony.setTransparent(true);
		
		GUIPanel visibleTiles = new GUIPanel(new FlowLayout());
		visibleTiles.setTransparent(true);
		for (PermitTile t : region.getPermitTileDeck().getVisibleTiles()) {
			GUIPermitTile tile = new GUIPermitTile(t);
			visibleTiles.add(tile);
		}
		tilesAndBalcony.add(visibleTiles);
		
		GUICouncillorBalcony balcony = new GUICouncillorBalcony(region.getCouncillorBalcony());
		tilesAndBalcony.add(balcony, SwingConstants.CENTER);
		
		add(tilesAndBalcony, BorderLayout.SOUTH);
		
	}
	
	protected void addCity(GUICity city) {
		
		cityPane.add(city);
		
	}
	
	public String getName() {
		
		return name;
		
	}

}
