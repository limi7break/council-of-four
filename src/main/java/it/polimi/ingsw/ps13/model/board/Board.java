package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	private final Map<String, Region> regions;
	private final Map<String, CityColor> cityColors;
	private final Map<String, City> cities;
	private final King king;
	private final NobilityTrack nobilityTrack;
	private final PoliticsCardDeck politicsCardDeck;
	private final CouncillorBalcony kingBalcony;
	private final Deque<Bonus> kingRewardTiles;
	private final List<Councillor> councillors;
	
	private Board(Map<String, Region> regions, Map<String, CityColor> cityColors, Map<String, City> cities, PoliticsCardDeck politicsCardDeck, CouncillorBalcony kingBalcony, List<Councillor> councillors, Document config) {
		
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
	 * Gets a king reward tile from the top of the list and removes it.
	 * 
	 * @return
	 */
	public Bonus getKingRewardTile() {
		
		return kingRewardTiles.removeFirst();
		
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
	private Deque<Bonus> createKingRewardTiles(Document config) {
		
		Deque<Bonus> rewardTiles = new LinkedList<>();
		
		Element kingRewardTilesElement = (Element) config.getElementsByTagName("kingrewardtiles").item(0);
		NodeList bonusElementList = kingRewardTilesElement.getElementsByTagName("bonus");
		for (int i=0; i<bonusElementList.getLength(); i++) {
			Element currentBonusElement = (Element) bonusElementList.item(i);
			rewardTiles.addLast(BonusFactory.createBonus(currentBonusElement));
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
	 * @param city
	 * @return
	 */
	public int priceToMoveKing(City city) {
		
		City kingCity = king.getCity();
		int price = 0;
		
		while(!kingCity.equals(city)){
			
			//TODO: implement this method
			
			price = price + 2;
		}
		
		return price;
		
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setKingCity(City c) {
		
		king.setCity(c);
		
	}
	
}
