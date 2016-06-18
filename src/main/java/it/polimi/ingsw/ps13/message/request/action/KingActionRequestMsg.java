package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a KingAction.
 *
 */
public class KingActionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String city;
	private final Collection<String> cards;
	
	/**
	 * Creates a new KingActionRequestMsg with the specified parameters.
	 * 
	 * @param city the name of the city entered by the player
	 * @param cards the names of the politics cards' colors entered by the player
	 */
	public KingActionRequestMsg(String city, Collection<String> cards) {
		
		this.cards = cards;
		this.city = city;
		
	}
	
	/**
	 * Returns the concrete action, constructed by the action visitor from this request message.
	 * 
	 */
	@Override
	public Action accept(ActionVisitor av) {
		
		return av.visit(this);
		
	}
	
	/**
	 * Returns the names of the politics cards' colors entered by the player.
	 * 
	 * @return the names of the politics cards' colors entered by the player
	 */
	public Collection<String> getCards() {
		return cards;
	}
	
	/**
	 * Returns the name of the city entered by the player.
	 * 
	 * @return the name of the city entered by the player
	 */
	public String getCity() {
		return city;
	}
	
}
