package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class TradeProposalRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final int assistants;
	private final Collection<Integer> tiles;
	private final Collection<String> cards;
	private final int price;
	
	public TradeProposalRequestMsg(int assistants, Collection<Integer> tiles, Collection<String> cards, int price) {
		
		this.assistants = assistants;
		this.tiles = tiles;
		this.cards = cards;
		this.price = price;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}

	public int getAssistants() {
		return assistants;
	}

	public Collection<Integer> getTiles() {
		return tiles;
	}

	public Collection<String> getCards() {
		return cards;
	}

	public int getPrice() {
		return price;
	}
	
}
