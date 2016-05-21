package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class PoliticsCardsBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;
	private int amount;
	
	protected PoliticsCardsBonus(int amount){
		
		this.amount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		// @TODO: think of a way to do this according to the fucking rules...
		//player.drawPoliticsCard(amount);		[method doesn't exist anymore]
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAmount() {
		
		return amount;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isEmpty() {
		
		return amount == 0;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "PoliticsCardsBonus "
				+ "x" + amount;
		
	}

}
