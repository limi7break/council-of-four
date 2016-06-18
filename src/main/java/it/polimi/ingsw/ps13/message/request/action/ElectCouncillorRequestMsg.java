package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform an ElectCouncillorAction.
 *
 */
public class ElectCouncillorRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String region;
	private final String councillor;
	
	/**
	 * Creates a new ElectCouncillorRequestMsg with the specified parameters.
	 * 
	 * @param region the name of the region entered by the player
	 * @param councillor the name of the councillor's color entered by the player
	 */
	public ElectCouncillorRequestMsg(String region, String councillor) {
		
		this.region = region;
		this.councillor = councillor;
		
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
	 * Returns the name of the councillor's color entered by the player.
	 * 
	 * @return the name of the councillor's color entered by the player
	 */
	public String getCouncillor() {
		return councillor;
	}
	
	/**
	 * Returns the name of the region entered by the player.
	 * 
	 * @return the name of the region entered by the player
	 */
	public String getRegion() {
		return region;
	}
	
}
