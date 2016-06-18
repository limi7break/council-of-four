package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform an OfferSelectionAction.
 *
 */
public class OfferSelectionRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final Collection<Integer> entries;
	
	/**
	 * Creates a new OfferSelectionRequestMsg with the specified parameters.
	 * 
	 * @param entries the number of the market entries entered by the player
	 */
	public OfferSelectionRequestMsg(Collection<Integer> entries) {
		
		this.entries = entries;
		
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
	 * Returns the number of the market entries entered by the player.
	 * 
	 * @return the number of the market entries entered by the player
	 */
	public Collection<Integer> getEntries() {
		return entries;
	}
	
}
