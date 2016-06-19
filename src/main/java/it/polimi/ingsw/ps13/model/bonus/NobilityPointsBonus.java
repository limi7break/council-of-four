package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.board.NobilityTrack;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents a nobility points bonus.
 *
 */
public class NobilityPointsBonus implements Bonus, Serializable {
	
	private static final long serialVersionUID = 0L;
	private final int nobilityPointsAmount;
	
	/**
	 * Creates a new NobilityPointsBonus with the specified initial amount.
	 * 
	 * @param amount the initial amount of the nobility points bonus
	 */
	public NobilityPointsBonus(int amount){
		
		this.nobilityPointsAmount = amount;
		
	}
	
	/**
	 * The first position of the nobility track is supposed to have no bonus. If so, then should be 
	 * part of the setup configuration.
	 * 
	 */
	@Override
	public void giveTo(Player player) {
		
		NobilityTrack nobilityTrack = player.getBoard().getNobilityTrack();
		
		for(int i=0; i<nobilityPointsAmount; i++){
			
			player.nobilityAdvance();
			
			Bonus bonus = nobilityTrack.getBonus(player.getNobilityPosition());
			bonus.giveTo(player);
			
		}
		
	}
	
	/**
	 * Returns the amount of this nobility points bonus.
	 * 
	 * @return the amount of this nobility points bonus.
	 */
	public int getAmount() {
		
		return nobilityPointsAmount;
		
	}
	
	/**
	 * Returns true if the amount of the nobility points bonus is zero.
	 * 
	 * @return true if the amount of the nobility points bonus is zero
	 */
	@Override
	public boolean isEmpty() {
		
		return nobilityPointsAmount == 0;
		
	}
	
	/**
	 * Used for Command Line Interface (CLI).
	 * 
	 */
	@Override
	public String toString() {
		
		return "NPx" + nobilityPointsAmount;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nobilityPointsAmount;
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
		NobilityPointsBonus other = (NobilityPointsBonus) obj;
		if (nobilityPointsAmount != other.nobilityPointsAmount)
			return false;
		return true;
	}


}
