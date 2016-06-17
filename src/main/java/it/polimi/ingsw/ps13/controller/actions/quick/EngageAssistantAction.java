package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
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
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getQuick() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Checks if player has at least 3 coins
		if(player.getCoins() < 3)
			throw new IllegalActionException("Not enough coins, 3 required");
					
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
