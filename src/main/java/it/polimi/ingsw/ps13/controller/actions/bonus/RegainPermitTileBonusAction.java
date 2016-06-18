package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * The ability to perform this action is gained through a special type of bonus.
 * The player who gains the bonus and can perform this special action, must do so before
 * passing the turn.
 * 
 * This action gives the player the bonus of the permit tile that has been selected from the hand.
 *
 */
public class RegainPermitTileBonusAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final int tile;
	
	/**
	 * Creates a new RegainPermitTileBonusAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param tile the number of the tile in player's hand
	 */
	public RegainPermitTileBonusAction(String playerName, int tile) {
		
		this.playerName = playerName;
		this.tile = tile;
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Selected permit tile number is valid
	 */
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

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.getPermitTiles().get(tile).getBonus().giveTo(player);
		player.consumeTileBonusToken();
		
	}

}
