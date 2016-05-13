package it.polimi.ingsw.ps13.model.deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.city.City;

import java.io.Serializable;

/**
 * A PermitTile is used to build an Emporium on a City,
 * selected among the List of cities on the PermitTile.
 *
 * Whenever a Player obtains a PermitTile, he/she will be
 * granted the corresponding Bonus.
 * 
 * After a PermitTile has been used once, it is turned face down
 * and can't be used anymore by the same Player.
 * 
 */
public class PermitTile implements Serializable {
	
	private static final long serialVersionUID = 0L;
	private boolean isFacingUp;
	private final Bonus bonus;
	private final List<City> cityList;
	
	/**
	 * Constructs a new PermitTile, with a given Bonus
	 * and a given Collection of cities.
	 * 
	 * @param bonus
	 * @param cities
	 */
	public PermitTile(Bonus bonus, Collection<City> cities) {
		
		// a new PermitTile is facing down by default
		isFacingUp = false;
		
		this.bonus = bonus;
		
		cityList = new ArrayList<>();
		cityList.addAll(cities);
		
	}
	
	/**
	 * 
	 * @return true if the permit tile is facing up
	 */
	public boolean isFacingUp() {
		
		return isFacingUp;
		
	}
	
	/**
	 * Turns the permit tile upside down, based on the current status.
	 */
	public void flip() {
		
		isFacingUp = !isFacingUp;
		
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
	public List<City> getCityList() {
		
		return cityList;
		
	}
}
