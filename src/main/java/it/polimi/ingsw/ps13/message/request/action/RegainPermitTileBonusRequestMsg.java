package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a RegainPermitTileBonusAction.
 *
 */
public class RegainPermitTileBonusRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final int tile;
	
	/**
	 * Creates a new RegainPermitTileBonusRequestMsg with the specified parameters.
	 * 
	 * @param tile the number of the tile entered by the player
	 */
	public RegainPermitTileBonusRequestMsg(int tile) {
		
		this.tile = tile;
		
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
	 * Returns the number of the tile entered by the player.
	 * 
	 * @return the number of the tile entered by the player
	 */
	public int getTile() {
		return tile;
	}
	
}
