package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a RegainRewardTokenAction.
 *
 */
public class RegainRewardTokenRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String city;
	
	/**
	 * Creates a new RegainRewardTokenRequestMsg with the specified parameters.
	 * 
	 * @param city the name of the city entered by the player
	 */
	public RegainRewardTokenRequestMsg(String city) {
		
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
	 * Returns the name of the city entered by the player.
	 * 
	 * @return the name of the city entered by the player
	 */
	public String getCity() {
		return city;
	}
	
}
