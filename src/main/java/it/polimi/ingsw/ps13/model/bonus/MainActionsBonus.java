package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents a main actions bonus.
 *
 */
public class MainActionsBonus implements Bonus, Serializable {

	private static final long serialVersionUID = 0L;
	private final int mainActionsAmount;
	
	/**
	 * Creates a new MainActionsBonus with the specified initial amount.
	 * 
	 * @param amount the initial amount of the main actions bonus
	 */
	public MainActionsBonus(int amount){
		
		this.mainActionsAmount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.addMainActions(mainActionsAmount);
		
	}
	
	/**
	 * Returns the amount of this main actions bonus.
	 * 
	 * @return the amount of this main actions bonus.
	 */
	public int getAmount() {
		
		return mainActionsAmount;
		
	}
	
	/**
	 * Returns true if the amount of the main actions bonus is zero.
	 * 
	 * @return true if the amount of the main actions bonus is zero
	 */
	@Override
	public boolean isEmpty() {
		
		return mainActionsAmount == 0;
		
	}
	
	/**
	 * Used for Command Line Interface (CLI).
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
