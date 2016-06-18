package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This action is performed when the player wants to spend 3 assistants to gain 1 extra main action.
 * 
 * This is a quick action.
 *
 */
public class GainMainActionAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	
	/**
	 * Creates a new GainMainActionAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 */
	public GainMainActionAction(String playerName) {
		
		this.playerName = playerName;
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Player has enough assistants
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getQuick() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if player has at least 3 assistants
		if(player.getAssistants() < 3)
			throw new IllegalActionException("Not enough assistants, 3 required");
		
		return true;
		
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.consumeAssistants(3);
		player.addMainActions(1);
		
		player.consumeQuickAction();
		
	}
	
}
