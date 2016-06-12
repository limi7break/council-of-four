package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

public class KingRewardTile implements Serializable {	

	private static final long serialVersionUID = 0L;
	
	private final Bonus bonus;
	private boolean available;
	
	protected KingRewardTile(Bonus bonus) {
		
		this.bonus = bonus;
		available = true;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getBonus() {
		
		return bonus;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAvailable() {
		
		return available;
		
	}
	
	/**
	 * 
	 * @param isAvailable
	 */
	public void setAvailable(boolean isAvailable) {
		
		if (!this.available && isAvailable) {
			throw new IllegalArgumentException("Bonus relative to a king reward tile cannot be reset to true");
		} else {
			this.available = isAvailable;
		}
		
	}
	
}
