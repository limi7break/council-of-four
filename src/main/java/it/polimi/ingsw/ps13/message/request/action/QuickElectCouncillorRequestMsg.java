package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class QuickElectCouncillorRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
 
	private final String councillor;
	private final String region; 
	
	public QuickElectCouncillorRequestMsg(String region, String councillor) {
		
		this.councillor = councillor;
		this.region = region;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
	
		return av.visit(this);
		
	}
	
	public String getCouncillor() {
		return councillor;
	}
	
	public String getRegion() {
		return region;
	}

}
