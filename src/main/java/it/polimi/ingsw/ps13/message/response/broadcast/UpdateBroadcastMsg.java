package it.polimi.ingsw.ps13.message.response.broadcast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.model.Game;

/**
 * This message contains an updated version of the model. It is broadcasted to every client
 * in a game when a player performs an action that is successfully validated by the game controller.
 * When a client receives this message, it updates the local model with the updated version.
 *
 */
public class UpdateBroadcastMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final Game game;
	
	public UpdateBroadcastMsg(String message, Game game) {
		
		super(message);
		this.game = game;
		
	}

	public Game getGame() {
		
		return game;
		
	}
	
}
