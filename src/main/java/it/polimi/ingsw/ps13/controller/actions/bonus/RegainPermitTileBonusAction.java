package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

public class RegainPermitTileBonusAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final int tile;
	
	public RegainPermitTileBonusAction(String playerName, int tile) {
		
		this.playerName = playerName;
		this.tile = tile;
		
	}
	
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (g.getPlayer(playerName).getTokens().getTileBonus() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if tile is a valid permit tile number
		if ( (tile > player.getPermitTiles().size()-1)
			|| (tile < 0) )
			throw new IllegalActionException("Selected permit tile is not valid");
		
		return true;
		
	}

	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.getPermitTiles().get(tile).getBonus().giveTo(player);
		player.consumeTileBonusToken();
		
	}

}
