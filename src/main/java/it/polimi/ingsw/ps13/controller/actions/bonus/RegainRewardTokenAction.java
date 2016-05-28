package it.polimi.ingsw.ps13.controller.actions.bonus;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.bonus.NobilityPointsBonus;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class RegainRewardTokenAction implements Action {

	private static final long serialVersionUID = 0L;

	private final Player player;
	private final City city; //The city whose token wants to be acquired again
	
	public RegainRewardTokenAction(Player player, City city) {
		
		this.player = player;
		this.city = city;
		
	}
	
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		if(city.getBonus() instanceof NobilityPointsBonus)
			legal = false;
		
		if(!player.hasBuiltOn(city.getName()))
			legal = false;
		
		return legal;
		
	}

	@Override
	public void apply(Game g) {
		
		city.getBonus().giveTo(player);
		
	}
	
}