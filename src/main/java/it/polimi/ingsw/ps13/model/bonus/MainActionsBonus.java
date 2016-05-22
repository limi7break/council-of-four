package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class MainActionsBonus implements Bonus, Serializable {

	private static final long serialVersionUID = 0L;
	private final int amount;
	
	protected MainActionsBonus(int amount){
		
		this.amount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.addMainActions(amount);
		
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
		
		return "MainActionsBonus "
				+ "x" + amount;
		
	}
}
