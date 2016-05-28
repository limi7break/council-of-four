package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.player.Player;

public class OfferSelectionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final int entry;
	
	public OfferSelectionRequestMsg(Player player, int entry) {
		
		this.player = player;
		this.entry = entry;
		
		setPlayerName(player.getName());
		
	}
	
	@Override
	public void accept(ActionVisitor av) {
		
		av.visit(this);
		
	}

	public Player getPlayer() {
		return player;
	}
	
	public int getEntry() {
		return entry;
	}
	
}
