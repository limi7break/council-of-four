package it.polimi.ingsw.ps13.controller;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.GameState;

/**
 * This class represents an atomic Action (like a command pattern) to be
 * executed on the server. It is possible to check if a generic Action is valid
 * and to execute it.
 * 
 */
public interface Action extends Serializable {
	
	/**
	 * Check if the atomic action is valid, according to the rules of the game.
	 * 
	 * @return true if the atomic action is valid
	 */
	public abstract boolean isLegal(GameState g);
	
	/**
	 * Executes the action on the passed GameState, effectively modifying it.
	 */
	public abstract void apply(GameState g);
	
}
