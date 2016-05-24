package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;

public class ChangePermitTilesAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final int player;
	private final String region;

	public ChangePermitTilesAction(int player, String region) {
		
		this.player = player;
		this.region = region;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		//checks if it's the player's turn
		if(g.getCurrentPlayerID() != player)
			legal = false;
		
		//checks if player can do a quick action
		if(!g.getPlayers().get(player).isQuickActionAvailable())
			legal = false; 
		
		//checks if conditions are satisfied
		int playerAssistants = g.getPlayers().get(player).getAssistants();
		
		if(playerAssistants < 1)
			legal = false;
		
		return legal;
		
	}

	@Override
	public void apply(Game g) {
		
		g.getPlayers().get(player).consumeAssistants(1);
		
		PermitTileDeck selected = g.getBoard().getRegion(region).getPermitTileDeck();
		selected.changeTiles();
		
		
	}

	
	
}
