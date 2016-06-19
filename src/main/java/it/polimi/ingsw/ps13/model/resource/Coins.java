package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents any non negative amount of coins in the game.
 * It is used in bonuses and in player supply. 
 *
 */
public class Coins extends Resource implements Serializable {

	private static final long serialVersionUID = 0L;

	/**
	 * Constructs a new coins object with the specified initial amount.
	 * 
	 * @param amount the initial amount of the coins object
	 */
	public Coins(int amount){
		
		super(amount);
		
	}
		
	
	@Override
	public void giveTo(Player player) {
		
		player.addCoins(getAmount());
		
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		
		return "Cx" + amount;
		
	}
	
}
