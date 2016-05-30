package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import it.polimi.ingsw.ps13.model.player.Player;

public class ChangePermitTilesAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	private final String region;

	public ChangePermitTilesAction(String playerName, String region) {
		
		this.playerName = playerName;
		this.region = region;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getQuick() == 0)
			return false;
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region))
			return false;
		
		// Check if player has at least one assistant
		if (player.getAssistants() < 1)
			return false;
		
		return true;
		
	}

	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.consumeAssistants(1);
		
		PermitTileDeck selected = g.getBoard().getRegion(region).getPermitTileDeck();
		selected.changeTiles();
		
		player.consumeQuickAction();
		
	}

	
	
}
