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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((bonus == null) ? 0 : bonus.hashCode());
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
		KingRewardTile other = (KingRewardTile) obj;
		if (available != other.available)
			return false;
		if (bonus == null) {
			if (other.bonus != null)
				return false;
		} else if (!bonus.equals(other.bonus))
			return false;
		return true;
	}
	
}
