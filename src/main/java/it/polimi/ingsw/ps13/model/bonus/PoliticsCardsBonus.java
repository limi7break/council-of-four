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
		
		// @TODO: come gli altri: chiamare controller per pescare da deck di board del game con player?
		
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
