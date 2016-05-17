package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class VisiblePermitTileBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;

	protected VisiblePermitTileBonus(){
		

		
	}
	
	@Override
	public void giveTo(Player player) {
		
		// @TODO: notify view -> controller ?
		
	}

}
