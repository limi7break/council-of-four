package it.polimi.ingsw.ps13.controller.actions.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
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
		this.city = city;
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
		
		// Check if player has already build on the city
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
		int kingMovementPrice = g.getBoard().priceToMoveKing(city);
		
		if(player.getCoins() < (corruptionPrice + kingMovementPrice))
			legal = false;
			
		return legal;
		
	}

	/**
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		int corruptionPrice = g.getBoard().getKingBalcony().coinsToPay(cardColors);
		int kingMovementPrice = g.getBoard().priceToMoveKing(city);
		int price = corruptionPrice + kingMovementPrice;
		
		player.consumeCoins(price);
		
		City realCity = g.getBoard().getCity(city);
		
		g.getBoard().setKingCity(realCity);
		
		Emporium emporium = player.removeEmporium();
		realCity.addEmporium(emporium);
		player.addCity(city);
		
		player.consumeMainAction();
		
	}
		
}
