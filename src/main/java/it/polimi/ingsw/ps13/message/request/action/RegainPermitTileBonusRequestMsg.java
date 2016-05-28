package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;

public class RegainPermitTileBonusRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final PermitTile tile;
	
	public RegainPermitTileBonusRequestMsg(Player player, PermitTile tile) {
		
		this.player = player;
		this.tile = tile;
		
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
	
}
