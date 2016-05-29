package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.region.Region;

public class ChangePermitTilesRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Region region;
	
	public ChangePermitTilesRequestMsg(Region region) {
		
		this.region = region;
		
	}

	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public Region getRegion() {
		return region;
	}
	
}
