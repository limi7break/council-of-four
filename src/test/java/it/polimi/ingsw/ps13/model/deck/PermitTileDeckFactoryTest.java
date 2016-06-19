package it.polimi.ingsw.ps13.model.deck;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class PermitTileDeckFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Element currentRegion;
	NodeList permitTileElementList;
	
	PermitTileDeck deck;
	
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
		
		Element regionsElement = (Element) config.getElementsByTagName("regions").item(0);
		NodeList regionElementList = regionsElement.getElementsByTagName("region");
		
		currentRegion = (Element) regionElementList.item(0);	//the first region in the file
		permitTileElementList = currentRegion.getElementsByTagName("permittile");
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void createPermitTileDeck() throws Exception {
		
		deck = PermitTileDeckFactory.createPermitTileDeck(permitTileElementList);
		
		List<Bonus> tileBonusesFile = new ArrayList<>(); //all bonuses of the permit tiles in the file
		Set<String> tileCitiesFile = new TreeSet<>();	//all cities of the permit tiles in the file
		for (int i=0; i<permitTileElementList.getLength(); i++) {
			Element currentPermitTile = (Element) permitTileElementList.item(i);
			Element bonus = (Element) currentPermitTile.getElementsByTagName("bonus").item(0);
		
			tileBonusesFile.add(BonusFactory.createBonus(bonus));
			
			NodeList cityElementList = currentPermitTile.getElementsByTagName("city");
			for (int j=0; j<cityElementList.getLength(); j++) {
				Element currentCity = (Element) cityElementList.item(j);
				String currentCityName = currentCity.getAttribute("name");
				tileCitiesFile.add(currentCityName);
			}
			
		}
		
		for(PermitTile pt : deck.getDrawPile()){
			
			assertTrue(tileBonusesFile.contains(pt.getBonus()));
			assertTrue(tileCitiesFile.containsAll(pt.getCityNames()));
			
		}
		
	}		
	
}
