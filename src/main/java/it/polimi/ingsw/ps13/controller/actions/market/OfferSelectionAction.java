package it.polimi.ingsw.ps13.controller.actions.market;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * 
 *
 */
public class OfferSelectionAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final int entry;
	
	/**
	 * 
	 * @param player
	 * @param entry
	 */
	public OfferSelectionAction(String playerName, int entry) {
		
		this.playerName = playerName;
		this.entry = entry;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isLegal(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getBuy() == 0)
			return false;
		
		int price = g.getMarket().getEntryList().get(entry).getPrice();
		if(player.getCoins() < price)
			return false;
		
		return true;
		
	}

	/**
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		g.getMarket().manageTransaction(player, entry);
		
		player.consumeBuyAction();
		
	}

}
