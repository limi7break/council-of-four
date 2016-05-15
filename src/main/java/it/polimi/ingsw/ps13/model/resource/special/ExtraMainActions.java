package it.polimi.ingsw.ps13.model.resource.special;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

public class ExtraMainActions extends SpecialResource implements Serializable {

	private static final long serialVersionUID = 0L;
	private final int amount;
	
	public ExtraMainActions(int amount){
		
		super();
		this.amount = amount;
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		player.setMainActions(player.getMainActions() + amount);
		
	}
	
	

}
