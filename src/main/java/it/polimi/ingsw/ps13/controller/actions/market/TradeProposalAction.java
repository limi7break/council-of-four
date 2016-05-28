package it.polimi.ingsw.ps13.controller.actions.market;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * The items filling the entry have to be removed from the player supply by this action factory.
 *
 */
public class TradeProposalAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final MarketEntry entry;
	
	/**
	 * 
	 * @param player
	 * @param entry
	 */
	public TradeProposalAction(Player player, MarketEntry entry) {
		
		this.player = player;
		this.entry = entry;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		if(!entry.getSeller().equals(player))
			legal = false;
		
		return legal;
		
	}

	/**
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		g.getMarket().addEntry(entry);
		
	}
	
}
