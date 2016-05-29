package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class OfferSelectionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final int entry;
	
	public OfferSelectionRequestMsg(int entry) {
		
		this.entry = entry;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public int getEntry() {
		return entry;
	}
	
}
