package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.market.Marketable;

/**
 * This class represents any non negative amount of assistants in the game.
 * It is used in bonuses and in player supply. 
 *
 */
public class Assistants extends Resource implements Marketable, Serializable {

	private static final long serialVersionUID = 0L;

	/**
	 * Constructs a new assistants object with the specified initial amount.
	 * 
	 * @param amount the initial amount of the assistants object
	 */
	public Assistants(int amount){
		
		super(amount);
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.addAssistants(this.getAmount());
		
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		
		return "Ax" + amount;
		
	}
}
