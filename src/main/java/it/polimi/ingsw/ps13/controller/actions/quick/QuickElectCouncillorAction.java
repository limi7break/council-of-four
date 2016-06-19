package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This action is performed when the player wants to elect a councillor, taken from the
 * available councillors, in a balcony. The councillor occupying the last position of the
 * balcony is dropped and placed into the available councillors. The elected councillor
 * is placed into the first position of the balcony.
 * 
 * This is a quick action.
 *
 */
public class QuickElectCouncillorAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName; 
	private final String region;
	private final String color; 
	
	/**
	 * Creates a new QuickElectCouncillorAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param region the name of the region where the player wants to elect the councillor (can also be 'king')
	 * @param color the color of the councillor to be elected
	 */
	public QuickElectCouncillorAction(String playerName, String region, String color) {
		
		this.playerName = playerName;
		this.region = region;
		this.color = color;
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Selected region is valid
	 * 		- Selected color is valid
	 * 		- Player has enough assistants
	 * 		- Selected councillor is available 
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
		if (!g.getBoard().isCouncillorAvailable(g.getColors().get(color)))
			throw new IllegalActionException("Selected councillor is not available");
		
		return legal;
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		player.consumeAssistants(1);
		
		Councillor chosen = g.getBoard().getCouncillor(g.getColors().get(color));
		
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
