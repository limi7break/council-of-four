package it.polimi.ingsw.ps13.controller.actions.market;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.resource.Assistants;

/**
 * This action is performed when a player wants to sell a Marketable item during the sell
 * market phase. The items filling the entry are removed from the player supply by this action's apply method
 * and put in the market. If by the end of the sell market phase no one has bought the entry, the items
 * are returned to the player.
 * 
 * This action is a sell action and can only be performed once during the sell market phase.
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
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param assistants the number of assistants the player wants to sell
	 * @param tiles the number of the permit tiles (in the player's hand) the player wants to sell
	 * @param cards the colors of the politics cards the player wants to sell
	 * @param price the price in coins the player has entered for the market entry
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
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Player has the declared amount of assistants
	 * 		- Player has selected valid permit tiles from hand
	 * 		- Player has selected valid politics colors
	 * 		- Player has the declared politics cards
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getSell() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if player has the declared amount of assistants
		if (player.getAssistants() < assistants)
			throw new IllegalActionException("Not enough assistants, " + player.getAssistants() + " required");
		
		// Check if selected permit tiles are valid
		for (Integer i : tiles) {
			if ( (i > g.getPlayer(playerName).getPermitTiles().size()-1)
					|| (i < 0) )
				throw new IllegalActionException("Selected permit tile is not valid");
		}
		
		// Retrieve colors of the selected politics cards from color names
		for (String card : cards) {
			if ("jolly".equals(card))
				cardColors.add(PoliticsCard.jollyColor);
			else if (!g.getColors().containsKey(card))
				throw new IllegalActionException("Selected color is not valid");
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
				throw new IllegalActionException("Politics cards mismatch");
		}
		
		return legal;
		
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
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
