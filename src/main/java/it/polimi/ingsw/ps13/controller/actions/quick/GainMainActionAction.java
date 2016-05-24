package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;

public class GainMainActionAction implements Action {

	private static final long serialVersionUID = 0L;

	private final int player;
	
	/**
	 * 
	 * @param player
	 */
	public GainMainActionAction(int player) {
		
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
		
		//checks if it's the player's turn
		if(g.getCurrentPlayerID() != player)
			legal = false;
		
		//checks if player can do a quick action
		if(!g.getPlayers().get(player).isQuickActionAvailable())
			legal = false; 
		
		//checks if conditions are satisfied
		if(g.getPlayers().get(player).getAssistants() < 3)
			legal = false; 
		
		return legal;
		
	}

	/**
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		g.getPlayers().get(player).consumeAssistants(3);
		g.getPlayers().get(player).addMainActions(1);
		
	}
	
}
