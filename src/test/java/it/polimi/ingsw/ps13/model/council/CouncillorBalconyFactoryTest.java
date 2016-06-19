package it.polimi.ingsw.ps13.model.council;

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

public class CouncillorBalconyFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Color> colors;
	List<CouncillorBalcony> balconies;
	List<Councillor> leftovers;
	
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
		
		balconies = new ArrayList<>();
		leftovers = new ArrayList<>();
		colors = new LinkedHashMap<>();
		ColorFactory.createColors(config, colors);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void createCouncillorBalconies() throws Exception {
		
		leftovers = CouncillorBalconyFactory.createCouncillorBalconies(4, balconies, colors, config);
		
		assertTrue(balconies.size() == 4); 
		
		Element politicsColorsElement = (Element) config.getElementsByTagName("politicscolors").item(0);
		NodeList colorsElements = politicsColorsElement.getElementsByTagName("color");
	
		List<Color> politicsColors = new ArrayList<>(); //all politics colors in the file
		for (int i=0; i<colorsElements.getLength(); i++) {
			
			Element currentColor = (Element) colorsElements.item(i);
			String currentColorName = currentColor.getAttribute("name");
			
			politicsColors.add(colors.get(currentColorName));
			
		}
		
		for(CouncillorBalcony cb : balconies){
			
			for(Councillor c : cb.getCouncillors()){
				
				assertTrue(politicsColors.contains(c.getColor()));
				
			}
			
		}
		
	}
	
}
