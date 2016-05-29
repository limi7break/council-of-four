package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;

public class VisiblePermitTileRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final PermitTile tile;
	
	public VisiblePermitTileRequestMsg(PermitTile tile) {
		
		this.tile = tile;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public PermitTile getTile() {
		return tile;
	}

}