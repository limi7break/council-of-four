package it.polimi.ingsw.ps13.model;

import java.io.Serializable;

/**
 * A Bonus is given to a Player upon the completion of certain tasks:
 * (for example building an Emporium on a City or obtaining a PermitTile).
 * 
 * It consists of any combination of Victory Points,
 * Coins, Assistants, Politics Cards, and Nobility Points.
 *
 */
public class Bonus implements Serializable {

	private static final long serialVersionUID = 0L;
	private final int victoryPoints;
	private final int coins;
	private final int assistants;
	private final int politicsCards;
	private final int nobilityPoints;
	
	
	/**
	 * 
	 * Constructs a Bonus with the given parameters.
	 */
	public Bonus(int victoryPoints, int coins, int assistants, int politicsCards, int nobilityPoints) {
		
		this.victoryPoints = victoryPoints;
		this.coins = coins;
		this.assistants = assistants;
		this.politicsCards = politicsCards;
		this.nobilityPoints = nobilityPoints;
		
	}
	
	/**
	 * 
	 * @return the number of victory points given by the bonus
	 */
	public int getVictoryPoints() {
		
		return victoryPoints;
		
	}
	
	/**
	 * 
	 * @return the number of coins given by the bonus
	 */
	public int getCoins() {
		
		return coins;
		
	}

	/**
	 * 
	 * @return the number of assistants given by the bonus
	 */
	public int getAssistants() {
	
		return assistants;
		
	}

	/**
	 * 
	 * @return the number of politics cards given by the bonus
	 */
	public int getPoliticsCards() {
	
		return politicsCards;
		
	}

	/**
	 * 
	 * @return the number of nobility points given by the bonus
	 */
	public int getNobilityPoints() {
	
		return nobilityPoints;
		
	}
}
