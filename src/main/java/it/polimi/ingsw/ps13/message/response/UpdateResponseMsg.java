package it.polimi.ingsw.ps13.message.response;

import it.polimi.ingsw.ps13.model.Game;

/**
 * This message contains an updated version of the model. It is broadcasted to every client
 * in a game when a player performs an action that is successfully validated by the game controller.
 * When a client receives this message, it updates the local model with the updated version.
 *
 */
public class UpdateResponseMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final Game game;
	
	/**
	 * Creates a new UpdateResponseMsg with the specified message and game state.
	 * 
	 * @param message the content of the message
	 * @param game the game state wrapped in the message
	 */
	public UpdateResponseMsg(String message, Game game) {
		
		super(message);
		this.game = game;
		
	}

	/**
	 * Returns the game state wrapped in the message.
	 * 
	 * @return the game state wrapped in the message
	 */
	public Game getGame() {
		
		return game;
		
	}
	
}
