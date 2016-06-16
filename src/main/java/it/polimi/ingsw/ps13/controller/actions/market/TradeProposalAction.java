package it.polimi.ingsw.ps13.controller.actions.market;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.resource.Assistants;

/**
 * The items filling the entry have to be removed from the player supply by this action factory.
 *
 */
public class TradeProposalAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final int assistants;
	private final Collection<Integer> tiles;
	private final Collection<String> cards;
	private final int price;
	
	private final List<Color> cardColors;
	
	/**
	 * 
	 * @param player
	 * @param entry
	 */
	public TradeProposalAction(String playerName, int assistants, Collection<Integer> tiles, Collection<String> cards, int price) {
		
		this.playerName = playerName;
		this.assistants = assistants;
		this.tiles = tiles;
		this.cards = cards;
		this.price = price;
		
		cardColors = new ArrayList<>();
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getSell() == 0)
			legal = false;
		
		// Check if player has the declared amount of assistants
		if (player.getAssistants() < assistants)
			legal = false;
		
		// Check if selected permit tiles are valid
		for (Integer i : tiles) {
			if ( (i > g.getPlayer(playerName).getPermitTiles().size()-1)
					|| (i < 0) )
				return false;
		}
		
		// Retrieve colors of the selected politics cards from color names
		for (String card : cards) {
			if ("jolly".equals(card))
				cardColors.add(PoliticsCard.jollyColor);
			else if (!g.getColors().containsKey(card))
				return false;
			else
				cardColors.add(g.getColors().get(card));
		}
		
		// Retrieve the colors of the politics cards in the player's hand
		List<Color> playerCardColors = new ArrayList<>();
		for (PoliticsCard card : player.getPoliticsCards()) {
			playerCardColors.add(card.getColor());
		}
		
		// Check if player has the selected politics cards
		for (Color c : cardColors) {
			boolean matchFound = false;
			for (Iterator<Color> it = playerCardColors.iterator(); it.hasNext() && !matchFound;) {
				Color color = it.next();
				if (color.equals(c)) {
					matchFound = true;
					it.remove();
				}
			}
			if (!matchFound)
				return false;
		}
		
		return legal;
		
	}

	/**
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		List<Marketable> items = new ArrayList<>();
		
		player.consumeAssistants(assistants);
		items.add(new Assistants(assistants));
		
		for (Color color : cardColors) {
			for (Iterator<PoliticsCard> it = player.getPoliticsCards().iterator(); it.hasNext();) {
				PoliticsCard current = it.next();
				if (color.equals(current.getColor())) {
					it.remove();
					items.add(current);
					break;
				}
			}
		}
		
		for (Integer i : tiles) {
			PermitTile current = player.getPermitTiles().get(i);
			player.getPermitTiles().remove(i.intValue());
			current.setUsable(true);
			items.add(current);
		}
		
		MarketEntry entry = new MarketEntry(player, items, price);
		g.getMarket().addEntry(entry);
		
		player.consumeSellAction();
		
	}
	
}
