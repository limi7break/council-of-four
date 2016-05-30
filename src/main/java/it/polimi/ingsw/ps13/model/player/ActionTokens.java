package it.polimi.ingsw.ps13.model.player;

import java.io.Serializable;

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
	public int getSell() {
		
		return sell;
		
	}
	
	/**
	 * 
	 * @param sell
	 */
	public void setSell(int sell) {
		
		this.sell = sell;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBuy() {
		
		return buy;
		
	}
	
	/**
	 * 
	 * @param buy
	 */
	public void setBuy(int buy) {
		
		this.buy = buy;
		
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
		sell = 0;
		buy = 0;
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
		sell = 0;
		buy = 0;
		tileBonus = 0;
		rewardToken = 0;
		takeTile = 0;
		
	}
	
	/**
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
	 * 
	 */
	@Override
	public String toString() {
		
		return main + " Main " + quick + " Quick " + sell + " Sell " + buy + " Buy " + tileBonus + " TileBonus " + rewardToken + " RewTok " + takeTile + " GetVisibleTile";
		
	}
	
}
