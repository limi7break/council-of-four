package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.region.Region;

public class GUIRegion extends JPanel {

	private static final long serialVersionUID = 0L;
	
	private final JPanel cityPane;

	public GUIRegion(Region region) {
		
		super(new BorderLayout());
		add(new JLabel(region.getName(), SwingConstants.CENTER), BorderLayout.NORTH);
		
		cityPane = new JPanel(new GridLayout(0, 2, 50, 50));
		add(cityPane, BorderLayout.CENTER);
		
		JPanel tilesAndBalcony = new JPanel(new GridLayout(2, 1));
		
		JPanel visibleTiles = new JPanel(new FlowLayout());
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

}
