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

	private final Player player;
	private final int entry;
	
	/**
	 * 
	 * @param player
	 * @param entry
	 */
	public OfferSelectionAction(Player player, int entry) {
		
		this.player = player;
		this.entry = entry;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		int price = g.getMarket().getEntryList().get(entry).getPrice();
		if(player.getCoins() < price)
			legal = false;
		
		return legal;
		
	}

	/**
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		g.getMarket().manageTransaction(player, entry);
		
	}

}
