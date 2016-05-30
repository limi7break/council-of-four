package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class ChangePermitTilesRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String region;
	
	public ChangePermitTilesRequestMsg(String region) {
		
		this.region = region;
		
	}

	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public String getRegion() {
		return region;
	}
	
}
