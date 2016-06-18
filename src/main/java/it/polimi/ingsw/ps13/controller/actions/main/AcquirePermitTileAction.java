package it.polimi.ingsw.ps13.controller.actions.main;

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
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This action is performed when the player wants to acquire a visible permit tile from a region,
 * satisfying the region's councillor balcony with politics cards and (if necessary) coins.
 * 
 * This is a main action.
 *
 */
public class AcquirePermitTileAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String region;
	private final int tile;
	private final Collection<String> cards;
	
	private final List<Color> cardColors;
	
	/**
	 * Creates a new AcquirePermitTileAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param region the name of the region to which the selected permit tile belongs
	 * @param tile the number of the tile in selected region's visible tiles
	 * @param cards the colors of the politics cards played in order to satisfy region's councillor balcony
	 */
	public AcquirePermitTileAction(String playerName, String region, int tile, Collection<String> cards) {
		
		this.playerName = playerName;
		this.cards = cards;
		this.region = region;
		this.tile = tile;
		
		cardColors = new ArrayList<>();
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Selected region is valid
	 * 		- Selected permit tile number is valid
	 * 		- Selected colors are valid
	 * 		- Selected politics cards are actually in the player's hand
	 * 		- Councillor balcony is satisfiable with selected politics cards and player coins 
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getMain() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region))
			throw new IllegalActionException("Selected region is not valid");
		
		// Check if tile is a valid visible permit tile number
		if ( (tile > g.getBoard().getRegion(region).getPermitTileDeck().getVisibleTiles().size()-1)
				|| (tile < 0) )
			throw new IllegalActionException("Selected permit tile is not valid");
		
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
		
		// Check if balcony is satisfiable
		if(!g.getBoard().getRegion(region).getCouncillorBalcony().isSatisfiable(cardColors, player.getCoins()))
			throw new IllegalActionException("Councillor balcony is not satisfiable");
		
		return legal;
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
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
		// Permit tile bonus is given by receivePermitTile method
		player.receivePermitTile(permitTile);
		
		player.consumeMainAction();
		
	}
	
}
