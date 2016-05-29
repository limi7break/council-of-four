package it.polimi.ingsw.ps13.controller.actions.main;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class AcquirePermitTileAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final Collection<PoliticsCard> cards;
	private final Region region;
	private final PermitTile tile;
	
	/**
	 * 
	 * @param player
	 * @param cards
	 * @param region
	 * @param tile
	 */
	public AcquirePermitTileAction(Player player, Collection<PoliticsCard> cards, Region region, PermitTile tile) {
		
		this.player = player;
		this.cards = cards;
		this.region = region;
		this.tile = tile;
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		boolean legal = true;
		
		if(!region.getCouncillorBalcony().isSatisfiable(cards, player.getCoins()))
			legal = false;
		
		if(!region.getPermitTileDeck().getVisibleTiles().contains(tile))
			legal = false;
		
		return legal;
	}

	@Override
	public void apply(Game g) {
		
		CouncillorBalcony balcony = region.getCouncillorBalcony();
		
		player.consumeCoins(balcony.coinsToPay(cards));
		player.discardPoliticsCards(cards);
		player.receivePermitTile(tile);
		
		tile.getBonus().giveTo(player);
		
	}

	
	
}
