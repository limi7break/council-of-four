package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform an AcquirePermitTileAction.
 *
 */
public class AcquirePermitTileRequestMsg extends ActionRequestMsg{

	private static final long serialVersionUID = 0L;

	private final Collection<String> cards;
	private final String region;
	private final int tile;
	
	/**
	 * Creates a new AcquirePermitTileRequestMsg with the specified parameters.
	 * 
	 * @param region the name of the region entered by the player
	 * @param tile the number of the tile entered by the player
	 * @param cards the names of the politics cards' colors entered by the player 
	 */
	public AcquirePermitTileRequestMsg(String region, int tile, Collection<String> cards) {
	
		this.region = region;
		this.tile = tile;
		this.cards = cards;
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
	
	/**
	 * Returns the number of the tile entered by the player.
	 * 
	 * @return the number of the tile entered by the player
	 */
	public int getTile() {
		return tile;
	}
	
	/**
	 * Returns the names of the politics cards' colors entered by the player.
	 * 
	 * @return the names of the politics cards' colors entered by the player
	 */
	public Collection<String> getCards() {
		return cards;
	}
	
}
