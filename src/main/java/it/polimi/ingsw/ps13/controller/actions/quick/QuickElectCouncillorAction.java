package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class QuickElectCouncillorAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player; 
	private final Councillor councillor;
	private final Region region; 
	
	/**
	 * Current player and isQuickActionAvailable are to checked.
	 * 
	 * @param player
	 * @param councillor
	 */
	public QuickElectCouncillorAction(Player player, Councillor councillor, Region region) {
		
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
		
		//checks if conditions are satisfied
		if(player.getAssistants() < 1 || !g.isCouncillorAvailable(councillor))
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
		
		player.consumeAssistants(1);
		
		g.getBoard().removeCouncillor(councillor);
		
		CouncillorBalcony balcony = region.getCouncillorBalcony();
		
		Councillor droppedCouncillor = balcony.insertCouncillor(councillor);
		
		g.getBoard().insertCouncillor(droppedCouncillor);
		
	}	
	
}
