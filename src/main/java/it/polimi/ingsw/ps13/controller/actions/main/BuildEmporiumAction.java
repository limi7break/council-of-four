package it.polimi.ingsw.ps13.controller.actions.main;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class BuildEmporiumAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final Player player;
	private final PermitTile tile;
	private final City city;
	
	/**
	 * 
	 * @param player
	 * @param emporium
	 * @param tile
	 * @param city
	 */
	public BuildEmporiumAction(Player player, PermitTile tile, City city) {
		
		this.player = player;
		this.tile = tile;
		this.city = city;
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		
		//Checks if player has a not used tile with the city
		if(!player.canBuildOn(city.getName()))
			legal = false;
		
		//Checks if the city and tile match
		if(!tile.getCityNames().contains(city.getName()))
			legal = false;
		
		if(player.hasBuiltOn(city.getName()))
			legal = false;
		
		return legal;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		tile.setUsed(true);
		Emporium emporium = player.removeEmporium();
		
		city.addEmporium(emporium);
		
	}
	
}
