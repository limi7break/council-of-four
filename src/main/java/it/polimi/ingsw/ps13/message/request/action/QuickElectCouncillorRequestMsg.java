package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class QuickElectCouncillorRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player; 
	private final Councillor councillor;
	private final Region region; 
	
	public QuickElectCouncillorRequestMsg(Player player, Councillor councillor, Region region) {
		
		this.player = player;
		this.councillor = councillor;
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
	
	public Councillor getCouncillor() {
		return councillor;
	}
	
	public Region getRegion() {
		return region;
	}

}
