package it.polimi.ingsw.ps13.model.region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

public final class RegionFactory {

	private RegionFactory() { }
	
	public static Map<String, City> createCities(Map<String, Region> regions, List<CouncillorBalcony> councillorBalconies, Map<String, CityColor> cityColors, Document config) {
		
		Map<String, City> cities = new HashMap<>();
		
		List<Bonus> rewardTokens = createRewardTokens(config);
		
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
			
			NodeList cityElementList = currentRegion.getElementsByTagName("city");
			for (int j=0; j<cityElementList.getLength(); j++) {
				Element currentCity = (Element) cityElementList.item(j); 
				String cityName = currentCity.getAttribute("name");
				CityColor cityColor = cityColors.get(currentCity.getAttribute("color"));
				
				cities.put(cityName, new City(cityName, region, cityColor, rewardTokens.remove(0)));
				region.addCityName(cityName);
				cityColors.get(cityColor).addCityName(cityName);
			}
			
		}
		
		return cities;
		
		
	}
	
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
