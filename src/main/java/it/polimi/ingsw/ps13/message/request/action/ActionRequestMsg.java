package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.message.request.RequestMsg;

public abstract class ActionRequestMsg extends RequestMsg {

	private static final long serialVersionUID = 0L;
	
	abstract void accept(ActionVisitor av);
	
}
