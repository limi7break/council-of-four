package it.polimi.ingsw.ps13.model.player;

import java.io.Serializable;

/**
 * This class represents the actions available to a player.
 * Action tokens are given to a player and consumed just like other resources.
 * The amount of action tokens is the first check to be performed in the legality
 * check of every action.
 *
 */
public class ActionTokens implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private int main;
	private int quick;
	private int sell;
	private int buy;
	private int tileBonus;
	private int rewardToken;
	private int takeTile;
	
	protected ActionTokens() {
		
		main = 0;
		quick = 0;
		
	}
	
	/**
	 * Returns the amount of main action tokens.
	 * 
	 * @return the amount of main action tokens
	 */
	public int getMain() {
		
		return main;
		
	}
	
	/**
	 * Sets the amount of main action tokens.
	 * 
	 * @param main the amount of main action tokens to set
	 */
	public void setMain(int main) {
		
		this.main = main;
		
	}
	
	/**
	 * Returns the amount of quick action tokens.
	 * 
	 * @return the amount of quick action tokens
	 */
	public int getQuick() {
		
		return quick;
		
	}
	
	/**
	 * Sets the amount of quick action tokens.
	 * 
	 * @param quick the amount of quick action tokens to set
	 */
	public void setQuick(int quick) {
		
		this.quick = quick;
		
	}
	
	/**
	 * Returns the amount of sell action tokens.
	 * 
	 * @return the amount of sell action tokens
	 */
	public int getSell() {
		
		return sell;
		
	}
	
	/**
	 * Sets the amount of sell action tokens.
	 * 
	 * @param sell the amount of sell action tokens to set
	 */
	public void setSell(int sell) {
		
		this.sell = sell;
		
	}
	
	/**
	 * Returns the amount of buy action tokens.
	 * 
	 * @return the amount of buy action tokens
	 */
	public int getBuy() {
		
		return buy;
		
	}
	
	/**
	 * Sets the amount of buy action tokens.
	 * 
	 * @param buy the amount of buy action tokens to set
	 */
	public void setBuy(int buy) {
		
		this.buy = buy;
		
	}
	
	/**
	 * Returns the amount of "get rt" action tokens.
	 * 
	 * @return the amount of "get rt" action tokens
	 */
	public int getRewardToken() {
		
		return rewardToken;
		
	}
	
	/**
	 * Sets the amount of "get rt" action tokens.
	 * 
	 * @param rewardToken the amount of "get rt" action tokens to set
	 */
	public void setRewardToken(int rewardToken) {
		
		this.rewardToken = rewardToken;
		
	}
	
	/**
	 * Returns the amount of "get tile" action tokens.
	 * 
	 * @return the amount of "get tile" action tokens
	 */
	public int getTakeTile() {
		
		return takeTile;
		
	}
	
	/**
	 * Sets the amount of "get tile" action tokens.
	 * 
	 * @param takeTile the amount of "get tile" action tokens to set
	 */
	public void setTakeTile(int takeTile) {
		
		this.takeTile = takeTile;
		
	}
	
	/**
	 * Returns the amount of "get tb" action tokens.
	 * 
	 * @return the amount of "get tb" action tokens
	 */
	public int getTileBonus() {
		
		return tileBonus;
		
	}
	
	/**
	 * Sets the amount of "get tb" action tokens.
	 * 
	 * @param tileBonus the amount of "get tb" action tokens to set
	 */
	public void setTileBonus(int tileBonus) {
		
		this.tileBonus = tileBonus;
		
	}
	
	/**
	 * Sets all the action tokens to zero.
	 * 
	 */
	public void setZeros(){
		
		main = 0;
		quick = 0;
		sell = 0;
		buy = 0;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
	/**
	 * Sets all the action tokens to the initial configuration for a new turn in the normal game phase
	 * (1 main action token, 1 quick action token).
	 * 
	 */
	public void setInitial(){
		
		main = 1;
		quick = 1;
		sell = 0;
		buy = 0;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
	/**
	 * Sets all the action tokens to the initial configuration for a new turn in the sell market phase
	 * (1 sell action token).
	 * 
	 */
	public void setSell() {
		
		main = 0;
		quick = 0;
		sell = 1;
		buy = 0;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
	/**
	 * Sets all the action tokens to the initial configuration for a new turn in the buy market game phase
	 * (1 buy action token).
	 * 
	 */
	public void setBuy() {
		
		main = 0;
		quick = 0;
		sell = 0;
		buy = 1;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
	/**
	 * Returns true if the player has zero action tokens.
	 * 
	 * @return true if the player has zero action tokens
	 */
	public boolean isEmpty() {
		
		return main==0 && quick==0 && sell==0 && buy==0 && tileBonus==0 && rewardToken==0 && takeTile==0;
		
	}
	
	/**
	 * Used for Command Line Interface (CLI).
	 * 
	 */
	@Override
	public String toString() {
		
		return main + " Main " + quick + " Quick " + sell + " sell " + buy + " buy " + tileBonus + " get tb " + rewardToken + " get rt " + takeTile + " get tile";
		
	}
	
}
