package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class RegainRewardTokenBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;

	protected RegainRewardTokenBonus () {
		
		
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		// @TODO: implement this method
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "RegainRewardTokenBonus";
		
	}
	
}
