package it.polimi.ingsw.ps13.model.board;

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

	public static Board create(Map<String, Region> regions, Map<String, CityColor> cityColors, Map<String, City> cities, PoliticsCardDeck politicsCardDeck, CouncillorBalcony kingBalcony, List<Councillor> councillors, Document config) {
		
		return new Board(regions, cityColors, cities, politicsCardDeck, kingBalcony, councillors, config);
		
	}
	
	// === GETTER METHODS ===

	public Region getRegion(String name) {
		
		return regions.get(name);
		
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Map<String, Region> getRegions() {
		
		return Collections.unmodifiableMap(regions);
		
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
	 * @param name
	 * @return
	 */
	public Map<String, CityColor> getCityColors() {
		
		return Collections.unmodifiableMap(cityColors);
		
	}
	
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
	public Map<String, City> getCities() {
		
		return Collections.unmodifiableMap(cities);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public King getKing() {
		
		return king;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public NobilityTrack getNobilityTrack() {
		
		return nobilityTrack;
		
	}
	
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
	 */
	public CouncillorBalcony getKingBalcony() {
		
		return kingBalcony;
		
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public List<KingRewardTile> getKingRewardTiles() {
		
		return kingRewardTiles;
		
	}
	
	/**
	 * Gets the first available king reward tile.
	 * If the list of king reward tiles is empty, returns null.
	 * 
	 * @return
	 */
	public KingRewardTile getNextKingRewardTile() {
		
		for (KingRewardTile krt : kingRewardTiles) {
			if (krt.isAvailable())
				return krt;
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Councillor> getCouncillors() {
		
		return councillors;
		
	}
	
	/**
	 * 
	 * @param config
	 * @return
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
	 * 
	 * @param config
	 * @return
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
	 * 
	 * @param councillor
	 */
	public void insertCouncillor(Councillor councillor){
		
		councillors.add(councillor);
		
	}
	
	/**
	 * Removes and returns a councillor from the free councillor list.
	 * 
	 * @param councillor
	 * @return
	 */
	public Councillor removeCouncillor(Councillor councillor){
		
		Councillor removed = null;
		
		Iterator<Councillor> it = councillors.iterator();
		
		while(it.hasNext() && removed == null){
			
			if(it.next().equals(councillor)){
				
				removed = it.next();
				councillors.remove(it.next());
				
			}
			
		}
		
		return removed;
		
	}
	
	/**
	 * 
	 * @param start
	 * @param destination
	 * @return
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
	 * 
	 * @param city
	 * @return
	 */
	public int priceToMoveKing(City destination) {
		
		return PRICE_FOR_KING_STEP*calculateDistance(king.getCity(), destination);
		
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setKingCity(City c) {
		
		king.setCity(c);
		
	}
	
}