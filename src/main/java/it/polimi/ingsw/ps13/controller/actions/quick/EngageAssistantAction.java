package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

public class EngageAssistantAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	
	/**
	 * 
	 * @param player
	 */
	public EngageAssistantAction(Player player) {
		
		this.player = player;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		//checks if conditions are satisfied
		if(player.getCoins() < 3)
			legal = false;
					
		return legal;
		
	}

	/**
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		player.consumeCoins(3);
		player.addAssistants(1);
		
	}
	
}
