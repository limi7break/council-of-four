package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.model.player.Player;

public abstract class ActionRequestMsg extends RequestMsg {

	private static final long serialVersionUID = 0L;
	
	private Player player;
	
	abstract Action accept(ActionVisitor av);
	
	public Player getPlayer() {
		
		return player;
		
	}
	
	public void setPlayer(Player player) {
		
		this.player = player;
		
	}
	
}
