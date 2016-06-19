package it.polimi.ingsw.ps13.model.deck;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class PoliticsCardDeckFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Color> colors;
	
	PoliticsCardDeck deck;
	
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
		
		colors = new LinkedHashMap<>();
		ColorFactory.createColors(config, colors);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void createPoliticsCardDeck() throws Exception {
		
		deck = PoliticsCardDeckFactory.createPoliticsCardDeck(config, colors);
		
		Element politicsColorsElement = (Element) config.getElementsByTagName("politicscolors").item(0);
		NodeList colorsElements = politicsColorsElement.getElementsByTagName("color");
		
		List<Color> politicsColorsFile = new ArrayList<>(); //all politics colors in the file
		for (int i=0; i<colorsElements.getLength(); i++) {
			
			Element currentColor = (Element) colorsElements.item(i);
			String currentColorName = currentColor.getAttribute("name");
			
			politicsColorsFile.add(colors.get(currentColorName));
			
		}
		
		politicsColorsFile.add(new Color(6, 6, 6)); //Multicolor is a politics card color.
		
		for(PoliticsCard pc : deck.getDrawPile()){
			
			assertTrue(politicsColorsFile.contains(pc.getColor()));
			
		}
		
	}
	
}
