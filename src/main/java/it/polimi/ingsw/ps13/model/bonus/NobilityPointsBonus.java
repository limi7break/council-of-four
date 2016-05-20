package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class NobilityPointsBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;
	private final int amount;
	
	protected NobilityPointsBonus(int amount){
		
		this.amount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.setNobilityPosition(player.getNobilityPosition() + amount);
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "NobilityPointsBonus "
				+ "x" + amount;
		
	}

}
