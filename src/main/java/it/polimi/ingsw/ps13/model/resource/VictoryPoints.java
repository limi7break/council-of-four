package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * 
 *
 */
public class VictoryPoints extends Resource implements Serializable {

	private static final long serialVersionUID = 0L;

	public VictoryPoints(int amount){
		
		super(amount);
		
	}

	@Override
	public void giveTo(Player player) {
		
		player.getSupply().addVictoryPoints(getAmount());
		
	}
	

}
