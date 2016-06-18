package it.polimi.ingsw.ps13.controller.actions.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.KingRewardTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

/**
 * This action is performed when a player wants to build an emporium on the city where the king resides.
 * To do so, the player must satisfy the king balcony and, if it's the case, pay coins to move the king to the
 * desired city.
 * 
 * This is a main action.
 *
 */
public class KingAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String city;
	private final Collection<String> cards;
	
	private final List<Color> cardColors;
	
	/**
	 * Creates a new KingAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param city the name of the city on which the player wants to build the emporium
	 * @param cards the colors of the politics cards played in order to satisfy king's councillor balcony
	 */
	public KingAction(String playerName, String city, Collection<String> cards) {
		
		this.playerName = playerName;
		this.city = capitalizeFirstLetter(city);
		this.cards = cards;
		
		cardColors = new ArrayList<>();
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Player has got at least one emporium
	 * 		- Selected city is valid
	 * 		- Player hasn't built on selected city yet
	 * 		- Selected colors are valid
	 * 		- Selected politics cards are actually in the player's hand
	 * 		- Councillor balcony is satisfiable with selected politics cards and player coins
	 * 		- Player has enough coins to move king to selected city
	 * 		- Player has enough assistants to build on selected city
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getMain() == 0)
			throw new IllegalActionException("Action is not available");
		
		//Check if player has at least one emporium
		if(player.getNumberOfEmporiums() == 0)
			throw new IllegalActionException("You have no emporiums left");
		
		// Check if city is a valid city
		if (!g.getBoard().getCities().containsKey(city))
			throw new IllegalActionException("Selected city is not valid");
		
		// Check if player has already built on the city
		if(player.hasBuiltOn(city))
			throw new IllegalActionException("You have already built on selected city");
		
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
		if(!g.getBoard().getKingBalcony().isSatisfiable(cardColors, player.getCoins()))
			throw new IllegalActionException("Councillor balcony is not satisfiable");
		
		int corruptionPrice = g.getBoard().getKingBalcony().coinsToPay(cardColors);
		int kingMovementPrice = g.getBoard().priceToMoveKing(g.getBoard().getCity(city));
		if(player.getCoins() < (corruptionPrice + kingMovementPrice))
			throw new IllegalActionException("Not enough coins, " + (corruptionPrice + kingMovementPrice) + " required");
		
		// Check if player has enough assistants (one for every emporium already built on the city)
		if(player.getAssistants() < g.getBoard().getCity(city).getNumberOfEmporiums())
			throw new IllegalActionException("Not enough assistants, " + g.getBoard().getCity(city).getNumberOfEmporiums() + "required");
			
		return legal;
		
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		City realCity = g.getBoard().getCity(city);
		
		int corruptionPrice = g.getBoard().getKingBalcony().coinsToPay(cardColors);
		int kingMovementPrice = g.getBoard().priceToMoveKing(realCity);
		int price = corruptionPrice + kingMovementPrice;
		
		player.consumeCoins(price);
		player.consumeAssistants(g.getBoard().getCity(city).getNumberOfEmporiums());
		
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
		
		g.getBoard().setKingCity(realCity);
		
		Emporium emporium = player.removeEmporium();
		realCity.addEmporium(emporium);
		player.addCity(city);
		
		realCity.giveBonuses(player);
		
		// Give the player the region bonus, if he has completed the region and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getRegion().isBonusAvailable() && player.getCityNames().containsAll(realCity.getRegion().getCityNames())) {
			realCity.getRegion().setBonusAvailable(false);
			realCity.getRegion().getBonus().giveTo(player);
			
			KingRewardTile krt = g.getBoard().getNextKingRewardTile();
			if (krt != null) {
				krt.getBonus().giveTo(player);
				krt.setAvailable(false);
			}
		}
		
		// Give the player the color bonus, if he has completed the color and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getCityColor().isBonusAvailable() && player.getCityNames().containsAll(realCity.getCityColor().getCityNames())) {
			realCity.getCityColor().setBonusAvailable(false);
			realCity.getCityColor().getBonus().giveTo(player);
			
			KingRewardTile krt = g.getBoard().getNextKingRewardTile();
			if (krt != null) {
				krt.getBonus().giveTo(player);
				krt.setAvailable(false);
			}
		}
		
		if ( (player.getNumberOfEmporiums() == 0) && (g.getPlayerWhoBuiltLastEmporium() == -1) ) {
			g.setPlayerWhoBuiltLastEmporium(player.getID());
			player.addVictoryPoints(3);
		}
		
		player.consumeMainAction();
		
	}
	
	private String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
		
}
