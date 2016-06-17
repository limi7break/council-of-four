package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.bonus.NobilityPointsBonus;
import it.polimi.ingsw.ps13.model.player.Player;

public class RegainRewardTokenAction implements Action {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	private final String city;
	
	public RegainRewardTokenAction(String playerName, String city) {
		
		this.playerName = playerName;
		this.city = capitalizeFirstLetter(city);
		
	}
	
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