package it.polimi.ingsw.ps13.model.market;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This interface is implemented by objects which a player can trade for coins
 * during the market phase.
 *
 */
@FunctionalInterface
public interface Marketable extends Serializable {

	/**
	 * Gives the marketable item to the player.
	 * 
	 * @param player the player who receives the marketable item
	 */
	abstract void giveTo(Player player);
	
}