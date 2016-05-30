package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

public class EngageAssistantAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	
	/**
	 * 
	 * @param player
	 */
	public EngageAssistantAction(String playerName) {
		
		this.playerName = playerName;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getQuick() == 0)
			return false;
		
		// Checks if player has at least 3 coins
		if(player.getCoins() < 3)
			return false;
					
		return true;
		
	}

	/**
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.consumeCoins(3);
		player.addAssistants(1);
		
		player.consumeQuickAction();
		
	}
	
}
