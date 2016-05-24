package it.polimi.ingsw.ps13.controller.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.Game;

public class PassTurnAction implements Action, Serializable {

	private static final long serialVersionUID = 0L;

	public boolean isLegal(Game g) {
		
		return true;
		
	}
	
	public void apply(Game g) {
		
		
		
	}
	
}
