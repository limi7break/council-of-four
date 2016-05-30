package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class RegainRewardTokenRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String city; //The city whose token wants to be acquired again
	
	public RegainRewardTokenRequestMsg(String city) {
		
		this.city = city;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public String getCity() {
		return city;
	}
	
}
