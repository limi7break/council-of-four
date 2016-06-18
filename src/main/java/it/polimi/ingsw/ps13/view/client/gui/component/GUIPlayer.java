package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This is the GUI representation of a player, containing basic player stats.
 *
 */
public class GUIPlayer extends GUIPanel {

	private static final long serialVersionUID = 0L;

	/**
	 * Creates the GUI representation of the player passed as parameter.
	 * 
	 * @param player the player from which the GUI representation is created
	 */
	protected GUIPlayer(Player player) {
		
		super(new BorderLayout());
		setTransparent(true);
		
		JLabel playerName = new JLabel(player.getName());
		playerName.setForeground(player.getColor());
		add(playerName, BorderLayout.NORTH);
		
		GUIPanel panel = new GUIPanel(new GridLayout(0, 4));
		
		// Add victory points
		panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/victorypoints.png"))));
		panel.add(new JLabel(String.valueOf(player.getVictoryPoints())));
		
		// Add coins
		panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/coins.png"))));
		panel.add(new JLabel(String.valueOf(player.getCoins())));
		
		// Add assistants
		panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/assistants.png"))));
		panel.add(new JLabel(String.valueOf(player.getAssistants())));
		
		// Add number of politics cards
		panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/politicscards.png"))));
		panel.add(new JLabel(String.valueOf(player.getPoliticsCards().size())));
		
		// Add number of emporiums
		panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/emporium.png"))));
		panel.add(new JLabel(String.valueOf(player.getNumberOfEmporiums())));
		
		// Add nobility position
		panel.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/nobilitypoints.png"))));
		panel.add(new JLabel(String.valueOf(player.getNobilityPosition())));
		
		add(panel);
		
	}
	
}
