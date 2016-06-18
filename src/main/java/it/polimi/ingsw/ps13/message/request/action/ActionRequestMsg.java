package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.message.request.RequestMsg;

/**
 * This class represents a generic action request message coming from a client.
 * 
 * It is converted into a concrete Action by an action factory which implements a
 * visitor pattern.
 *
 */
public abstract class ActionRequestMsg extends RequestMsg {

	private static final long serialVersionUID = 0L;
	
	/**
	 * This method is implemented by every action request message and simply calls the
	 * visit method on the passed action visitor, passing the message itself as parameter.
	 * 
	 * @param av an action visitor which converts action request messages into actions
	 * @return the concrete action constructed from the request message
	 */
	public abstract Action accept(ActionVisitor av);
	
}
