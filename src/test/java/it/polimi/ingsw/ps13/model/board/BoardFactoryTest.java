package it.polimi.ingsw.ps13.model.board;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
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

import static org.junit.Assert.assertTrue;

public class BoardFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Color> colors;
	Board board;
	
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
	public void createBoard() throws Exception {
		
		board = BoardFactory.createBoard(config, colors);
		
		Element regionsElement = (Element) config.getElementsByTagName("regions").item(0);
		NodeList regionElementList = regionsElement.getElementsByTagName("region");
		for (int i=0; i<regionElementList.getLength(); i++) {
			Element currentRegion = (Element) regionElementList.item(i);
			String regionName = currentRegion.getAttribute("name");
			
			assertTrue(board.getRegions().keySet().contains(regionName));
			
		}
		
	}
	
}
