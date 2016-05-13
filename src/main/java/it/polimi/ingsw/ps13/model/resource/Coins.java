package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.Player;

/**
 *
 *
 */
public class Coins extends Resource implements Serializable {

	private static final long serialVersionUID = 0L;

	public Coins(int amount){
		
		super(amount);
		
	}
		
	
	@Override
	public void giveTo(Player player) {
		
		// @TODO: implement this method
		
	}

}
