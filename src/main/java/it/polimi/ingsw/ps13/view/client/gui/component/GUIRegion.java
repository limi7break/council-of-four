package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.region.Region;

public class GUIRegion extends GUIPanel {

	private static final long serialVersionUID = 0L;
	
	private final GUIPanel cityPane;
	private final GUICouncillorBalcony councillorBalcony;
	private final List<GUIPermitTile> tiles;
	private final String name;

	protected GUIRegion(Region region) {
		
		super(new BorderLayout());
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		
		this.name = region.getName();
		
		add(new JLabel(region.getName(), SwingConstants.CENTER), BorderLayout.NORTH);
		
		cityPane = new GUIPanel(new GridLayout(0, 2, 40, 25));
		cityPane.setTransparent(true);
		add(cityPane, BorderLayout.CENTER);
		
		GUIPanel southPanel = new GUIPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
		southPanel.setTransparent(true);
		
		councillorBalcony = new GUICouncillorBalcony(region.getCouncillorBalcony());
		southPanel.add(councillorBalcony);
		
		tiles = new ArrayList<>();
		GUIPanel visibleTiles = new GUIPanel(new FlowLayout());
		visibleTiles.setTransparent(true);
		for (int i=0; i<region.getPermitTileDeck().getVisibleTiles().size(); i++) {
			PermitTile t = region.getPermitTileDeck().getVisibleTiles().get(i);
			GUIPermitTile tile = new GUIPermitTile(t, i);
			visibleTiles.add(tile);
			tiles.add(tile);
		}
		southPanel.add(visibleTiles);
		
		GUIPanel bonusPanel = new GUIPanel(new FlowLayout());
		GUIPanel bonus = new GUIPanel(new GridLayout(1, 0));
		GUIBonusFactory.createBonus(region.getBonus(), bonus);
		bonus.setPreferredSize(new Dimension(60, 30));
		bonus.setBorder(BorderFactory.createLineBorder(Color.black));
		if (region.isBonusAvailable()) {
			bonus.setBackground(new Color(139, 69, 19, 96));
		} else {
			bonus.setTransparent(true);
		}
		bonusPanel.add(bonus);
		southPanel.add(bonusPanel);
		
		add(southPanel, BorderLayout.SOUTH);
		
	}
	
	/**
	 * 
	 * @param city
	 */
	protected void addCity(GUICity city) {
		
		cityPane.add(city);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public GUICouncillorBalcony getCouncillorBalcony() {
		
		return councillorBalcony;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<GUIPermitTile> getVisibleTiles() {
		
		return tiles;
		
	}

}
