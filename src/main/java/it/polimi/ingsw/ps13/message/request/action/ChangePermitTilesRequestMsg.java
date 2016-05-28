package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class ChangePermitTilesRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final Region region;
	
	public ChangePermitTilesRequestMsg(Player player, Region region) {
		
		this.player = player;
		this.region = region;
		
		setPlayerName(player.getName());
		
	}

	@Override
	public void accept(ActionVisitor av) {
		
		av.visit(this);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Region getRegion() {
		return region;
	}
	
}
