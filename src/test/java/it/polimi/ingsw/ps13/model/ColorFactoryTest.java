package it.polimi.ingsw.ps13.model;

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

import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

import static org.junit.Assert.*;

public class ColorFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Color> colors;
	
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
	public void createColors() throws Exception {
		
		Element colorsElement = (Element) config.getElementsByTagName("colors").item(0);
		NodeList colorsNodeList = colorsElement.getElementsByTagName("color");
		for (int i=0; i<colorsNodeList.getLength(); i++) {
			
			Element currentColor = (Element) colorsNodeList.item(i);
			String colorName = currentColor.getAttribute("name");
			int r = Integer.parseInt(currentColor.getAttribute("r"));
			int g = Integer.parseInt(currentColor.getAttribute("g"));
			int b = Integer.parseInt(currentColor.getAttribute("b"));
			
			assertTrue(colors.values().contains(new Color(r, g, b)));
			assertTrue(colors.keySet().contains(colorName));
			
		}	
	}
}
