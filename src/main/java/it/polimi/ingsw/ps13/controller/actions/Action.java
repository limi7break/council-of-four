package it.polimi.ingsw.ps13.controller.actions;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.Game;

/**
 * This class represents an atomic Action (like a command pattern) to be
 * executed on the server. It is possible to check if a generic Action is valid
 * and to execute it.
 * 
 */
public interface Action extends Serializable {
	
	/**
	 * Check if the action is valid, according to the rules of the game.
	 * 
	 * @param g the state of the game
	 * @return true if the action is valid
	 * @throws IllegalActionException if the action is not valid
	 */
	public abstract boolean isLegal(Game g) throws IllegalActionException;
	
	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 * @param g the state of the game
	 */
	public abstract void apply(Game g);
	
}
