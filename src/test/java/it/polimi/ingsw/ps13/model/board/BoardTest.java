package it.polimi.ingsw.ps13.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.council.CouncillorBalconyFactory;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeckFactory;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;
import it.polimi.ingsw.ps13.model.region.CityColorFactory;
import it.polimi.ingsw.ps13.model.region.Region;
import it.polimi.ingsw.ps13.model.region.RegionFactory;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

/**
 * 
 *
 */
public class BoardTest {
	
	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Region> regions;
	Map<String, CityColor> cityColors;
	Map<String, Color> colors;
	List<CouncillorBalcony> balconies;
	PoliticsCardDeck pcDeck;
	Map<String, City> cities;
	List<Bonus> rewardTokens;
	List<Councillor> freeCouncillors;
	
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
		
		regions = new HashMap<>();
		colors = new HashMap<>();
		ColorFactory.createColors(config, colors);
		cityColors = CityColorFactory.createCityColors(config, colors);
		balconies = new ArrayList<>();
		freeCouncillors = CouncillorBalconyFactory.createCouncillorBalconies(4, balconies, colors, config);
		
		cities = RegionFactory.createCities(regions, cityColors, balconies, colors, config);
		
		pcDeck = PoliticsCardDeckFactory.createPoliticsCardDeck(config, colors);
		
		board = new Board(regions, cityColors, cities, pcDeck, balconies.get(0), freeCouncillors, config);
		
		assertEquals(board.getCityColors(), cityColors);
		
		CityColor arkonCityColor = board.getCity("Arkon").getCityColor();
		assertEquals(arkonCityColor, board.getCityColor(arkonCityColor.getColorName()));
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void getRegion() throws Exception {
		
		assertEquals(board.getRegion("coast").getName(), "coast");
		
	}

	@Test
	public void getCity() throws Exception {
		
		assertEquals(board.getCity("Juvelar").getName(), "Juvelar");
		
	}
	
	@Test
	public void kingRewardTiles() throws Exception {
		
		Element kingRewardTilesElement = (Element) config.getElementsByTagName("kingrewardtiles").item(0);
		NodeList bonusElementList = kingRewardTilesElement.getElementsByTagName("bonus");
		for (int i=0; i<bonusElementList.getLength(); i++) {
			
			Element currentBonusElement = (Element) bonusElementList.item(i);
			assertTrue(board.getKingRewardTiles().contains(new KingRewardTile(BonusFactory.createBonus(currentBonusElement))));
			assertTrue(board.getKingRewardTiles().get(i).isAvailable());
			assertEquals(board.getKingRewardTiles().get(i).getBonus(), BonusFactory.createBonus(currentBonusElement));
			
			KingRewardTile equalRewardTile = new KingRewardTile(BonusFactory.createBonus(currentBonusElement));
			assertTrue(board.getKingRewardTiles().get(i).equals(equalRewardTile));
			assertEquals(board.getKingRewardTiles().get(i).hashCode(), equalRewardTile.hashCode());
		}
		
		board.getKingRewardTiles().get(0).setAvailable(false);
		assertFalse(board.getKingRewardTiles().get(0).isAvailable());
		
		boolean illegalStateChange = false;
		try {
			board.getKingRewardTiles().get(0).setAvailable(true);
		} catch (IllegalArgumentException e) {
			illegalStateChange = true;
		}
		assertTrue(illegalStateChange);
		
		assertEquals(board.getNextAvailableKingRewardTile(), board.getKingRewardTiles().get(1));
		
		// When every king reward tile is not available, getNextAvailableKingRewardTile should return null 
		for (KingRewardTile krt : board.getKingRewardTiles()) {
			krt.setAvailable(false);
		}
		assertEquals(board.getNextAvailableKingRewardTile(), null);
		
	}
	
	@Test
	public void nobilityTrack() throws Exception {
		
		Element nobilityTrackElement = (Element) config.getElementsByTagName("nobilitytrack").item(0);
		NodeList positionElementList = nobilityTrackElement.getElementsByTagName("position");
		for (int i=0; i<positionElementList.getLength(); i++) {
			Element currentElement = (Element) positionElementList.item(i);
			int position = Integer.parseInt(currentElement.getAttribute("value"));
			Element bonusElement = (Element) currentElement.getElementsByTagName("bonus").item(0);
			Bonus bonus = BonusFactory.createBonus(bonusElement);
			
			assertEquals(board.getNobilityTrack().getBonus(position).toString(), bonus.toString());
		}
		
	}
	
	@Test
	public void insertCouncillor() throws Exception {
		
		int councillorsPre = board.getCouncillors().size();
		
		Councillor c = new Councillor(Color.BLACK, "black");
		board.insertCouncillor(c);
		
		assertTrue(board.getCouncillors().size() == councillorsPre + 1);
		assertTrue(board.getCouncillors().contains(c));
		
	}
	
	@Test
	public void priceToMoveKing() throws Exception {
		
		City juvelar = board.getCity("Juvelar");
		City graden = board.getCity("Graden");
		City merkatim = board.getCity("Merkatim");
		
		assertTrue(board.priceToMoveKing(juvelar) == 0);
		assertTrue(board.priceToMoveKing(graden) == 2);
		assertTrue(board.priceToMoveKing(merkatim) == 4);
		
		
	}
	
	@Test
	public void kingCity() throws Exception {
		
		City merkatim = board.getCity("Merkatim");
		board.setKingCity(merkatim);
		
		assertTrue(board.getKing().getCity().getName().equals("Merkatim"));
		
		King king = new King();
		king.setCity(merkatim);
		assertEquals(board.getKing().toString(), king.toString());
		
	}
	
	@Test
	public void getCouncillorByColor() throws Exception {
	
		Color firstCouncillorColor = board.getCouncillors().get(0).getColor();
		Councillor returnedCouncillor = board.getCouncillor(firstCouncillorColor);
		
		assertEquals(firstCouncillorColor, returnedCouncillor.getColor());
		
	}
	
	@Test
	public void councillorNotAvailable() throws Exception {
		
		// Remove every black councillor from the available councillors (if any)
		for (Iterator<Councillor> it = board.getCouncillors().iterator(); it.hasNext();) {
			Councillor councillor = it.next();
			if (councillor.getColor().equals(Color.black)) {
				it.remove();
			}
		}
		
		// Now getCouncillor should return null when called with Color.black
		assertEquals(board.getCouncillor(Color.black), null);
		
		// Now isCouncillorAvailable should return false when called with Color.black
		assertFalse(board.isCouncillorAvailable(Color.black));
		
	}
	
}
