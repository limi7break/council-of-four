package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps13.model.deck.PermitTile;

/**
 * This is the GUI representation of a permit tile.
 *
 */
public class GUIPermitTile extends GUIPanel {

	private static final long serialVersionUID = 0L;
	private final int number;

	/**
	 * Creates the GUI representation of the permit tile passed as parameter.
	 * 
	 * @param tile the permit tile from which the GUI representation is created
	 * @param number the number of the permit tile
	 */
	protected GUIPermitTile(PermitTile tile, int number) {
	
		super(new BorderLayout());
		this.number = number;
		
		GUIPanel cityPane = new GUIPanel(new GridLayout(0, 1));
		cityPane.setTransparent(true);
		for (String cityName : tile.getCityNames()) {
			JLabel cityLabel = new JLabel(cityName);
			cityLabel.setHorizontalAlignment(JLabel.CENTER);
			cityPane.add(cityLabel, SwingConstants.CENTER);
		}
		
		GUIPanel bonusPane = new GUIPanel(new GridLayout(1, 0));
		bonusPane.setTransparent(true);
		GUIBonusFactory.createBonus(tile.getBonus(), bonusPane);
		
		add(cityPane, BorderLayout.CENTER);
		add(bonusPane, BorderLayout.SOUTH);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(70, 70));
		
		if (tile.isUsable()) {
			setBackground(new Color(139, 69, 19, 96));
		} else {
			setTransparent(true);
		}
		
	}
	
	/**
	 * Returns the number of the permit tile.
	 * 
	 * @return the number of the permit tile
	 */
	public int getNumber() {
		
		return number;
		
	}

}
