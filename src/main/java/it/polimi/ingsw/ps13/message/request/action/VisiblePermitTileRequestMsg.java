package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a GainVisiblePermitTileAction.
 *
 */
public class VisiblePermitTileRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final String region;
	private final int tile;
	
	/**
	 * Creates a new VisiblePermitTileRequestMsg with the specified parameters.
	 * 
	 * @param region the name of the region entered by the player
	 * @param tile the number of the tile entered by the player
	 */
	public VisiblePermitTileRequestMsg(String region, int tile) {
		
		this.region = region;
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
	 * Returns the name of the region entered by the player.
	 * 
	 * @returnthe name of the region entered by the player
	 */
	public String getRegion() {
		return region;
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