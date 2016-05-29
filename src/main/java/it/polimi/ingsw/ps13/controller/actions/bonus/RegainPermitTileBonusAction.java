package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;

public class RegainPermitTileBonusAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final PermitTile tile;
	
	public RegainPermitTileBonusAction(Player player, PermitTile tile) {
		
		this.player = player;
		this.tile = tile;
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		if(player.getPermitTiles().isEmpty())
			legal = false;
		
		if(!player.getPermitTiles().contains(tile))
			legal = false;
		
		return legal;
		
	}

	@Override
	public void apply(Game g) {
		
		Bonus bonus = tile.getBonus();
		bonus.giveTo(player);
		
	}

}
