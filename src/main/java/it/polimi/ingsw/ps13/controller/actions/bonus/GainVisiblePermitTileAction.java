package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * The ability to perform this action is gained through a special type of bonus.
 * The player who gains the bonus and can perform this special action, must do so before
 * passing the turn.
 * 
 * This action puts a visible permit tile, chosen by the player from any of the region's
 * visible tiles, into the player's hand.
 *
 */
public class GainVisiblePermitTileAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String region;
	private final int tile;
	
	/**
	 * Creates a new GainVisiblePermitTileAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param region the region to which the selected permit tile belongs
	 * @param tile the number of the tile in selected region's visible tiles
	 */
	public GainVisiblePermitTileAction(String playerName, String region, int tile) {
		
		this.playerName = playerName;
		this.region = region;
		this.tile = tile;
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Selected region is valid
	 * 		- Selected permit tile number is valid
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		// Check if player has token
		if (g.getPlayer(playerName).getTokens().getTakeTile() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region))
			throw new IllegalActionException("Selected region is not valid");
		
		// Check if tile is a valid visible permit tile number
		if ( (tile > g.getBoard().getRegion(region).getPermitTileDeck().getVisibleTiles().size()-1)
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
		
		PermitTile selected = g.getBoard().getRegion(region).getPermitTileDeck().takeTile(tile);
		Player player = g.getPlayer(playerName);
		player.receivePermitTile(selected);
		
		selected.getBonus().giveTo(player);
		
		player.consumeTakeTileToken();
		
	}

}
