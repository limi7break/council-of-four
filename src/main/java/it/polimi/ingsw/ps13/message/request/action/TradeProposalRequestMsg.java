package it.polimi.ingsw.ps13.message.request.action;

import java.util.Collection;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;

/**
 * This message is sent from the client when the player wants to perform a TradeProposalAction.
 *
 */
public class TradeProposalRequestMsg extends ActionRequestMsg {

	private static final long serialVersionUID = 0L;

	private final int assistants;
	private final Collection<Integer> tiles;
	private final Collection<String> cards;
	private final int price;
	
	/**
	 * Creates a new TradeProposalRequestMsg with the specified parameters.
	 * 
	 * @param assistants the number of assistants entered by the player
	 * @param tiles the number of the tiles entered by the player
	 * @param cards the names of the politics cards' colors entered by the player
	 * @param price the number of coins entered by the player
	 */
	public TradeProposalRequestMsg(int assistants, Collection<Integer> tiles, Collection<String> cards, int price) {
		
		this.assistants = assistants;
		this.tiles = tiles;
		this.cards = cards;
		this.price = price;
		
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
	 * Returns the number of assistants entered by the player.
	 * 
	 * @return the number of assistants entered by the player
	 */
	public int getAssistants() {
		return assistants;
	}

	/**
	 * Returns the number of the tiles entered by the player.
	 * 
	 * @return the number of the tiles entered by the player
	 */
	public Collection<Integer> getTiles() {
		return tiles;
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
	 * Returns the number of coins entered by the player.
	 * 
	 * @return the number of coins entered by the player
	 */
	public int getPrice() {
		return price;
	}
	
}
