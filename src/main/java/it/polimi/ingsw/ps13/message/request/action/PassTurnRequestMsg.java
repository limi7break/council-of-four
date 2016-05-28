package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.player.Player;

public class PassTurnRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	private final Player player;
	
	/**
	 * 
	 * @param player
	 */
	public PassTurnRequestMsg(Player player) {
		
		this.player = player;
		setPlayerName(player.getName());
		
	}
	
	/**
	 * 
	 */
	@Override
	public void accept(ActionVisitor av) {
		av.visit(this);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getPlayer() {
		
		return player;
		
	}
	
}
