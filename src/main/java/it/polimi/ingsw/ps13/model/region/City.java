package it.polimi.ingsw.ps13.model.region;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.awt.Color;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * 
 *
 */
public class City implements Serializable {

	private static final long serialVersionUID = 0L;
	private final String name;
	private final Region region;
	private final CityColor color;
	private final Bonus bonus;
	private final List<Emporium> emporiums;
	private final Set<City> neighbors;
	
	/**
	 * 
	 */
	public City(String name, Region region, CityColor color, Bonus bonus) {
		
		this.name = name;
		this.region = region;
		this.color = color;
		this.bonus = bonus;
		emporiums = new ArrayList<>();
		neighbors = new HashSet<>();
		
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
	 * @return the region of the city
	 */
	public Region getRegion() {
		
		return region;
		
	}
	
	/**
	 * 
	 * @return the color of the city
	 */
	public Color getColor() {
		
		return color.getColor();
		
	}
	
	/**
	 * 
	 * 	@return the CityColor of the city
	 */
	public CityColor getCityColor() {
		
		return color;
		
	}
	
	/**
	 * 
	 * @return the set of cities adjacent to this city
	 */
	public Set<City> getNeighbors() {
		
		return Collections.unmodifiableSet(neighbors);
		
	}
	
	/**
	 * Adds a neighbor to the set.
	 * This method is only called by RegionFactory during the initialization
	 * of the game from the XML configuration file.
	 * 
	 * @param city
	 */
	void addNeighbor(City city) {
		
		neighbors.add(city);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Emporium> getEmporiums() {
		
		return Collections.unmodifiableList(emporiums);
		
	}
	
	/**
	 * 
	 * @return the number of emporiums that have been built on the city
	 */
	public int getNumberOfEmporiums() {
		
		return emporiums.size();
		
	}
	
	/**
	 * Adds an emporium to the list.
	 * The player who owns the emporium is given the bonus (reward token) of this city,
	 * and the bonuses of every city connected to this on which the player has already
	 * built an emporium.
	 * 
	 * @param emporium
	 */
	public void addEmporium(Emporium emporium) {
		
		emporiums.add(emporium);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getBonus() {
		
		return bonus;
		
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
		
		for (City neighbor : neighbors) {
			
			if ( !(visited.contains(neighbor)) && (player.hasBuiltOn(neighbor.name)) ) {
				neighbor.giveBonuses(player, visited);
			}
			
		}
		
	}
	
	/**
	 * Used for Command Line Interface (CLI).
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n[City]\n")
			.append("NAME: ").append(name).append("\n")
			.append("REGION: ").append(region.getName()).append("\n")
			.append("COLOR: ").append(color.getColorName()).append("\n")
			.append("BONUS: ").append(bonus.toString()).append("\n")
			.append("EMPORIUMS: ").append(emporiums.size()).append("\n")
			.append("NEIGHBORS: ");
			
			for (City city : neighbors) {
				sb.append(city.getName()).append(" ");
			}
			
			sb.append("\n");
			return sb.toString();
	}
	
}
