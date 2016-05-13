package it.polimi.ingsw.ps13.model;

import java.io.Serializable;

/**
 * This class encapsulates the entire status of a game.
 * 
 * It's Serializable so that it can be easily sent over a network:
 * for example the Server broadcasts a new updated GameState whenever
 * a Player successfully performs an Action.
 * 
 */

public class GameState implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private GameState() { }
	
}
