package it.polimi.ingsw.ps13.model.bonus;

import static org.junit.Assert.*;

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
import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class ConcreteBonusTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	List<Bonus> fileRewardTokens;
	ConcreteBonus concreteBonus;
	
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
		

		Element rewardTokensElement = (Element) config.getElementsByTagName("rewardtokens").item(0);
		NodeList rewardTokensNodeList = rewardTokensElement.getElementsByTagName("bonus");
		
		fileRewardTokens = new ArrayList<>();	//all the reward tokens in the file
		for (int i=0; i<rewardTokensNodeList.getLength(); i++) {
			Element currentBonusElement = (Element) rewardTokensNodeList.item(i);
			fileRewardTokens.add(BonusFactory.createBonus(currentBonusElement));
		}
		fileRewardTokens.add(BonusFactory.createEmptyBonus()); 
		
		concreteBonus = new ConcreteBonus(fileRewardTokens); //should create a concrete bonus containing all reward token bonuses
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void isEmpty() throws Exception {
		
		assertFalse(concreteBonus.isEmpty());
		
	}
	
	@Test
	public void giveTo() throws Exception {
		
		Map<String, Color> colors = new LinkedHashMap<>();
		ColorFactory.createColors(config, colors);
		Board board = BoardFactory.createBoard(config, colors);
		Player player0 = new Player("player0", Color.BLACK, "black", 0, board);
		
		int victoryPointsPre = player0.getVictoryPoints();
		int assistantsPre = player0.getAssistants();
		
		concreteBonus.giveTo(player0);
		
		assertFalse(player0.getVictoryPoints() == victoryPointsPre);
		assertFalse(player0.getAssistants() == assistantsPre);
		
	}
	
	@Test
	public void getContents() throws Exception {
		
		List<Bonus> contents = concreteBonus.getContents();
		assertFalse(contents.equals(null));
		for(Bonus b : fileRewardTokens){
			
			assertTrue(contents.contains(b));
			
		}
		
	}
	
}