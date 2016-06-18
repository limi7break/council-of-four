package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.board.NobilityTrack;
import it.polimi.ingsw.ps13.model.player.Player;

public class NobilityPointsBonus implements Bonus, Serializable {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
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
		if (amount != other.amount)
			return false;
		return true;
	}

	private static final long serialVersionUID = 0L;
	private final int amount;
	
	protected NobilityPointsBonus(int amount){
		
		this.amount = amount;
		
	}
	
	/**
	 * The first position of the nobility track is supposed to have no bonus. If so, then should be 
	 * part of the setup configuration.
	 * 
	 */
	@Override
	public void giveTo(Player player) {
		
		NobilityTrack nobilityTrack = player.getBoard().getNobilityTrack();
		
		for(int i=0; i<amount; i++){
			
			player.nobilityAdvance();
			
			Bonus bonus = nobilityTrack.getBonus(player.getNobilityPosition());
			bonus.giveTo(player);
			
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAmount() {
		
		return amount;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isEmpty() {
		
		return amount == 0;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "NPx" + amount;
		
	}

}
