package it.polimi.ingsw.ps13.model.city;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import it.polimi.ingsw.ps13.model.bonus.*;

/**
 * This class represents the material of the cities. A material is identified
 * with a name and a color.
 * 
 * The bonus relative to each material can be obtained only once.
 */
public class CityMaterial implements Serializable {

	private static final long serialVersionUID = 0L;
	private final String name;
	private final Color color;
	private final List<City> cityList;	// list of cities made of this material.
	private final Bonus bonus;
	private boolean bonusAvailable;
	
	/**
	 * Creates a CityMaterial object.
	 * 
	 * The field bonusAvailable is set to true by default.
	 * The bonus will be granted to the first player who builds an emporium on every city
	 * made of a certain material, and the flag will be set to false.
	 * 
	 */
	public CityMaterial(String name, Color color, Bonus bonus){
		
		this.name = name;
		this.color = color;
		
		cityList = new ArrayList<>();
		
		this.bonus = bonus;
		bonusAvailable = true;
		
	}
	
	/**
	 * 
	 * @return the name of this material.
	 */
	public String getName(){
		
		return name;
		
	}
	
	/**
	 * 
	 * @return the color associated with this material.
	 */
	public Color getColor(){
		
		return color;
		
	}
	
	/**
	 * 
	 * @return the number of cities which have this material.
	 */
	public List<City> getListOfCities(){
		
		return cityList;
		
	}
	
	/**
	 * 
	 * @return bonus given to the player who builds
	 */
	public Bonus getBonus(){
		
		return bonus;
		
	}
	
	/**
	 * 
	 * @return true if the bonus tile of this material can still be acquired.
	 */
	public boolean isBonusAvailable(){
		
		return bonusAvailable;
		
	}
	
}
