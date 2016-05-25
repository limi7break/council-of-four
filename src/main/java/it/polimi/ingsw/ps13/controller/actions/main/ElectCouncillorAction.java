package it.polimi.ingsw.ps13.controller.actions.main;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.Region;

public class ElectCouncillorAction implements Action{

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final Councillor councillor;
	private final Region region; //whose council is to be satisfied by player

	/**
	 * As with all actions, if current player is player and if player has main actions available has to be checked.
	 * 
	 * @param player
	 * @param councillor
	 * @param region
	 * @param cards
	 * @param tile
	 */
	public ElectCouncillorAction(Player player, Councillor councillor, Region region) {
		
		this.player = player;
		this.councillor = councillor;
		this.region = region;
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		if(!g.getBoard().getCouncillors().contains(councillor))
			legal = false;
		
		return legal;
		
	}

	@Override
	public void apply(Game g) {
		
		Councillor removed = region.getCouncillorBalcony().insertCouncillor(councillor);
		g.getBoard().insertCouncillor(removed);
		
		player.addCoins(4);
	}

	
	
}
