package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;

public class GainVisiblePermitTileAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final PermitTile tile;
	
	public GainVisiblePermitTileAction(Player player, PermitTile tile) {
		
		this.player = player;
		this.tile = tile;
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		
		return true;	// Once checked the player's turn is correct and token is available, 
						// (GameController's task) this action is legal.
		
	}

	@Override
	public void apply(Game g) {
		
		player.receivePermitTile(tile);
		
		tile.getBonus().giveTo(player);
		
	}

}
