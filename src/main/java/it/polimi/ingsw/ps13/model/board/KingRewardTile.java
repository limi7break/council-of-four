package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

/**
 * This class represents a king reward tile.
 * The first available king reward tile is gained by a player whenever that player gains
 * a region bonus or a city color bonus. 
 *
 */
public class KingRewardTile implements Serializable {	

	private static final long serialVersionUID = 0L;
	
	private final Bonus bonus;
	private boolean available;
	
	protected KingRewardTile(Bonus bonus) {
		
		this.bonus = bonus;
		available = true;
		
	}
	
	/**
	 * Returns the bonus contained in the king reward tile.
	 * 
	 * @return the bonus contained in the king reward tile
	 */
	public Bonus getBonus() {
		
		return bonus;
		
	}
	
	/**
	 * Returns true if the king reward tile is available.
	 * 
	 * @return true if the king reward tile is available
	 */
	public boolean isAvailable() {
		
		return available;
		
	}
	
	/**
	 * Sets the availability for the king reward tile.
	 * The king reward tile is available by default upon instantiation, and once its availability
	 * has been set to false it cannot be reset to true.
	 * 
	 * @param isAvailable false for making the king reward tile not available
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
