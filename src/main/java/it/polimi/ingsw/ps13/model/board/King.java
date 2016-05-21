package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.region.City;

/**
 * 
 *
 */
public class King implements Serializable {

	private static final long serialVersionUID = 0L;
	private City city;
	
	/**
	 * 
	 * @param c
	 */
	protected King() { }
	
	/**
	 * 
	 * @return
	 */
	public City getCity(){
		
		return city;
		
	}
	
	/**
	 * 
	 * @param c
	 */
	protected void setCity(City c){
		
		city = c;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return "[King]\n"
				+ "King is on " + city.getName() + "\n\n";
		
	}
	
}
