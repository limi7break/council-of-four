package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents a regain permit tile bonus.
 *
 */
public class RegainPermitTileBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;

	protected RegainPermitTileBonus() { }
	
	@Override
	public void giveTo(Player player) {
	
		player.addTileBonusToken(1);
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "Regain Permit Tile Bonus";
		
	}
	
}
