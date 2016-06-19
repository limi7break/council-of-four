package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * A Bonus is given to a player upon the completion of certain tasks,
 * for example building an emporium on a city or obtaining a permit tile.
 *
 */
@FunctionalInterface
public interface Bonus extends Serializable {

	/**
	 * Gives the bonus to the player.
	 * 
	 * @param player the player who receives the bonus
	 */
	abstract void giveTo(Player player);
	
	/**
	 * Default implementation of the isEmpty method always returns false.
	 * 
	 * @return
	 */
	default boolean isEmpty() {
		return false;
	}
	
}
