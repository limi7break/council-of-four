package it.polimi.ingsw.ps13.controller.actions.main;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class KingAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final Collection<PoliticsCard> cards;
	private final City city;
	
	/**
	 * 
	 * @param player
	 * @param cards
	 * @param city
	 */
	public KingAction(Player player, Collection<PoliticsCard> cards, City city) {
		
		this.player = player;
		this.cards = cards;
		this.city = city;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		if(player.hasBuiltOn(city.getName()))
			legal = false;
		
		if(g.getBoard().getKingBalcony().isSatisfiable(cards, player.getCoins()))
			legal = false;
		
		int corruptionPrice = g.getBoard().getKingBalcony().coinsToPay(cards);
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
		
		int corruptionPrice = g.getBoard().getKingBalcony().coinsToPay(cards);
		int kingMovementPrice = g.getBoard().priceToMoveKing(city);
		int price = corruptionPrice + kingMovementPrice;
		
		player.consumeCoins(price);
		
		g.getBoard().setKingCity(city);
		
		Emporium emporium = player.removeEmporium();
		city.addEmporium(emporium);
		
	}
		
}
