package it.polimi.ingsw.ps13.controller.actions.market;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class TradeProposalActionTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	TradeProposalAction action;
	
	List<String> cards;
	Map<String, Color> colors;
	Board board;
	Game game;
	
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
		
		game.getPlayer("player0").receivePermitTile(game.getBoard().getRegion("hill").getPermitTileDeck().getVisibleTiles().get(0));
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void noToken() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setSell(0);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(0);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		cards.add("pink");
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 10, tiles, cards, 15);
		
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
	public void noAssistantsEnoughToSell() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setSell(1);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(0);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		cards.add("pink");
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 20, tiles, cards, 15);
		
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
	public void invalidPositiveTiles() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setSell(1);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(10);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		cards.add("pink");
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 10, tiles, cards, 15);
		
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
	public void invalidNegativeTiles() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setSell(1);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(-3);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		cards.add("pink");
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 10, tiles, cards, 15);
		
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
	public void invalidCards() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setSell(1);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(0);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		cards.add("jolly");
		cards.add("cartaCheSpacca");
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 10, tiles, cards, 15);
		
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
	public void mismatch() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setSell(1);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(0);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		
		String colorNotPresent = null;
		boolean isNotIn = false;
		Iterator<String> it = colors.keySet().iterator();
		
			while(it.hasNext() && isNotIn==false){
				
				isNotIn = true;
				String currentColor = it.next();
				for(PoliticsCard pc : p0.getPoliticsCards()){
					
					if(pc.getColorName().equals(currentColor))
						isNotIn = false;
					
				}
				colorNotPresent=currentColor;
			}
		
		cards.add(colorNotPresent);
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 10, tiles, cards, 15);
		
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
		p0.getTokens().setSell(1);
		
		p0.addAssistants(10);
		Collection<Integer> tiles = new ArrayList<>();
		tiles.add(0);
		p0.receivePoliticsCard(new PoliticsCard(Color.PINK, "pink"));
		Collection<String> cards = new ArrayList<>();
		cards.add("pink");
		
		game.getPlayer("player1").addCoins(20);
		
		action = new TradeProposalAction(p0.getName(), 10, tiles, cards, 15);
		
		assertTrue(action.isLegal(game));
		
		int assistantsPre = p0.getAssistants();
		int tilesPre = p0.getPermitTiles().size();
		int cardsPre = p0.getPoliticsCards().size();
		
		action.apply(game);
		
		assertTrue(game.getMarket().getEntryList().get(0).getPrice() == 15);
		assertEquals(assistantsPre - 10, p0.getAssistants());
		assertEquals(tilesPre - 1, p0.getPermitTiles().size());
		assertEquals(cardsPre - 1, p0.getPoliticsCards().size());
		
	}
	
}