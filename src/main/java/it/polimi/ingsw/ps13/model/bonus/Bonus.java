package it.polimi.ingsw.ps13.model.bonus;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * A Bonus is given to a player upon the completion of certain tasks:
 * (for example building an emporium on a city or obtaining a permit tile).
 *
 */
@FunctionalInterface
public interface Bonus extends Serializable {

	abstract void giveTo(Player player);
	
}
