package it.polimi.ingsw.ps13.message.request.action;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a BuildEmporiumAction.
 *
 */
public class BuildEmporiumRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;
	
	private final int tile;
	private final String city;
	
	/**
	 * Creates a new BuildEmporiumRequestMsg with the specified parameters.
	 * 
	 * @param tile the number of the tile entered by the player
	 * @param city the name of the city entered by the player
	 */
	public BuildEmporiumRequestMsg(int tile, String city) {
		
		this.tile = tile;
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
	 * Returns the number of the tile entered by the player.
	 * 
	 * @return the number of the tile entered by the player
	 */
	public int getTile() {
		return tile;
	}
	
	/**
	 * Returns the name of the city entered by the player
	 * 
	 * @return the name of the city entered by the player
	 */
	public String getCity() {
		return city;
	}

}
