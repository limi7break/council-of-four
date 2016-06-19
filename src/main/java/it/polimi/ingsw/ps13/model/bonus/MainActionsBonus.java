package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

public class MainActionsBonus implements Bonus, Serializable {

	private static final long serialVersionUID = 0L;
	private final int mainActionsAmount;
	
	public MainActionsBonus(int amount){
		
		this.mainActionsAmount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.addMainActions(mainActionsAmount);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAmount() {
		
		return mainActionsAmount;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isEmpty() {
		
		return mainActionsAmount == 0;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "MAx" + mainActionsAmount;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mainActionsAmount;
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
		MainActionsBonus other = (MainActionsBonus) obj;
		if (mainActionsAmount != other.mainActionsAmount)
			return false;
		return true;
	}
}
