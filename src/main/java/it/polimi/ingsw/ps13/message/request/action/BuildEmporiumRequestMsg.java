package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class BuildEmporiumRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	private final int tile;
	private final String city;
	
	public BuildEmporiumRequestMsg(int tile, String city) {
		
		this.tile = tile;
		this.city = city;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public int getTile() {
		return tile;
	}
	
	public String getCity() {
		return city;
	}

}
