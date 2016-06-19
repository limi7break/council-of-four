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

public class CityColorFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Color> colors;
	Map<String, CityColor> cityColors;
	
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
		
		colors = new HashMap<>();
		ColorFactory.createColors(config, colors);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void createCityColors() throws Exception {
		
		cityColors = CityColorFactory.createCityColors(config, colors);
		
		List<Bonus> cityColorsBonusesFile = new ArrayList<>();	//all bonuses linked to city materials(colors)
		List<String> cityColorsNamesFile = new ArrayList<>();	//all the names of the city colors
		
		Element cityColorsElement = (Element) config.getElementsByTagName("citycolors").item(0);
		NodeList colorsElementList = cityColorsElement.getElementsByTagName("color"); 
		for (int i=0; i<colorsElementList.getLength(); i++) {
			Element currentColor = (Element) colorsElementList.item(i);
			
			Element currentBonusElement = (Element) currentColor.getElementsByTagName("bonus").item(0);
			Bonus currentBonus = BonusFactory.createBonus(currentBonusElement);
			String currentColorName = currentColor.getAttribute("name");
			
			cityColorsBonusesFile.add(currentBonus);
			cityColorsNamesFile.add(currentColorName);
			
		}
		
		for(Map.Entry<String, CityColor> entry : cityColors.entrySet()){
			
			assertTrue(cityColorsNamesFile.contains(entry.getKey()));
			assertTrue(cityColorsBonusesFile.contains(entry.getValue().getBonus()));
			
		}
		
	}
	
}
