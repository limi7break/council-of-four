package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.player.Player;

public class TradeProposalRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final MarketEntry entry;
	
	public TradeProposalRequestMsg(Player player, MarketEntry entry) {
		
		this.player = player;
		this.entry = entry;
		
		setPlayerName(player.getName());
	}
	
	@Override
	public void accept(ActionVisitor av) {
		av.visit(this);
		
	}

	public Player getPlayer() {
		return player;
	}
	
	public MarketEntry getEntry() {
		return entry;
	}
	
}
