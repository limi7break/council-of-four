package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.board.NobilityTrack;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;

public class GUINobilityTrack extends GUIPanel {

	private static final long serialVersionUID = 0L;

	/**
	 * @TODO: fix nobility track width with certain bonuses (the special ones)
	 * 
	 * @param nobilityTrack
	 */
	protected GUINobilityTrack(NobilityTrack nobilityTrack) {
		
		super(new GridLayout(2, 0));
		setTransparent(true);
		setBorder(BorderFactory.createTitledBorder("Nobility Track"));
		
		// Add bonuses
		for (Bonus b : nobilityTrack.getBonusMap().values()) {
			GUIPanel bonusPane = new GUIPanel(new GridLayout(0, 2));
			bonusPane.setTransparent(true);
			bonusPane.setBorder(BorderFactory.createLineBorder(Color.black));
			GUIBonusFactory.createBonus((ConcreteBonus)b, bonusPane);
			add(bonusPane);
		}
		
		// Add cell numbers
		for (int i=0; i<nobilityTrack.getBonusMap().size(); i++) {
			JLabel bonusNumber = new JLabel(String.valueOf(i));
			bonusNumber.setHorizontalAlignment(JLabel.CENTER);
			bonusNumber.setBorder(BorderFactory.createLineBorder(Color.black));
			add(bonusNumber);
		}
		
	}
	
}
