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
		
		player.setMainActions(player.getMainActions() + amount);
		
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
