package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 *
 */
public class Player implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private final String name;
	private final Color color;
	private final PlayerSupply supply;
	private int nobilityPosition;
	private int mainActions;
	private boolean quickAction;
	private final Set<String> cityNames;
	
	public Player(String name, Color color, int position) { 
		
		this.name = name;
		this.color = color;
		supply = new PlayerSupply(position);
		
		nobilityPosition = 0;
		mainActions = 0;
		quickAction = false;
		cityNames = new TreeSet<>();
		
	}
	
	//getter and setter
	
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
	 * @return
	 */
	public int getMainActions() {
		
		return mainActions;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public PlayerSupply getSupply() {
		
		return supply;
		
	}
	
	/**
	 * 
	 * @param mainActions
	 */
	public void setMainActions(int mainActions) {
		
		this.mainActions = mainActions;
		
	}
	
	/**
	 * 
	 * @param quickAction
	 */
	public void setQuickAction(boolean quickAction) {
		
		this.quickAction = quickAction;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isQuickAction() {
		
		return quickAction;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNobilityPosition() {
		
		return nobilityPosition;
		
	}
	
	/**
	 * 
	 * @param nobilityPosition
	 */
	public void setNobilityPosition(int nobilityPosition) {
		
		this.nobilityPosition = nobilityPosition;
		
	}
	
	/**
	 * 
	 * @param city the city to check whether the player has built on
	 * @return true if the player has built an emporium on that city
	 */
	public boolean hasBuiltOn(String cityName) {
		
		return cityNames.contains(cityName);
		
	}
	
}
