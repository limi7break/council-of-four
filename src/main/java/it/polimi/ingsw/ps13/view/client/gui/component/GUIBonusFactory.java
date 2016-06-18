package it.polimi.ingsw.ps13.view.client.gui.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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

/**
 * This class loads the bonus images, converts a bonus into its GUI representation and adds it to the desired panel.
 *
 */
public final class GUIBonusFactory {

	private GUIBonusFactory() { }
	
	/**
	 * Populates the passed JPanel with the correct images and values, and returns it.
	 * 
	 * @param cb
	 * @param panel
	 * @return
	 */
	protected static void createBonus(Bonus bonus, JPanel panel) {
		
		if (bonus instanceof ConcreteBonus) {
			ConcreteBonus cb = (ConcreteBonus) bonus;
			
			for (Bonus b : cb.getContents()) {
				addToPanel(b, panel);
			}
		}
		
		else {
			addToPanel(bonus, panel);
		}
		
	}
	
	private static void addToPanel(Bonus b, JPanel panel) {
		
		if (b instanceof Coins) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/coins.png")), SwingConstants.CENTER));
			panel.add(new JLabel(String.valueOf(((Coins) b).getAmount()), JLabel.CENTER));
		} else if (b instanceof Assistants) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/assistants.png")), SwingConstants.CENTER));
			panel.add(new JLabel(String.valueOf(((Assistants) b).getAmount()), JLabel.CENTER));
		} else if (b instanceof VictoryPoints) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/victorypoints.png")), SwingConstants.CENTER));
			panel.add(new JLabel(String.valueOf(((VictoryPoints) b).getAmount()), JLabel.CENTER));
		} else if (b instanceof PoliticsCardsBonus) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/politicscards.png")), SwingConstants.CENTER));
			panel.add(new JLabel(String.valueOf(((PoliticsCardsBonus) b).getAmount()), JLabel.CENTER));
		} else if (b instanceof MainActionsBonus) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/mainactions.png")), SwingConstants.CENTER));
			panel.add(new JLabel(String.valueOf(((MainActionsBonus) b).getAmount()), JLabel.CENTER));
		} else if (b instanceof NobilityPointsBonus) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/nobilitypoints.png")), SwingConstants.CENTER));
			panel.add(new JLabel(String.valueOf(((NobilityPointsBonus) b).getAmount()), JLabel.CENTER));
		} else if (b instanceof RegainRewardTokenBonus) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/rewardtokenagain.png")), SwingConstants.CENTER));
		} else if (b instanceof RegainPermitTileBonus) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/tilebonusagain.png")), SwingConstants.CENTER));
		} else if (b instanceof VisiblePermitTileBonus) {
			panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/takevisibletile.png")), SwingConstants.CENTER));
		}
		
	}

}
