package it.polimi.ingsw.ps13.controller.actions.main;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.KingRewardTile;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;

/**
 * This action is performed when the player wants to build an emporium on a city using
 * a previously acquired permit tile.
 * 
 * This is a main action.
 *
 */
public class BuildEmporiumAction implements Action {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	private final int tile;
	private final String city;
	
	/**
	 * Creates a new BuildEmporiumAction.
	 * 
	 * @param playerName unique identifier of the player wanting to perform the action
	 * @param tile the number of the tile in player's hand
	 * @param city the name of the city on which the player wants to build the emporium
	 */
	public BuildEmporiumAction(String playerName, int tile, String city) {
		
		this.playerName = playerName;
		this.tile = tile;
		this.city = city;
		
	}
	
	/**
	 * This action is legal if all these conditions are satisfied:
	 * 
	 * 		- Player has got the appropriate action token
	 * 		- Player has got at least one emporium
	 * 		- Selected city is valid
	 * 		- Selected permit tile number is valid
	 * 		- Player hasn't built on selected city yet
	 * 		- Selected permit tile does allow building on selected city
	 * 		- Player has enough assistants to build on selected city
	 */
	@Override
	public boolean isLegal(Game g) throws IllegalActionException {
		
		boolean legal = true;
		Player player = g.getPlayer(playerName);
		
		// Check if player has token
		if (player.getTokens().getMain() == 0)
			throw new IllegalActionException("Action is not available");
		
		//Check if player has at least one emporium
		if(player.getNumberOfEmporiums() == 0)
			throw new IllegalActionException("You have no emporiums left");
		
		// Check if city is a valid city
		if (!g.getBoard().getCities().containsKey(city))
			throw new IllegalActionException("Selected city is not valid");
		
		// Check if tile is a valid permit tile number
		if ( (tile > player.getPermitTiles().size()-1)
			|| (tile < 0) )
			throw new IllegalActionException("Selected permit tile is not valid");
		
		// Check if player has already built on the city
		if(player.hasBuiltOn(city))
			throw new IllegalActionException("You have already built on selected city");
		
		PermitTile permitTile = player.getPermitTiles().get(tile);
		
		// Check if the city and tile match
		if(!permitTile.getCityNames().contains(city))
			throw new IllegalActionException("Selected permit tile doesn\'t allow building on selected city");
		
		// Check if player has enough assistants (one for every emporium already built on the city)
		if(player.getAssistants() < g.getBoard().getCity(city).getNumberOfEmporiums())
			throw new IllegalActionException("Not enough assistants, " + g.getBoard().getCity(city).getNumberOfEmporiums() + " required");
		
		return legal;
		
	}
	
	/**
	 * Executes the action on the passed Game, effectively modifying it.
	 * 
	 */
	@Override
	public void apply(Game g) {
		
		Player player = g.getPlayer(playerName);
		City realCity = g.getBoard().getCity(city);
		player.consumeAssistants(realCity.getNumberOfEmporiums());
		
		PermitTile permitTile = player.getPermitTiles().get(tile);
		permitTile.setUsable(false);
		
		Emporium emporium = player.removeEmporium();
		realCity.addEmporium(emporium);
		player.addCity(city);
		
		realCity.giveBonuses(player);
		
		// Give the player the region bonus, if he has completed the region and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getRegion().isBonusAvailable() && player.getCityNames().containsAll(realCity.getRegion().getCityNames())) {
			realCity.getRegion().setBonusAvailable(false);
			realCity.getRegion().getBonus().giveTo(player);
			
			KingRewardTile krt = g.getBoard().getNextAvailableKingRewardTile();
			if (krt != null) {
				krt.getBonus().giveTo(player);
				krt.setAvailable(false);
			}
		}
		
		// Give the player the color bonus, if he has completed the color and if it's available
		// Also check if a king reward tile is available and give it to the player
		if (realCity.getCityColor().isBonusAvailable() && player.getCityNames().containsAll(realCity.getCityColor().getCityNames())) {
			realCity.getCityColor().setBonusAvailable(false);
			realCity.getCityColor().getBonus().giveTo(player);
			
			KingRewardTile krt = g.getBoard().getNextAvailableKingRewardTile();
			if (krt != null) {
				krt.getBonus().giveTo(player);
				krt.setAvailable(false);
			}
		}
		
		if ( (player.getNumberOfEmporiums() == 0) && (g.getPlayerWhoBuiltLastEmporium() == -1) ) {
			g.setPlayerWhoBuiltLastEmporium(player.getID());
			player.addVictoryPoints(3);
		}
		
		player.consumeMainAction();
		
	}
	
}
