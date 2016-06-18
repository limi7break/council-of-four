package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a ChangePermitTilesAction.
 *
 */
public class ChangePermitTilesRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String region;
	
	/**
	 * Creates a new ChangePermitTilesRequestMsg with the specified parameters.
	 * 
	 * @param region the name of the region entered by the player
	 */
	public ChangePermitTilesRequestMsg(String region) {
		
		this.region = region;
		
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
	 * Returns the name of the region entered by the player.
	 * 
	 * @return the name of the region entered by the player
	 */
	public String getRegion() {
		return region;
	}
	
}
