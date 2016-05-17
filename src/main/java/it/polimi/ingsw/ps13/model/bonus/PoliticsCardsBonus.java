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
		
		player.drawPoliticsCard(amount);
		
	}

}
