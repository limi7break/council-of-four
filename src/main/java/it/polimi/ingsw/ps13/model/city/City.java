package it.polimi.ingsw.ps13.model.city;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import it.polimi.ingsw.ps13.model.board.Emporium;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * 
 *
 */
public class City implements Serializable {

	private static final long serialVersionUID = 0L;
	private final String name;
	private final CityMaterial material;
	private final Bonus bonus;
	private final List<Emporium> emporiumList;
	private final List<City> neighborList;
	
	/**
	 * 
	 */
	public City(String name, CityMaterial material, Bonus bonus) { 
		
		this.name = name;
		this.material = material;
		this.bonus = bonus;
		emporiumList = new ArrayList<>();
		neighborList = new ArrayList<>();
		
	}
	
	/**
	 * 
	 * @return the name of the city
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * 
	 * @return the material of which the city is made
	 */
	public CityMaterial getMaterial() {
		
		return material;
		
	}
	
	/**
	 * 
	 * @return the list of cities adjacent to the city
	 */
	public List<City> getNeighborList() {
		
		return neighborList;
		
	}
	
	/**
	 * Adds a neighbor to the list.
	 * 
	 * @param city
	 */
	public void addNeighbor(City city) {
		
		neighborList.add(city);
		
	}
	
	/**
	 * 
	 * @return the list of emporiums that have been built on the city
	 */
	public List<Emporium> getEmporiumList() {
		
		return emporiumList;
		
	}
	
	/**
	 * Adds an emporium to the list.
	 * 
	 * @param emporium
	 */
	public void addEmporium(Emporium emporium) {
		
		emporiumList.add(emporium);
		
	}
	
	/**
	 * Entry point for the getBonuses recursive method.
	 * It creates an empty set, which will be used to keep track of
	 * visited cities, and passes it to the method with the appropriate
	 * signature.
	 * 
	 * @param player the player to give the bonuses to
	 */
	public void giveBonuses(Player player) {
		
		Set<City> visited = new HashSet<>();
		this.giveBonuses(player, visited);
		
	}
	
	/**
	 * This method is called when a player builds an emporium on a city.
	 * 
	 * It is called recursively on the cities in the neighbor list, only
	 * if the player has built an emporium on that city.
	 * Else, the recursion is stopped.
	 * 
	 * @param player
	 */
	private void giveBonuses(Player player, Set<City> visited) {
		
		visited.add(this);
		
		// If the recursion has gotten to this city, it means it's connected
		// to the previous city AND the player has already built an emporium on it.
		// So, the bonus of this city is given to the player.
		bonus.giveTo(player);
		
		for (City neighbor : neighborList) {
			
			if ( !(visited.contains(neighbor)) && (player.hasBuiltOn(neighbor)) ) {
				neighbor.giveBonuses(player, visited);
			}
			
		}
		
	}
	
}
