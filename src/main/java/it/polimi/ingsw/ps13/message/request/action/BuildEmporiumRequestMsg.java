package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class BuildEmporiumRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	private final Player player;
	private final PermitTile tile;
	private final City city;
	
	public BuildEmporiumRequestMsg(Player player, PermitTile tile, City city) {
		
		this.player = player;
		this.tile = tile;
		this.city = city;
		
		setPlayerName(player.getName());
		
	}
	
	@Override
	public void accept(ActionVisitor av) {
		
		av.visit(this);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public PermitTile getTile() {
		return tile;
	}
	
	public City getCity() {
		return city;
	}

}
