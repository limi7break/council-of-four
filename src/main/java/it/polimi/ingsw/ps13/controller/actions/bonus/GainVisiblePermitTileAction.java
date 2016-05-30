package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;

public class GainVisiblePermitTileAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String region;
	private final int tile;
	
	public GainVisiblePermitTileAction(String playerName, String region, int tile) {
		
		this.playerName = playerName;
		this.region = region;
		this.tile = tile;
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		
		// Check if player has token
		if (g.getPlayer(playerName).getTokens().getTakeTile() == 0)
			return false;
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region))
			return false;
		
		// Check if tile is a valid visible permit tile number
		if ( (tile > g.getBoard().getRegion(region).getPermitTileDeck().getVisibleTiles().size()-1)
				|| (tile < 0) )
			return false;
		
		return true;
		
	}

	@Override
	public void apply(Game g) {
		
		PermitTile selected = g.getBoard().getRegion(region).getPermitTileDeck().takeTile(tile);
		Player player = g.getPlayer(playerName);
		player.receivePermitTile(selected);
		
		selected.getBonus().giveTo(player);
		
		player.consumeTakeTileToken();
		
	}

}
