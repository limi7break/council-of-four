package it.polimi.ingsw.ps13.controller.actions;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This action is performed when a player wants to end his turn and pass it to the next player.
 * Most of the turn logic is handled by the model in the passTurn method.
 *
 */
public class PassTurnAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	
	/**
	 * Creates a new PassTurnAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 */
	public PassTurnAction(String playerName) {

		this.playerName = playerName;
	
	}

	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Game isn't finished yet
	 * 		- Player has zero main actions available
	 * 		- Player has zero bonus actions available
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		boolean legal = true;
		
		if(g.isFinished())
			throw new IllegalActionException("Game is already finished");
		
		else if(player.getTokens().getMain() != 0)
			throw new IllegalActionException("You can\'t pass with main actions available");
		
		else if(player.getTokens().getRewardToken() != 0)
			throw new IllegalActionException("You can\'t pass with bonus actions available");
		
		else if(player.getTokens().getTakeTile() != 0)
			throw new IllegalActionException("You can\'t pass with bonus actions available");
		
		else if(player.getTokens().getTileBonus() != 0)
			throw new IllegalActionException("You can\'t pass with bonus actions available");
		
		return legal;
		
	}
	
	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		g.passTurn();
		
	}
	
}
