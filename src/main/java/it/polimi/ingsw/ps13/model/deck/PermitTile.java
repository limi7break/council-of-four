package it.polimi.ingsw.ps13.model.deck;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.city.City;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.market.Marketable;

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
	private final Set<City> citySet;
	
	/**
	 * Constructs a new PermitTile, with a given Bonus and a given Collection of cities.
	 * 
	 * @param bonus
	 * @param cities
	 */
	PermitTile(Bonus bonus, Set<City> cities) {
		
		this.bonus = bonus;
		
		citySet = new HashSet<>();
		citySet.addAll(cities);
		
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
	public Set<City> getCitySet() {
		
		return Collections.unmodifiableSet(citySet);
		
	}
	
	/**
	 * 
	 * 
	 */
	public void giveTo(Player player) {
		
		// @TODO: implement this method
		// remember: when this method is called, the tile has already been removed
		// from the seller's hand. Only need to put the reference in the buyer's hand.
		
	}
}
