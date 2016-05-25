package it.polimi.ingsw.ps13.controller.actions;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

public class PassTurnAction implements Action {

	private static final long serialVersionUID = 0L;
	
	public PassTurnAction(Player player) {

	
	}

	@Override
	public boolean isLegal(Game g) {
		
		return true;
		
	}
	
	@Override
	public void apply(Game g) {
		
		//TODO
		
	}
	
}
