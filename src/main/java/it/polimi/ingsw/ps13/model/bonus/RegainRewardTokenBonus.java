package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents a regain reward token bonus.
 *
 */
public class RegainRewardTokenBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;

	protected RegainRewardTokenBonus () { }
	
	@Override
	public void giveTo(Player player) {
		
		player.addRewardTokenToken(1);
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "Regain Reward Token Bonus";
		
	}
	
}
