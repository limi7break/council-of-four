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
	public boolean isLegal(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		boolean legal = true;
		
		if(g.isFinished())
			legal = false;
		
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
		
		g.passTurn();
		
	}
	
}
