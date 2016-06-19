package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class PoliticsCardsBonus implements Bonus, Serializable {

	private static final long serialVersionUID = 0L;
	private int politicsCardsAmount;
	
	public PoliticsCardsBonus(int amount){
		
		this.politicsCardsAmount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.drawPoliticsCards(politicsCardsAmount);
	
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAmount() {
		
		return politicsCardsAmount;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isEmpty() {
		
		return politicsCardsAmount == 0;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "PCx" + politicsCardsAmount;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + politicsCardsAmount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PoliticsCardsBonus other = (PoliticsCardsBonus) obj;
		if (politicsCardsAmount != other.politicsCardsAmount)
			return false;
		return true;
	}

}
