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
	
	/**
	 * Constructs a new PermitTile, with a given Bonus and a given Collection of city names.
	 * 
	 * @param bonus
	 * @param cities
	 */
	protected PermitTile(Bonus bonus, Set<String> cityNames) {
		
		this.bonus = bonus;
		
		this.cityNames = new TreeSet<>();
		this.cityNames.addAll(cityNames);
		
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
	 * 
	 */
	@Override
	public void giveTo(Player player) {
		
		// @TODO: implement this method
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
		
		sb.append("[PermitTile]\n");
		sb.append("Cities: ").append(cityNames.toString()).append("\n");
		sb.append("Bonus:\n").append(bonus.toString()).append("\n");
		
		return sb.toString();
	}
}
