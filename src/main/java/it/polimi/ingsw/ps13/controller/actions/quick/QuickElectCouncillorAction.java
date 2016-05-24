package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;

public class QuickElectCouncillorAction implements Action {

	private static final long serialVersionUID = 0L;

	private final int player; 
	private final Councillor councillor;
	private final String region; 
	
	/**
	 * 
	 * @param player
	 * @param councillor
	 */
	public QuickElectCouncillorAction(int player, Councillor councillor, String region) {
		
		this.player = player;
		this.councillor = councillor;
		this.region = region;
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
		if(g.getPlayers().get(player).getAssistants() < 1 || !g.isCouncillorAvailable(councillor))
			legal = false;
		
		return legal;
	}

	/**
	 * Elects the councillor in the region balcony and puts the dropped councillor into the board councillors.
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		g.getBoard().removeCouncillor(councillor);
		
		CouncillorBalcony balcony = g.getBoard().getRegion(region).getCouncillorBalcony();
		
		Councillor droppedCouncillor = balcony.insertCouncillor(councillor);
		
		g.getBoard().insertCouncillor(droppedCouncillor);
		
		g.getPlayers().get(player).consumeAssistants(1);
		
	}	
	
}
