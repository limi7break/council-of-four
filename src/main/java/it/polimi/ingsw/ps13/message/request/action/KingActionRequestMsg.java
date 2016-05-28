package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class KingActionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final Collection<PoliticsCard> cards;
	private final City city;
	
	public KingActionRequestMsg(Player player, Collection<PoliticsCard> cards, City city) {
		
		this.player = player;
		this.cards = cards;
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
	
	public Collection<PoliticsCard> getCards() {
		return cards;
	}
	
	public City getCity() {
		return city;
	}
	
}
