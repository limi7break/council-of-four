package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.deck.PermitTile;

public class GUIPermitTile extends GUIPanel {

	private static final long serialVersionUID = 0L;

	public GUIPermitTile(PermitTile tile) {
	
		super(new BorderLayout());
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		
		GUIPanel cityPane = new GUIPanel(new GridLayout(0, 1));
		cityPane.setTransparent(true);
		for (String cityName : tile.getCityNames()) {
			JLabel cityLabel = new JLabel(cityName);
			cityLabel.setHorizontalAlignment(JLabel.CENTER);
			cityPane.add(cityLabel, SwingConstants.CENTER);
		}
		
		GUIPanel bonusPane = new GUIPanel(new GridLayout(1, 0));
		bonusPane.setTransparent(true);
		GUIBonusFactory.createBonus((ConcreteBonus)tile.getBonus(), bonusPane);
		
		add(cityPane, BorderLayout.CENTER);
		add(bonusPane, BorderLayout.SOUTH);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(70, 70));
		setBackground(new Color(160, 82, 45));
		
	}

}
