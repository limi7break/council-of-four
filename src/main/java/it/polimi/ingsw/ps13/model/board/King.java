package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.city.City;

/**
 * 
 * @author alessiomongelluzzo
 *
 */
public class King implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private City city;
	
	/**
	 * 
	 * @param c
	 */
	public King(City c){
		
		city = c;
		
	}
	
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
	public void setCity(City c){
		
		city = c;
		
	}
	
}