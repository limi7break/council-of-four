package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.bonus.NobilityPointsBonus;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * The ability to perform this action is gained through a special type of bonus.
 * The player who gains the bonus and can perform this special action, must do so before
 * passing the turn.
 * 
 * This action gives the player the bonus of the city that has been selected,
 * if an emporium belonging to the player is present on the selected city.
 *
 */
public class RegainRewardTokenAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String city;
	
	/**
	 * Creates a new RegainRewardTokenAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param city the name of the city whose bonus has to be regained by the player
	 */
	public RegainRewardTokenAction(String playerName, String city) {
		
		this.playerName = playerName;
		this.city = capitalizeFirstLetter(city);
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Selected city is valid
	 * 		- Player has built an emporium on selected city
	 * 		- Selected city bonus does not contain a nobility points bonus
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (g.getPlayer(playerName).getTokens().getRewardToken() == 0)
			throw new IllegalActionException("Action is not available");
		
		// Check if city is a valid city
		if (!g.getBoard().getCities().containsKey(city))
			throw new IllegalActionException("Selected city is not valid");
		
		// Check if player has built an emporium on the city
		if(!player.hasBuiltOn(city))
			throw new IllegalActionException("You haven\'t built an emporium on selected city");
		
		// Check if the city's bonus contains a nobility points bonus
		ConcreteBonus bonus = (ConcreteBonus) g.getBoard().getCity(city).getBonus();
		for (Bonus b : bonus.getContents()) {
			if (b instanceof NobilityPointsBonus)
				throw new IllegalActionException("Selected city bonus contains a nobility bonus");
		}
		
		return true;
		
	}

	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		
		g.getBoard().getCity(city).getBonus().giveTo(player);
		player.consumeRewardTokenToken();
		
	}
	
	private String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
}