package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.region.City;

public class BuildEmporiumRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	private final PermitTile tile;
	private final City city;
	
	public BuildEmporiumRequestMsg(PermitTile tile, City city) {
		
		this.tile = tile;
		this.city = city;
		
	}
	
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	public PermitTile getTile() {
		return tile;
	}
	
	public City getCity() {
		return city;
	}

}
