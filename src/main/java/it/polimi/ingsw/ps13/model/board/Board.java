package it.polimi.ingsw.ps13.model.board;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;
import it.polimi.ingsw.ps13.model.region.Region;

/**
 * This class represents the game board.
 * It constitutes a large part of the whole game model, the remaining data is stored
 * in the Game class, for example the market, the player data, and information relative
 * to current turn/phase of the game. 
 *
 */
public class Board implements Serializable {

	private static final long serialVersionUID = 0L;
	private static final int PRICE_FOR_KING_STEP = 2;
	private final Map<String, Region> regions;
	private final Map<String, CityColor> cityColors;
	private final Map<String, City> cities;
	private final King king;
	private final NobilityTrack nobilityTrack;
	private final PoliticsCardDeck politicsCardDeck;
	private final CouncillorBalcony kingBalcony;
	private final List<KingRewardTile> kingRewardTiles;
	private final List<Councillor> councillors;

	/**
	 * Creates a new Board setting all the created objects passed as parameters.
	 * The Board constructor creates the King and sets it onto the capital (which is the only city with no bonus),
	 * and also creates the nobility track and the king reward tiles. 
	 * 
	 * @param regions
	 * @param cityColors
	 * @param cities
	 * @param politicsCardDeck
	 * @param kingBalcony
	 * @param councillors
	 * @param config
	 */
	public Board(Map<String, Region> regions, Map<String, CityColor> cityColors, Map<String, City> cities, PoliticsCardDeck politicsCardDeck, CouncillorBalcony kingBalcony, List<Councillor> councillors, Document config) {
		
		this.regions = regions;
		this.cityColors = cityColors;
		this.cities = cities;
		this.politicsCardDeck = politicsCardDeck;
		this.kingBalcony = kingBalcony;
		this.councillors = councillors;
		this.king = new King();
		
		// Set king city
		for (City city : cities.values()) {
			if (city.getBonus().isEmpty()) {
				king.setCity(city);
			}
		}
		
		// Create nobility track
		nobilityTrack = createNobilityTrack(config);
		
		// Create king reward tiles
		kingRewardTiles = createKingRewardTiles(config);
		
	}
	
	// === GETTER METHODS ===

	/**
	 * Returns a region object by name.
	 * 
	 * @param name the name of the region
	 * @return the region object with the desired name
	 */
	public Region getRegion(String name) {
		
		return regions.get(name);
		
	}
	
	/**
	 * Returns a map view of the regions.
	 * 
	 * @return a map view of the regions
	 */
	public Map<String, Region> getRegions() {
		
		return Collections.unmodifiableMap(regions);
		
	}
	
	/**
	 * Returns a city color object by name.
	 * 
	 * @param name the name of the city color
	 * @return the city color object with the desired name
	 */
	public CityColor getCityColor(String name) {
		
		return cityColors.get(name);
		
	}
	
	/**
	 * Returns a map view of the city colors.
	 * 
	 * @return a map view of the city colors
	 */
	public Map<String, CityColor> getCityColors() {
		
		return Collections.unmodifiableMap(cityColors);
		
	}
	
	/**
	 * Returns a city object by name.
	 * 
	 * @param name the name of the city
	 * @return the city object with the desired name
	 */
	public City getCity(String name) {
		
		return cities.get(name);
		
	}
	
	/**
	 * Returns a map view of the cities.
	 * 
	 * @return a map view of the cities
	 */
	public Map<String, City> getCities() {
		
		return Collections.unmodifiableMap(cities);
		
	}
	
	/**
	 * Returns the king object.
	 * 
	 * @return the king object
	 */
	public King getKing() {
		
		return king;
		
	}
	
	/**
	 * Returns the nobility track.
	 * 
	 * @return the nobility track
	 */
	public NobilityTrack getNobilityTrack() {
		
		return nobilityTrack;
		
	}
	
	/**
	 * Returns the politics cards deck.
	 * 
	 * @return the politics cards deck
	 */
	public PoliticsCardDeck getPoliticsCardDeck() {
		
		return politicsCardDeck;
		
	}
	
	/**
	 * Returns the king's councillor balcony.
	 * 
	 * @return the king's councillor balcony
	 */
	public CouncillorBalcony getKingBalcony() {
		
		return kingBalcony;
		
	}
	
	/**
	 * Returns a list view of the king reward tiles.
	 * 
	 * @return a list view of the king reward tiles
	 */
	public List<KingRewardTile> getKingRewardTiles() {
		
		return kingRewardTiles;
		
	}
	
	/**
	 * Gets the first available king reward tile.
	 * If the list of king reward tiles is empty, returns null.
	 * 
	 * @return the first available king reward tile, null if no one is available
	 */
	public KingRewardTile getNextAvailableKingRewardTile() {
		
		for (KingRewardTile krt : kingRewardTiles) {
			if (krt.isAvailable())
				return krt;
		}
		
		return null;
		
	}
	
	/**
	 * Returns a list view of the available councillors on the board.
	 * 
	 * @return a list view of the available councillors on the board
	 */
	public List<Councillor> getCouncillors() {
		
		return councillors;
		
	}
	
	/**
	 * Creates the king reward tiles from the configuration document.
	 * 
	 * @param config the configuration document
	 * @return a list of the imported king reward tiles
	 */
	private List<KingRewardTile> createKingRewardTiles(Document config) {
		
		List<KingRewardTile> rewardTiles = new ArrayList<>();
		
		Element kingRewardTilesElement = (Element) config.getElementsByTagName("kingrewardtiles").item(0);
		NodeList bonusElementList = kingRewardTilesElement.getElementsByTagName("bonus");
		for (int i=0; i<bonusElementList.getLength(); i++) {
			Element currentBonusElement = (Element) bonusElementList.item(i);
			rewardTiles.add(new KingRewardTile(BonusFactory.createBonus(currentBonusElement)));
		}
		
		return rewardTiles;
		
	}

	/**
	 * Creates the nobility track from the configuration document.
	 * 
	 * @param config the configuration document
	 * @return the imported nobility track
	 */
	private NobilityTrack createNobilityTrack(Document config) {
		
		TreeMap<Integer, Bonus> bonusMap = new TreeMap<>();
		
		Element nobilityTrackElement = (Element) config.getElementsByTagName("nobilitytrack").item(0);
		NodeList positionElementList = nobilityTrackElement.getElementsByTagName("position");
		for (int i=0; i<positionElementList.getLength(); i++) {
			Element currentElement = (Element) positionElementList.item(i);
			int position = Integer.parseInt(currentElement.getAttribute("value"));
			Element bonusElement = (Element) currentElement.getElementsByTagName("bonus").item(0);
			Bonus bonus = BonusFactory.createBonus(bonusElement);
			
			bonusMap.put(position, bonus);
		}
		
		for (int i=0; i<bonusMap.lastKey(); i++) {
			if (!bonusMap.keySet().contains(i)) {
				bonusMap.put(i, BonusFactory.createEmptyBonus());
			}
		}
		
		return new NobilityTrack(bonusMap);
		
	}
	
	/**
	 * Inserts a councillor among the available ones on the board.
	 * 
	 * @param councillor the councillor to insert among the available ones on the board
	 */
	public void insertCouncillor(Councillor councillor){
		
		councillors.add(councillor);
		
	}
	
	/**
	 * Removes and returns the first councillor with the given color belonging to the free councillors of the board.
	 * If there are none, returns null.
	 * 
	 * @param color the color of the councillor
	 * @return the councillor of the selected color, null if no councillor of the selected color is available
	 */
	public Councillor getCouncillor(Color color) {
		
		Iterator<Councillor> it = councillors.iterator();
		
		while(it.hasNext()) {
			Councillor current = it.next();
			
			if(current.getColor().equals(color)) {
				it.remove();
				return current;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Returns true if a councillor of the given color is available on the board to be used.
	 * 
	 * @param color the color of the councillor
	 * @return true if a councillor of the selected color is available on the board to be used
	 */
	public boolean isCouncillorAvailable(Color color){
		
		for(Councillor c : councillors){
			
			if(c.getColor().equals(color)) 
				return true;

		}
		
		return false;
		
	}
	
	/**
	 * Calculates the shortest path between the two selected cities.
	 * 
	 * @param start the starting city
	 * @param destination the destination city
	 * @return how many steps are necessary to go from the starting city to the destination city
	 */
	private int calculateDistance(City start, City destination) {
		
		Map<City, AtomicInteger> distances = new HashMap<>();
		
		for (City c : cities.values()) {
			distances.put(c, new AtomicInteger(-1));
		}
		
		Queue<City> queue = new LinkedList<>();
		
		distances.get(start).set(0);
		queue.add(start);
		
		while (!queue.isEmpty()) {
			City current = queue.remove();
			
			for (City neighbor : current.getNeighbors()) {
				if (distances.get(neighbor).intValue() == -1) {
					distances.get(neighbor).set(distances.get(current).intValue()+1);
					queue.add(neighbor);
				}
			}
		}
		
		return distances.get(destination).intValue();
		
	}
	
	/**
	 * Returns how many coins are necessary to move the king from the current city to the specified destination city.
	 * 
	 * @param city the destination city
	 * @return how many coins are necessary to move the king from the current city to the specified destination city
	 */
	public int priceToMoveKing(City destination) {
		
		return PRICE_FOR_KING_STEP*calculateDistance(king.getCity(), destination);
		
	}
	
	/**
	 * Sets a new king city.
	 * 
	 * @param c the new king city
	 */
	public void setKingCity(City c) {
		
		king.setCity(c);
		
	}
	
}