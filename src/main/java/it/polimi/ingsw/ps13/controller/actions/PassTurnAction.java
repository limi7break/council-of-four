package it.polimi.ingsw.ps13.controller.actions;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * 
 *
 */
public class PassTurnAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	
	/**
	 * 
	 * @param player
	 */
	public PassTurnAction(String playerName) {

		this.playerName = playerName;
	
	}

	/**
	 * 
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
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		g.passTurn();
		
	}
	
}
