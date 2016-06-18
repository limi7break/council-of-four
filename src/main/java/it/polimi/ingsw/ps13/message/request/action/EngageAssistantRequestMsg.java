package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform an EngageAssistantAction.
 *
 */
public class EngageAssistantRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	/**
	 * Creates a new EngageAssistantRequestMsg.
	 */
	public EngageAssistantRequestMsg() { 
		
		// This action request message has no arguments
		
	}
	
	/**
	 * Returns the concrete action, constructed by the action visitor from this request message.
	 * 
	 */
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
}
