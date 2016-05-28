package it.polimi.ingsw.ps13.model.player;

import java.io.Serializable;

public class ActionTokens implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private int main;
	private int quick;
	private int tileBonus;
	private int rewardToken;
	private int takeTile;
	
	protected ActionTokens() {
		
		main = 1;
		quick = 1;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMain() {
		
		return main;
		
	}
	
	/**
	 * 
	 * @param main
	 */
	public void setMain(int main) {
		
		this.main = main;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getQuick() {
		
		return quick;
		
	}
	
	/**
	 * 
	 * @param quick
	 */
	public void setQuick(int quick) {
		
		this.quick = quick;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRewardToken() {
		
		return rewardToken;
		
	}
	
	/**
	 * 
	 * @param rewardToken
	 */
	public void setRewardToken(int rewardToken) {
		
		this.rewardToken = rewardToken;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTakeTile() {
		
		return takeTile;
		
	}
	
	/**
	 * 
	 * @param takeTile
	 */
	public void setTakeTile(int takeTile) {
		
		this.takeTile = takeTile;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTileBonus() {
		
		return tileBonus;
		
	}
	
	/**
	 * 
	 * @param tileBonus
	 */
	public void setTileBonus(int tileBonus) {
		
		this.tileBonus = tileBonus;
		
	}
	
	/**
	 * 
	 */
	public void setZeros(){
		
		main = 0;
		quick = 0;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
	/**
	 * 
	 */
	public void setInitial(){
		
		main = 1;
		quick = 1;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
}
