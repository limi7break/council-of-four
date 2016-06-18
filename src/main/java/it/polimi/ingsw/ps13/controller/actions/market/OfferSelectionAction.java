package it.polimi.ingsw.ps13.controller.actions.market;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This action is performed when a player wants to buy one or more market entries during the buy market phase.
 *
 * This action is a buy action and can only be performed during the buy market phase.
 *
 */
public class OfferSelectionAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final Collection<Integer> entries;
	
	/**
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param entries the number of the market entries the player wants to buy
	 */
	public OfferSelectionAction(String playerName, Collection<Integer> entries) {
		
		this.playerName = playerName;
		this.entries = entries;
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Selected market entries is valid
	 * 		- Player has enough coins to buy selected entries
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getBuy() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if entry is a valid market entry number
		int totalPrice = 0;
		for (Integer entry : entries) {
			if ( (entry.intValue() > g.getMarket().getEntryList().size()-1)
				|| (entry.intValue() < 0) )
				throw new IllegalActionException("Selected market entry is not valid");
			
			totalPrice += g.getMarket().getEntryList().get(entry).getPrice();
		}
		
		if(player.getCoins() < totalPrice)
			throw new IllegalActionException("Not enough coins, " + totalPrice + " required");
		
		return true;
		
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		Set<Integer> ndEntries = new TreeSet<>(entries);
		for (Integer entry : ndEntries) {
			g.getMarket().manageTransaction(player, entry);
		}
		
		g.getMarket().removeEntries(new ArrayList<Integer>(ndEntries));
		
		player.consumeBuyAction();
		
	}

}
