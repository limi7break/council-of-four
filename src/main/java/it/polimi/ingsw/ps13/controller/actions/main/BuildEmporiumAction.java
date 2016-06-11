package it.polimi.ingsw.ps13.controller.actions.main;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

public class BuildEmporiumAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	private final int tile;
	private final String city;
	
	/**
	 * 
	 * @param player
	 * @param emporium
	 * @param tile
	 * @param city
	 */
	public BuildEmporiumAction(String playerName, int tile, String city) {
		
		this.playerName = playerName;
		this.tile = tile;
		this.city = capitalizeFirstLetter(city);
		
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isLegal(Game g) {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getMain() == 0)
			legal = false;
		
		//Check if player has at least one emporium
		if(player.getNumberOfEmporiums() == 0)
			legal = false;
		
		// Check if city is a valid city
		if (!g.getBoard().getCities().containsKey(city))
			return false;
		
		// Check if tile is a valid permit tile number
		if ( (tile > player.getPermitTiles().size()-1)
			|| (tile < 0) )
			return false;
		
		// Check if player has already built on the city
		if(player.hasBuiltOn(city))
			legal = false;
		
		PermitTile permitTile = player.getPermitTiles().get(tile);
		
		// Check if the city and tile match
		if(!permitTile.getCityNames().contains(city))
			legal = false;
		
		// Check if player has enough assistants (one for every emporium already built on the city)
		if(player.getAssistants() < g.getBoard().getCity(city).getNumberOfEmporiums())
			legal = false;
		
		return legal;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		City realCity = g.getBoard().getCity(city);
		player.consumeAssistants(realCity.getNumberOfEmporiums());
		
		PermitTile permitTile = player.getPermitTiles().get(tile);
		permitTile.setUsed(true);
		
		Emporium emporium = player.removeEmporium();
		realCity.addEmporium(emporium);
		player.addCity(city);
		
		realCity.giveBonuses(player);
		
		// Give the player the region bonus, if he has completed the region and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getRegion().isBonusAvailable() && player.getCityNames().containsAll(realCity.getRegion().getCityNames())) {
			realCity.getRegion().setBonusAvailable(false);
			realCity.getRegion().getBonus().giveTo(player);
			g.getBoard().getKingRewardTile().giveTo(player);
		}
		
		// Give the player the color bonus, if he has completed the color and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getCityColor().isBonusAvailable() && player.getCityNames().containsAll(realCity.getCityColor().getCityNames())) {
			realCity.getCityColor().setBonusAvailable(false);
			realCity.getCityColor().getBonus().giveTo(player);
			g.getBoard().getKingRewardTile().giveTo(player);
		}
		
		if ( (player.getNumberOfEmporiums() == 0) && (g.getPlayerWhoBuiltLastEmporium() == -1) ) {
			g.setPlayerWhoBuiltLastEmporium(player.getID());
			player.addVictoryPoints(3);
		}
		
		player.consumeMainAction();
		
	}
	
	private String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
}
