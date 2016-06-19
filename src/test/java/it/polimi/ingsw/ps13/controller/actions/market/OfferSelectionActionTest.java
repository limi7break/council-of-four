package it.polimi.ingsw.ps13.controller.actions.market;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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

import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.ColorFactory;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;
import it.polimi.ingsw.ps13.model.resource.Assistants;

public class OfferSelectionActionTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	OfferSelectionAction action;
	
	List<String> cards;
	Map<String, Color> colors;
	Board board;
	Game game;
	
	MarketEntry entry;
	
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
		board = BoardFactory.createBoard(config, colors);
		
		List<String> fourPlayers = new ArrayList<>();
		fourPlayers.add("player0");
		fourPlayers.add("player1");
		fourPlayers.add("player2");
		fourPlayers.add("player3");
		
		game = new Game(config, fourPlayers);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void noToken() throws Exception {
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setBuy(0);
		
		Collection<Marketable> items = new ArrayList<>();
		items.add(new Assistants(8));
		items.add(new PoliticsCard(Color.BLUE, "blue"));
		
		entry = new MarketEntry(game.getPlayer("player1"), items, 1);
		game.getMarket().addEntry(entry);
		
		Collection<Integer> entries = new ArrayList<>();
		entries.add(0);
		action = new OfferSelectionAction(p0.getName(), entries);
		
		boolean thrown = false;
		try{		
			action.isLegal(game);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		
		assertTrue(thrown);
		
	}
	
	@Test
	public void invalidPositiveEntry() throws Exception {
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setBuy(1);
		
		Collection<Marketable> items = new ArrayList<>();
		items.add(new Assistants(8));
		items.add(new PoliticsCard(Color.BLUE, "blue"));
		
		entry = new MarketEntry(game.getPlayer("player1"), items, 1);
		game.getMarket().addEntry(entry);
		
		Collection<Integer> entries = new ArrayList<>();
		entries.add(2);
		action = new OfferSelectionAction(p0.getName(), entries);
		
		boolean thrown = false;
		try{		
			action.isLegal(game);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		
		assertTrue(thrown);
		
	}
	
	@Test
	public void invalidNegativeEntry() throws Exception {
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setBuy(1);
		
		Collection<Marketable> items = new ArrayList<>();
		items.add(new Assistants(8));
		items.add(new PoliticsCard(Color.BLUE, "blue"));
		
		entry = new MarketEntry(game.getPlayer("player1"), items, 1);
		game.getMarket().addEntry(entry);
		
		Collection<Integer> entries = new ArrayList<>();
		entries.add(-2);
		action = new OfferSelectionAction(p0.getName(), entries);
		
		boolean thrown = false;
		try{		
			action.isLegal(game);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		
		assertTrue(thrown);
		
	}
	
	@Test
	public void noCoinsEnough() throws Exception {
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setBuy(1);
		
		Collection<Marketable> items = new ArrayList<>();
		items.add(new Assistants(8));
		items.add(new PoliticsCard(Color.BLUE, "blue"));
		
		entry = new MarketEntry(game.getPlayer("player1"), items, 50);
		game.getMarket().addEntry(entry);
		
		Collection<Integer> entries = new ArrayList<>();
		entries.add(0);
		action = new OfferSelectionAction(p0.getName(), entries);
		
		boolean thrown = false;
		try{		
			action.isLegal(game);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		
		assertTrue(thrown);
		
	}
	
	@Test
	public void apply() throws Exception {
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setBuy(1);
		
		Collection<Marketable> items = new ArrayList<>();
		items.add(new Assistants(8));
		items.add(new PoliticsCard(Color.BLUE, "blue"));
		
		entry = new MarketEntry(game.getPlayer("player1"), items, 5);
		game.getMarket().addEntry(entry);
		
		Collection<Integer> entries = new ArrayList<>();
		entries.add(0);
		action = new OfferSelectionAction(p0.getName(), entries);
		
		int coinsPre = p0.getCoins();
		int assistantsPre = p0.getAssistants();
		int handPre = p0.getPoliticsCards().size();
		
		assertTrue(action.isLegal(game));
		action.apply(game);
		
		assertEquals(coinsPre - 5, p0.getCoins());
		assertEquals(assistantsPre + 8, p0.getAssistants());
		assertEquals(handPre + 1, p0.getPoliticsCards().size());
		
	}
	
}
