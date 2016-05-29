package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.market.MarketEntry;

public class TradeProposalRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final MarketEntry entry;
	
	public TradeProposalRequestMsg(MarketEntry entry) {
		
		this.entry = entry;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public MarketEntry getEntry() {
		return entry;
	}
	
}
