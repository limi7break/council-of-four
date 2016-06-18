package it.polimi.ingsw.ps13.model.region;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.model.ColorFactory;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.council.CouncillorBalconyFactory;

/**
 * Region factory class tested.
 *
 */
public class RegionFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Region> regions;
	Map<String, CityColor> cityColors;
	Map<String, Color> colors;
	List<CouncillorBalcony> balconies;
	
	Map<String, City> cities;
	List<Bonus> rewardTokens;
	
	@Before
	public void setUp() {
		
		try {
			File testFile = new File(testFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			config = dBuilder.parse(testFile);
			config.getDocumentElement().normalize();
		} catch(Exception e) {
			LOG.log(Level.SEVERE, "An error occured while reading the file.", e);
		}
		
		regions = new HashMap<>();
		colors = new HashMap<>();
		ColorFactory.createColors(config, colors);
		cityColors = CityColorFactory.createCityColors(config, colors);
		balconies = new ArrayList<>();
		CouncillorBalconyFactory.createCouncillorBalconies(4, balconies, colors, config);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	
	@Test
	public void createCities() throws Exception {
		
		cities = RegionFactory.createCities(regions, cityColors, balconies, colors, config);
		
		Element regionsElement = (Element) config.getElementsByTagName("regions").item(0);
		NodeList regionElementList = regionsElement.getElementsByTagName("region");
		
		//All region names in the file
		List<String> fileRegions = new ArrayList<>();
		for (int i=0; i<regionElementList.getLength(); i++) {
			Element currentRegion = (Element) regionElementList.item(i);
			String regionName = currentRegion.getAttribute("name");
			fileRegions.add(regionName);
		}
		
		
		NodeList cityElementList = regionsElement.getElementsByTagName("city");
		
		//All city names in the file
		List<String> fileCities = new ArrayList<>();
		for (int i=0; i<cityElementList.getLength(); i++) {
			
			Element currentCity = (Element) cityElementList.item(i);
			fileCities.add(currentCity.getAttribute("name"));
			
		}
		
		Element rewardTokensElement = (Element) config.getElementsByTagName("rewardtokens").item(0);
		NodeList rewardTokensNodeList = rewardTokensElement.getElementsByTagName("bonus");
		
		//All reward tokens in the file
		List<Bonus> fileRewardTokens = new ArrayList<>();
		for (int i=0; i<rewardTokensNodeList.getLength(); i++) {
			Element currentBonusElement = (Element) rewardTokensNodeList.item(i);
			fileRewardTokens.add(BonusFactory.createBonus(currentBonusElement));
		}
		fileRewardTokens.add(BonusFactory.createEmptyBonus());
		
		String cityRegion;
		for(City c : cities.values()){
			
			cityRegion = c.getRegion().getName();
			assertTrue(fileRegions.contains(cityRegion));
			assertTrue(cityColors.values().contains(c.getCityColor()));
			assertTrue(fileCities.contains(c.getName()));
			assertTrue(fileRewardTokens.contains(c.getBonus()));
			
		}
		
	}

}