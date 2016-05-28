package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class RegainRewardTokenRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final City city; //The city whose token wants to be acquired again
	
	public RegainRewardTokenRequestMsg(Player player, City city) {
		
		this.player = player;
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
	
	public City getCity() {
		return city;
	}
	
}
