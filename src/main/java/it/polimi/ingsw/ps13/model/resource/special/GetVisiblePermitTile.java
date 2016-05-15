package it.polimi.ingsw.ps13.model.resource.special;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.player.Player;

public class GetVisiblePermitTile extends SpecialResource implements Serializable{
	
	private static final long serialVersionUID = 0L;

	public GetVisiblePermitTile(){
		
		super();
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		// @TODO: notify view -> controller ?
		
	}

}
