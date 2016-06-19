package it.polimi.ingsw.ps13.model.bonus;

import static org.junit.Assert.*;

import java.io.File;
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

public class BonusFactoryTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Element rewardTokensElement;
	NodeList rewardTokensNodeList;
	
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
		
		rewardTokensElement = (Element) config.getElementsByTagName("rewardtokens").item(0);
		rewardTokensNodeList = rewardTokensElement.getElementsByTagName("bonus");
		
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void createBonus() throws Exception {
		
		for (int i=0; i<rewardTokensNodeList.getLength(); i++) {
			Element currentBonusElement = (Element) rewardTokensNodeList.item(i);
			assertFalse(BonusFactory.createBonus(currentBonusElement).equals(null));
			assertTrue(BonusFactory.createBonus(currentBonusElement) instanceof ConcreteBonus );
		}
		
	}
	
}
