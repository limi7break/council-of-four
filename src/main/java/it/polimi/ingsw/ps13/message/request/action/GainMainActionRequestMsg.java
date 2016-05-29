package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class GainMainActionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	public GainMainActionRequestMsg() { }
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
}
