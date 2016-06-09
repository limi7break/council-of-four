package it.polimi.ingsw.ps13.view.client.gui.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.bonus.MainActionsBonus;
import it.polimi.ingsw.ps13.model.bonus.NobilityPointsBonus;
import it.polimi.ingsw.ps13.model.bonus.PoliticsCardsBonus;
import it.polimi.ingsw.ps13.model.bonus.RegainPermitTileBonus;
import it.polimi.ingsw.ps13.model.bonus.RegainRewardTokenBonus;
import it.polimi.ingsw.ps13.model.bonus.VisiblePermitTileBonus;
import it.polimi.ingsw.ps13.model.resource.Assistants;
import it.polimi.ingsw.ps13.model.resource.Coins;
import it.polimi.ingsw.ps13.model.resource.VictoryPoints;

public final class GUIBonusFactory {

	private GUIBonusFactory() { }
	
	/**
	 * Populates the passed JPanel with the correct images and values, and returns it.
	 * 
	 * @param cb
	 * @param panel
	 * @return
	 */
	protected static void createBonus(ConcreteBonus cb, JPanel panel) {
		
		for (Bonus b : cb.getContents()) {
			
			if (b instanceof Coins) {
				panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/coins.png"))));
				panel.add(new JLabel(String.valueOf(((Coins) b).getAmount())));
			} else if (b instanceof Assistants) {
				panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/assistants.png"))));
				panel.add(new JLabel(String.valueOf(((Assistants) b).getAmount())));
			} else if (b instanceof VictoryPoints) {
				panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/victorypoints.png"))));
				panel.add(new JLabel(String.valueOf(((VictoryPoints) b).getAmount())));
			} else if (b instanceof PoliticsCardsBonus) {
				panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/politicscards.png"))));
				panel.add(new JLabel(String.valueOf(((PoliticsCardsBonus) b).getAmount())));
			} else if (b instanceof MainActionsBonus) {
				panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/mainactions.png"))));
				panel.add(new JLabel(String.valueOf(((MainActionsBonus) b).getAmount())));
			} else if (b instanceof NobilityPointsBonus) {
				panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/nobilitypoints.png"))));
				panel.add(new JLabel(String.valueOf(((NobilityPointsBonus) b).getAmount())));
			} else if (b instanceof RegainRewardTokenBonus) {
				panel.add(new JLabel("rt"));
				//panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/rewardtokenagain.png"))));
			} else if (b instanceof RegainPermitTileBonus) {
				panel.add(new JLabel("ptb"));
				//panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/tilebonusagain.png"))));
			} else if (b instanceof VisiblePermitTileBonus) {
				panel.add(new JLabel("vt"));
				//panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/takevisibletile.png"))));
			}
			
		}
		
	}

}
