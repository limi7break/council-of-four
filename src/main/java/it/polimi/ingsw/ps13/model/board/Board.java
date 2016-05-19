package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps13.model.city.City;
import it.polimi.ingsw.ps13.model.city.CityColor;
import it.polimi.ingsw.ps13.model.city.Region;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;

public class Board implements Serializable {

	private static final long serialVersionUID = 0L;
	private final Map<String, Region> regions;
	private final Map<String, CityColor> cityColors;
	private final Map<String, City> cities;
	//private final NobilityTrack nobilityTrack;		// created in the constructor
	private final PoliticsCardDeck politicsCardDeck;	// created in the constructor
	//private final King king;							// created here
	private final CouncillorBalcony kingBalcony;		// created here
	//private final Deque<Bonus> kingRewardTiles;
	private final List<Councillor> councillors;			// modifiable
	
	private Board(Map<String, Region> regions, Map<String, CityColor> cityColors, Map<String, City> cities, PoliticsCardDeck politicsCardDeck, CouncillorBalcony kingBalcony, List<Councillor> councillors) {
		
		this.regions = regions;
		this.cityColors = cityColors;
		this.cities = cities;
		this.politicsCardDeck = politicsCardDeck;
		this.kingBalcony = kingBalcony;
		this.councillors = councillors;
		
		// create nobility track
		// create king
		// create king reward tiles
		
	}
	
	public static Board create(Map<String, Region> regions, Map<String, CityColor> cityColors, Map<String, City> cities, PoliticsCardDeck politicsCardDeck, CouncillorBalcony kingBalcony, List<Councillor> councillors) {
		
		return new Board(regions, cityColors, cities, politicsCardDeck, kingBalcony, councillors);
		
	}
	
	// === GETTER METHODS ===
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public City getCity(String name) {
		
		return cities.get(name);
		
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Region getRegion(String name) {
		
		return regions.get(name);
		
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public CityColor getCityColor(String name) {
		
		return cityColors.get(name);
		
	}
	
	/**
	 * 
	 * @return
	 *
	public NobilityTrack getNobilityTrack() {
		
		return nobilityTrack;
		
	} IMPLEMENT */
	
	/**
	 * 
	 * @return
	 */
	public PoliticsCardDeck getPoliticsCardDeck() {
		
		return politicsCardDeck;
		
	}
	
	/**
	 * 
	 * @return
	 *
	public King getKing() {
		
		return king;
		
	} IMPLEMENT */
	
	/**
	 * 
	 * @return
	 */
	public CouncillorBalcony getKingBalcony() {
		
		return kingBalcony;
		
	}
	
	/**
	 * Gets a king reward tile from the top of the list and removes it.
	 * 
	 * @return
	 *
	public Bonus getKingRewardTile() {
		
		return kingRewardTiles.removeFirst();
		
	} IMPLEMENT */
	
	/**
	 * 
	 * @return
	 */
	public List<Councillor> getCouncillors() {
		
		return councillors;
		
	}
	
}
