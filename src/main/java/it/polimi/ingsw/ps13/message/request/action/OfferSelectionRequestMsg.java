package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class OfferSelectionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Collection<Integer> entries;
	
	public OfferSelectionRequestMsg(Collection<Integer> entries) {
		
		this.entries = entries;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public Collection<Integer> getEntries() {
		return entries;
	}
	
}
