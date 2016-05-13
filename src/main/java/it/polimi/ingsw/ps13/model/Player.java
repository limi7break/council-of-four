package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import it.polimi.ingsw.ps13.model.city.City;

/**
 * 
 *
 */
public class Player implements Serializable {

	public static final long serialVersionUID = 0L;
	private String name;
	private Color color;
	private final List<City> cityList;
	
	/**
	 * 
	 * @param name the name of the player
	 * @param color the color of the player
	 */
	public Player(String name, Color color) {
		
		this.name = name;
		this.color = color;
		
		cityList = new ArrayList<>();
		
	}
	
	/**
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * 
	 * @return the color of the player
	 */
	public Color getColor() {
		
		return color;
		
	}
	
	/**
	 * 
	 * @param city the city to check whether the player has built on
	 * @return true if the player has built an emporium on that city
	 */
	public boolean hasBuiltOn(City city) {
		
		return cityList.contains(city);
		
	}
	
	/**
	 * 
	 * @return the number of cities on which the player has built an emporium
	 */
	public int getNumberOfCities() {
		
		return cityList.size();
		
	}
	
}
