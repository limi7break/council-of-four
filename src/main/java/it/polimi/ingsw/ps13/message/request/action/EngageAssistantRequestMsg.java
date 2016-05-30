package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class EngageAssistantRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	public EngageAssistantRequestMsg() { 
		
		// This action request message has no arguments
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
}
