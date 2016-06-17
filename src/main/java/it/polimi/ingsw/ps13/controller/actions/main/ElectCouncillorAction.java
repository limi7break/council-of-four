package it.polimi.ingsw.ps13.controller.actions.main;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.player.Player;

public class ElectCouncillorAction implements Action{

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String region;
	private final String color;

	/**
	 * As with all actions, has to be checked if current player is player and if player has main actions available.
	 * 
	 * @param player
	 * @param councillor
	 * @param region
	 * @param cards
	 * @param tile
	 */
	public ElectCouncillorAction(String playerName, String region, String color) {
		
		this.playerName = playerName;
		this.color = color;
		this.region = region;
		
	}
	
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getMain() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region) && !"king".equals(region))
			throw new IllegalActionException("Selected region is not valid");
		
		// Check if color is a valid color
		if (!g.getColors().containsKey(color))
			throw new IllegalActionException("Selected color is not valid");
		
		// Check if a councillor with the desired color is available
		if (!g.isCouncillorAvailable(g.getColors().get(color)))
			throw new IllegalActionException("Selected councillor is not available");
		
		return true;
		
	}

	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		Councillor chosen = g.getCouncillor(g.getColors().get(color));
		
		CouncillorBalcony balcony;
		if ("king".equals(region)) {
			balcony = g.getBoard().getKingBalcony();
		} else {
			balcony = g.getBoard().getRegion(region).getCouncillorBalcony();
		}
		Councillor toBeDiscarded = balcony.insertCouncillor(chosen);
		g.getBoard().insertCouncillor(toBeDiscarded);
		
		
		player.addCoins(4);
		
		player.consumeMainAction();
		
	}

	
	
}
