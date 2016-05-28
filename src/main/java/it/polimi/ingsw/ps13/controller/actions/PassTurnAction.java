package it.polimi.ingsw.ps13.controller.actions;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * 
 *
 */
public class PassTurnAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final Player player;
	
	/**
	 * 
	 * @param player
	 */
	public PassTurnAction(Player player) {

		this.player = player;
	
	}

	/**
	 * 
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		if(player.getTokens().getMain() != 0)
			legal = false;
		
		if(player.getTokens().getRewardToken() != 0)
			legal = false;
		
		if(player.getTokens().getTakeTile() != 0)
			legal = false;
		
		if(player.getTokens().getTileBonus() != 0)
			legal = false;
		
		return legal;
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		//Resets all of this player's tokens.
		player.getTokens().setZeros();
		
		//Updates currentPlayerID
		if(g.getCurrentPlayerID() == (g.getNumberOfPlayers() - 1))
			g.setCurrentPlayerID(0);
		else
			g.setCurrentPlayerID(g.getCurrentPlayerID() + 1);
		
		//Sets the standard tokens for the next player(1 main action, 1 quick action)
		Player next = g.getCurrentPlayer();
		next.getTokens().setInitial();
		
	}
	
}
