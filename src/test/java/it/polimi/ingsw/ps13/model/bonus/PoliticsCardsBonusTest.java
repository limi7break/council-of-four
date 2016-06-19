package it.polimi.ingsw.ps13.model.bonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.ColorFactory;
import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class PoliticsCardsBonusTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	PoliticsCardsBonus politicsCardsBonus;
	
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
		
		politicsCardsBonus = new PoliticsCardsBonus(5);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void giveTo() throws Exception {
		
		Map<String, Color> colors = new LinkedHashMap<>();
		ColorFactory.createColors(config, colors);
		Board board = BoardFactory.createBoard(config, colors);
		Player player0 = new Player("player0", Color.BLACK, "black", 0, board);
		
		int pcPre = player0.getPoliticsCards().size();
		
		politicsCardsBonus.giveTo(player0);
		
		assertTrue(player0.getPoliticsCards().size() == pcPre + 5);
		
	}
	
	@Test
	public void getAmount() throws Exception {
		
		assertTrue(politicsCardsBonus.getAmount() == 5);
		
	}
	
	@Test
	public void isEmpty() throws Exception {
		
		assertFalse(politicsCardsBonus.isEmpty());
		
	}
	
	@Test
	public void toStringTest() throws Exception {
		
		assertEquals("PCx5", politicsCardsBonus.toString());
		
	}
	
	@Test
    public void hashCodeAndEquals() {
    	
		PoliticsCardsBonus equalPoliticsCardsBonus = new PoliticsCardsBonus(5);
    	assertEquals(politicsCardsBonus.hashCode(), equalPoliticsCardsBonus.hashCode());
    	assertEquals(politicsCardsBonus, politicsCardsBonus);
    	
    	boolean equalToNull = politicsCardsBonus.equals(null);
    	assertFalse(equalToNull);
    	
    }
	
}