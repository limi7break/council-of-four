package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class ChangePermitTilesAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final Player player;
	private final Region region;

	public ChangePermitTilesAction(Player player, Region region) {
		
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
		
		//checks if conditions are satisfied
		int playerAssistants = player.getAssistants();
		
		if(playerAssistants < 1)
			legal = false;
		
		return legal;
		
	}

	@Override
	public void apply(Game g) {
		
		player.consumeAssistants(1);
		
		PermitTileDeck selected = region.getPermitTileDeck();
		selected.changeTiles();
		
		
	}

	
	
}
