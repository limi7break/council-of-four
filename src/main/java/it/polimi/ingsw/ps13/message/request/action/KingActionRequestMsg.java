package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.region.City;

public class KingActionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Collection<PoliticsCard> cards;
	private final City city;
	
	public KingActionRequestMsg(Collection<PoliticsCard> cards, City city) {
		
		this.cards = cards;
		this.city = city;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public Collection<PoliticsCard> getCards() {
		return cards;
	}
	
	public City getCity() {
		return city;
	}
	
}
