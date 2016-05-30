package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.market.Marketable;

/**
 * 
 *
 */
public class Assistants extends Resource implements Marketable, Serializable {

	private static final long serialVersionUID = 0L;

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
