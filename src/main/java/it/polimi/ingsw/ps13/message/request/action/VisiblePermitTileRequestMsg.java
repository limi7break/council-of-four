package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class VisiblePermitTileRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String region;
	private final int tile;
	
	public VisiblePermitTileRequestMsg(String region, int tile) {
		
		this.region = region;
		this.tile = tile;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public String getRegion() {
		return region;
	}
	
	public int getTile() {
		return tile;
	}

}