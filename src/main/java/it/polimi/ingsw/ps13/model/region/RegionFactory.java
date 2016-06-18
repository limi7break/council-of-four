package it.polimi.ingsw.ps13.model.region;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeckFactory;

/**
 * 
 *
 */
public final class RegionFactory {

	private RegionFactory() { }
	
	public static Map<String, City> createCities(Map<String, Region> regions, Map<String, CityColor> cityColors, List<CouncillorBalcony> councillorBalconies, Map<String, Color> colors, Document config) {
		
		Map<String, City> cities = new LinkedHashMap<>();
		List<Bonus> rewardTokens = createRewardTokens(config);
		
		// Create capital color with empty bonus
		Element capitalColorElement = (Element) ((Element) config.getElementsByTagName("citycolors").item(0)).getElementsByTagName("capitalcolor").item(0);
		String capitalColorName = capitalColorElement.getAttribute("name");
		CityColor capitalColor = new CityColor(colors.get(capitalColorName), capitalColorName, BonusFactory.createEmptyBonus());
		cityColors.put(capitalColorName, capitalColor);
		
		Element regionsElement = (Element) config.getElementsByTagName("regions").item(0);
		NodeList regionElementList = regionsElement.getElementsByTagName("region");
		
		for (int i=0; i<regionElementList.getLength(); i++) {
			Element currentRegion = (Element) regionElementList.item(i);
			String regionName = currentRegion.getAttribute("name");
			Bonus regionBonus = BonusFactory.createBonus((Element)currentRegion.getElementsByTagName("bonus").item(0));
			
			NodeList permitTileElementList = currentRegion.getElementsByTagName("permittile");
			PermitTileDeck regionPermitTileDeck = PermitTileDeckFactory.createPermitTileDeck(permitTileElementList);
			
			Region region = new Region(regionName, regionBonus, councillorBalconies.remove(0), regionPermitTileDeck);
			regions.put(regionName, region);
			
			Element citiesElement = (Element) currentRegion.getElementsByTagName("cities").item(0);
			NodeList cityElementList = citiesElement.getElementsByTagName("city");
			for (int j=0; j<cityElementList.getLength(); j++) {
				Element currentCity = (Element) cityElementList.item(j); 
				String cityName = currentCity.getAttribute("name");
				String cityColorName = currentCity.getAttribute("color");
				CityColor cityColor = cityColors.get(cityColorName);
				
				if (cityColorName.equals(capitalColorName)) {
					City capitalCity = new City(cityName, region, cityColor, BonusFactory.createEmptyBonus());
					cities.put(cityName, capitalCity);
				} else {
					cities.put(cityName, new City(cityName, region, cityColor, rewardTokens.remove(0)));
				}
				
				region.addCityName(cityName);
				cityColor.addCityName(cityName);
			}
		}
		
		// Now that every city is created, add neighbors
		NodeList cityElementList = regionsElement.getElementsByTagName("city");
		for (int i=0; i<cityElementList.getLength(); i++) {
			Element currentCity = (Element) cityElementList.item(i);
			String currentCityName = currentCity.getAttribute("name");
			
			NodeList neighborElementList = currentCity.getElementsByTagName("neighbor");
			for (int j=0; j<neighborElementList.getLength(); j++) {
				Element currentNeighbor = (Element) neighborElementList.item(j);
				String currentNeighborName = currentNeighbor.getAttribute("name");
				
				cities.get(currentCityName).addNeighbor(cities.get(currentNeighborName));
			}
		}
		
		return cities;
		
		
	}
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	private static List<Bonus> createRewardTokens(Document config) {
		
		List<Bonus> rewardTokens = new ArrayList<>();
		
		Element rewardTokensElement = (Element) config.getElementsByTagName("rewardtokens").item(0);
		NodeList rewardTokensNodeList = rewardTokensElement.getElementsByTagName("bonus");
		for (int i=0; i<rewardTokensNodeList.getLength(); i++) {
			Element currentBonusElement = (Element) rewardTokensNodeList.item(i);
			rewardTokens.add(BonusFactory.createBonus(currentBonusElement));
		}
		
		Collections.shuffle(rewardTokens);
		
		return rewardTokens;
		
	}
	
}
