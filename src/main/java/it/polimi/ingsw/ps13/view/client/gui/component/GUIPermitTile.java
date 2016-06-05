package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.deck.PermitTile;

public class GUIPermitTile extends JPanel {

	private static final long serialVersionUID = 0L;

	public GUIPermitTile(PermitTile tile) {
	
		super(new BorderLayout());
		
		JPanel cityPane = new JPanel(new GridLayout(0, 1));
		for (String cityName : tile.getCityNames()) {
			JLabel cityLabel = new JLabel(cityName);
			cityLabel.setHorizontalAlignment(JLabel.CENTER);
			cityPane.add(cityLabel, SwingConstants.CENTER);
		}
		
		JPanel bonusPane = new JPanel(new GridLayout(1, 0));
		GUIBonusFactory.createBonus((ConcreteBonus)tile.getBonus(), bonusPane);
		
		add(cityPane, BorderLayout.CENTER);
		add(bonusPane, BorderLayout.SOUTH);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(70, 70));
		setBackground(new Color(160, 82, 45));
		setOpaque(true);
		
	}

}
