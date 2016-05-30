package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

public class AcquirePermitTileRequestMsg extends ActionRequestMsg{

	private static final long serialVersionUID = 0L;

	private final Collection<String> cards;
	private final String region;
	private final int tile;
	
	public AcquirePermitTileRequestMsg(String region, int tile, Collection<String> cards) {
	
		this.region = region;
		this.tile = tile;
		this.cards = cards;
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public String getRegion() {
		return region;
	}
	
	public int getTile() {
		return tile;
	}
	
	public Collection<String> getCards() {
		return cards;
	}
	
}
