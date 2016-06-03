package it.polimi.ingsw.ps13.controller.actions.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class KingAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String city;
	private final Collection<String> cards;
	
	private final List<Color> cardColors;
	
	/**
	 * 
	 * @param player
	 * @param cards
	 * @param city
	 */
	public KingAction(String playerName, String city, Collection<String> cards) {
		
		this.playerName = playerName;
		this.city = capitalizeFirstLetter(city);
		this.cards = cards;
		
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
		if (player.getTokens().getMain() == 0)
			legal = false;
		
		// Check if city is a valid city
		if (!g.getBoard().getCities().containsKey(city))
			return false;
		
		// Check if player has already built on the city
		if(player.hasBuiltOn(city))
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
		
		if(!g.getBoard().getKingBalcony().isSatisfiable(cardColors, player.getCoins()))
			legal = false;
		
		int corruptionPrice = g.getBoard().getKingBalcony().coinsToPay(cardColors);
		int kingMovementPrice = g.getBoard().priceToMoveKing(g.getBoard().getCity(city));
		
		if(player.getCoins() < (corruptionPrice + kingMovementPrice))
			legal = false;
		
		// Check if player has enough assistants (one for every emporium already built on the city)
		if(player.getAssistants() < g.getBoard().getCity(city).getNumberOfEmporiums())
			legal = false;
			
		return legal;
		
	}

	/**
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
			g.getBoard().getKingRewardTile().giveTo(player);
		}
		
		// Give the player the color bonus, if he has completed the color and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getCityColor().isBonusAvailable() && player.getCityNames().containsAll(realCity.getCityColor().getCityNames())) {
			realCity.getCityColor().setBonusAvailable(false);
			realCity.getCityColor().getBonus().giveTo(player);
			g.getBoard().getKingRewardTile().giveTo(player);
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
