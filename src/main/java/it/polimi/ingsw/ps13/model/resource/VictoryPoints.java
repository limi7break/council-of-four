package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents any non negative amount of victory points in the game.
 * It is used in bonuses and in player supply. 
 *
 */
public class VictoryPoints extends Resource implements Serializable {

	private static final long serialVersionUID = 0L;

	/**
	 * Constructs a new victory points object with the specified initial amount.
	 * 
	 * @param amount the initial amount of the victory points object
	 */
	public VictoryPoints(int amount){
		
		super(amount);
		
	}

	@Override
	public void giveTo(Player player) {
		
		player.addVictoryPoints(getAmount());
		
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		
		return "VPx" + amount;
		
	}
	
}
