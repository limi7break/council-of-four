package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.region.Region;

public class QuickElectCouncillorRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
 
	private final Councillor councillor;
	private final Region region; 
	
	public QuickElectCouncillorRequestMsg(Councillor councillor, Region region) {
		
		this.councillor = councillor;
		this.region = region;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
	
		return av.visit(this);
		
	}
	
	public Councillor getCouncillor() {
		return councillor;
	}
	
	public Region getRegion() {
		return region;
	}

}
