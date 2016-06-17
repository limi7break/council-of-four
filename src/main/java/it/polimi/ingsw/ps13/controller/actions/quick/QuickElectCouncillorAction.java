package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.player.Player;

public class QuickElectCouncillorAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName; 
	private final String region;
	private final String color; 
	
	/**
	 * Current player and isQuickActionAvailable are to checked.
	 * 
	 * @param player
	 * @param councillor
	 */
	public QuickElectCouncillorAction(String playerName, String region, String color) {
		
		this.playerName = playerName;
		this.region = region;
		this.color = color;
		
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getQuick() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if region is a valid region
		if (!g.getBoard().getRegions().containsKey(region) && !"king".equals(region))
			throw new IllegalActionException("Selected region is not valid");
		
		// Check if color is a valid color
		if (!g.getColors().containsKey(color))
			throw new IllegalActionException("Selected color is not valid");
		
		// Check if player has at least 1 assistant 
		if(player.getAssistants() < 1)
			throw new IllegalActionException("Not enough assistants, 1 required");
		
		// Check if a councillor with the desired color is available
		if (!g.isCouncillorAvailable(g.getColors().get(color)))
			throw new IllegalActionException("Selected councillor is not available");
		
		return legal;
	}

	/**
	 * Elects the councillor in the region balcony and puts the dropped councillor into the board councillors.
	 * 
	 * @param g
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.consumeAssistants(1);
		
		Councillor chosen = g.getCouncillor(g.getColors().get(color));
		
		CouncillorBalcony balcony;
		if ("king".equals(region)) {
			balcony = g.getBoard().getKingBalcony();
		} else {
			balcony = g.getBoard().getRegion(region).getCouncillorBalcony();
		}
		Councillor toBeDiscarded = balcony.insertCouncillor(chosen);
		g.getBoard().insertCouncillor(toBeDiscarded);
		
		player.consumeQuickAction();
		
	}	
	
}
