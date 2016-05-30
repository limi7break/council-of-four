package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class ElectCouncillorRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String region;
	private final String councillor;
	
	public ElectCouncillorRequestMsg(String region, String councillor) {
		
		this.region = region;
		this.councillor = councillor;
		
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
