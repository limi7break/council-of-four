package it.polimi.ingsw.ps13.controller.actions.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;

public class AcquirePermitTileAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String region;
	private final int tile;
	private final Collection<String> cards;
	
	private final List<Color> cardColors;
	
	/**
	 * 
	 * @param player
	 * @param cards
	 * @param region
	 * @param tile
	 */
	public AcquirePermitTileAction(String playerName, String region, int tile, Collection<String> cards) {
		
		this.playerName = playerName;
		this.cards = cards;
		this.region = region;
		this.tile = tile;
		
		cardColors = new ArrayList<>();
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getMain() == 0)
			legal = false;
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region))
			return false;
		
		// Check if tile is a valid visible permit tile number
		if ( (tile > g.getBoard().getRegion(region).getPermitTileDeck().getVisibleTiles().size()-1)
				|| (tile < 0) )
			legal = false;
		
		for (String card : cards) {
			if ("jolly".equals(card))
				cardColors.add(PoliticsCard.jollyColor);
			else if (!g.getColors().containsKey(card))
				return false;
			else
				cardColors.add(g.getColors().get(card));
		}
		
		List<Color> playerCardColors = new ArrayList<>();
		
		for (PoliticsCard card : player.getPoliticsCards()) {
			playerCardColors.add(card.getColor());
		}
		
		if (!playerCardColors.containsAll(cardColors))
			legal = false;
		
		if(!g.getBoard().getRegion(region).getCouncillorBalcony().isSatisfiable(cardColors, player.getCoins()))
			legal = false;
		
		return legal;
	}

	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.consumeCoins(g.getBoard().getRegion(region).getCouncillorBalcony().coinsToPay(cardColors));
		
		for (Color color : cardColors) {
			for (Iterator<PoliticsCard> it = player.getPoliticsCards().iterator(); it.hasNext();) {
				PoliticsCard current = it.next();
				if (color.equals(current.getColor())) {
					it.remove();
					g.getBoard().getPoliticsCardDeck().discardCard(current);
					break;
				}
			}
		}
		
		PermitTile permitTile = g.getBoard().getRegion(region).getPermitTileDeck().takeTile(tile);
		player.receivePermitTile(permitTile);
		permitTile.getBonus().giveTo(player);
		
		player.consumeMainAction();
		
	}
	
}
