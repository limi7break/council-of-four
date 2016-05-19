package it.polimi.ingsw.ps13.model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.council.CouncillorBalconyFactory;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeckFactory;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;
import it.polimi.ingsw.ps13.model.region.CityColorFactory;
import it.polimi.ingsw.ps13.model.region.Region;
import it.polimi.ingsw.ps13.model.region.RegionFactory;

/**
 * 
 * @author irons
 *
 */
public class BoardFactory {

	public static final int COUNCILLORS_PER_COLOR = 4;
	
	private BoardFactory() { }
	
	public static Board createBoard(Document config) {
		
		Map<String, CityColor> cityColors = CityColorFactory.createCityColors(config);
		Map<String, Region> regions = new HashMap<>();
		Map<String, City> cities;
		
		PoliticsCardDeck politicsCardDeck = PoliticsCardDeckFactory.createPoliticsCardDeck(config);
		
		Element regionsElement = (Element) config.getElementsByTagName("regions").item(0);
		NodeList regionElementList = regionsElement.getElementsByTagName("region");
		
		int numberOfRegions = regionElementList.getLength();
		List<Councillor> councillors;
		List<CouncillorBalcony> councillorBalconies = new ArrayList<>();
		councillors = CouncillorBalconyFactory.createCouncillorBalconies(numberOfRegions+1, councillorBalconies, config);
		
		King king = new King();
		cities = RegionFactory.createCities(regions, cityColors, councillorBalconies, king, config);
		
		return Board.create(regions, cityColors, cities, politicsCardDeck, councillorBalconies.remove(0), councillors, king);
		
	}
	
	
	
}
