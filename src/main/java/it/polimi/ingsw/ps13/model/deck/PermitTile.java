package it.polimi.ingsw.ps13.model.deck;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * A PermitTile is used to build an Emporium on a City,
 * selected among the List of cities on the PermitTile.
 *
 * Whenever a Player obtains a PermitTile, he/she will be
 * granted the corresponding Bonus.
 * 
 * This class is immutable.
 * 
 */
public final class PermitTile implements Marketable, Serializable {
	
	private static final long serialVersionUID = 0L;
	private final Bonus bonus;
	private final Set<String> cityNames;
	private boolean used;
	
	
	/**
	 * Constructs a new PermitTile, with a given Bonus and a given Collection of city names.
	 * 
	 * @param bonus
	 * @param cityNames
	 */
	protected PermitTile(Bonus bonus, Set<String> cityNames) {
		
		this.bonus = bonus;
		
		this.cityNames = new TreeSet<>();
		this.cityNames.addAll(cityNames);
		
		this.used = false;
		
	}
	
	/**
	 * 
	 * @return the bonus associated with the permit tile
	 */
	public Bonus getBonus() {
		
		return bonus;
		
	}
	
	/**
	 * 
	 * @return the list of cities associated with the permit tile
	 */
	public Set<String> getCityNames() {
		
		return Collections.unmodifiableSet(cityNames);
		
	}
	
	/**
	 * 
	 * @return true if the permit tile has already been used
	 */
	public boolean isUsed() {
		
		return used;
		
	}
	
	/**
	 * 
	 */
	public void setUsed(boolean used) {
		
		if (this.used && !used) {
			throw new IllegalArgumentException("An used permit tile cannot be reset to unused");
		} else {
			this.used = used;
		}
		
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	public void giveTo(Player player) {
		
		player.receivePermitTile(this);
		// remember: when this method is called, the tile has already been removed
		// from the seller's hand. Only need to put the reference in the buyer's hand.
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nCITIES: ").append(cityNames.toString()).append("\n");
		sb.append("BONUS: ").append(bonus.toString());
		
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PermitTile that = (PermitTile) o;

		if (used != that.used)
			return false;
		if (!bonus.equals(that.bonus))
			return false;
		return cityNames.equals(that.cityNames);

	}

	@Override
	public int hashCode() {
		int result = bonus.hashCode();
		result = 31 * result + cityNames.hashCode();
		result = 31 * result + (used ? 1 :0);
		return result;
	}
}
