package it.polimi.ingsw.ps13.model.region;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

/**
 * This class represents the color of the cities.
 * The bonus relative to each color can be obtained only once.
 */
public class CityColor implements Serializable {

	private static final long serialVersionUID = 0L;
	private final Color color;
	private final String colorName;
	private final Set<String> cityNames;
	private final Bonus bonus;
	private boolean bonusAvailable;
	
	/**
	 * Creates a CityColor object.
	 * 
	 * The field bonusAvailable is set to true by default.
	 * The bonus will be granted to the first player who builds an emporium on every city
	 * of a certain color, and the flag will be set to false.
	 * 
	 */
	public CityColor (Color color, String colorName, Bonus bonus){
		
		this.color = color;
		this.colorName = colorName;
		this.bonus = bonus;
		
		if (bonus.isEmpty()) {
			bonusAvailable = false;
		} else {
			bonusAvailable = true;
		}
		
		cityNames = new TreeSet<>();
		
	}
	
	/**
	 * 
	 * @return the color of this object
	 */
	public Color getColor(){
		
		return color;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getColorName() {
		
		return colorName;
		
	}
	
	/**
	 * 
	 * @return the bonus associated to this color
	 */
	public Bonus getBonus(){
		
		return bonus;
		
	}
	
	/**
	 * 
	 * @return the cities of this color
	 */
	public Set<String> getCityNames(){
		
		return Collections.unmodifiableSet(cityNames);
		
	}
	
	/**
	 * 
	 * @param city
	 */
	void addCityName(String name) {
		
		cityNames.add(name);
		
	}
	
	/**
	 * 
	 * @return true if the bonus tile of this color can still be acquired.
	 */
	public boolean isBonusAvailable(){
		
		return bonusAvailable;
		
	}
	
	/**
	 * 
	 */
	public void setBonusAvailable(boolean colorBonusAvailable) {
		
		if (!this.bonusAvailable && colorBonusAvailable) {
			throw new IllegalArgumentException("Bonus relative to a city color cannot be reset to true");
		} else {
			this.bonusAvailable = colorBonusAvailable;
		}
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n[CityColor]\n")
		  .append("COLOR: ").append(colorName).append("\n")
		  .append("CITIES: ").append(cityNames.toString()).append("\n")
		  .append("BONUS: ").append(bonus.toString())
		  .append("\navailable = ").append(bonusAvailable).append("\n");
		
		return sb.toString();
		
	}
	
}
