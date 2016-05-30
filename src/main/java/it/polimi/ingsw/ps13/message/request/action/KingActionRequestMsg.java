package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class KingActionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String city;
	private final Collection<String> cards;
	
	public KingActionRequestMsg(String city, Collection<String> cards) {
		
		this.cards = cards;
		this.city = city;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public Collection<String> getCards() {
		return cards;
	}
	
	public String getCity() {
		return city;
	}
	
}
