package it.polimi.ingsw.ps13.controller.actions.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
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
import it.polimi.ingsw.ps13.model.council.Councillor;

public class ElectCouncillorActionTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(ElectCouncillorActionTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	List<String> cards;
	Map<String, Color> colors;
	Board board;
	Game game;
	
	ElectCouncillorAction action;
	
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
	public void noTokens() throws Exception {
		
		action = new ElectCouncillorAction("player0", "hill", game.getBoard().getCouncillors().get(0).getColorName());
		game.getPlayer("player0").getTokens().setMain(0);
		
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
	public void regionInvalid() throws Exception {
		
		action = new ElectCouncillorAction("player0", "regioneMoltoMoltoBella", game.getBoard().getCouncillors().get(0).getColorName());
		game.getPlayer("player0").getTokens().setMain(1);
		
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
	public void colorInvalid() throws Exception {
		
		action = new ElectCouncillorAction("player0", "hill", "Colore molto carino");
		game.getPlayer("player0").getTokens().setMain(1);
		
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
	public void noCouncillorAvailable() throws Exception {
		
		String colorNotPresent = null;
		
		boolean isNotIn = false;
		Iterator<String> it = colors.keySet().iterator();
		
		while(it.hasNext() && isNotIn==false){
				
			isNotIn = true;
			String currentColor = it.next();
			for(Councillor c : game.getBoard().getCouncillors()){
				
				if(c.getColorName().equals(currentColor))
					isNotIn = false;
					
			}
			colorNotPresent=currentColor;
		}
		
		action = new ElectCouncillorAction("player0", "hill", colorNotPresent);
		game.getPlayer("player0").getTokens().setMain(1);
		
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
	public void isLegal() throws Exception {
		
		action = new ElectCouncillorAction("player0", "hill", game.getBoard().getCouncillors().get(0).getColorName());
		game.getPlayer("player0").getTokens().setMain(1);
		
		assertTrue(action.isLegal(game));
		
	}
	
	@Test
	public void apply() throws Exception {
		
		action = new ElectCouncillorAction("player0", "hill", game.getBoard().getCouncillors().get(0).getColorName());
		game.getPlayer("player0").getTokens().setMain(1);
		
		List<Councillor> hillBalconyPre = new ArrayList<>();
		hillBalconyPre.addAll(game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors());
		int coinsPre = game.getPlayer("player0").getCoins();
		
		action.apply(game);
		
		assertFalse(hillBalconyPre.equals(game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()));
		assertEquals(coinsPre + 4, game.getPlayer("player0").getCoins());
	}
	
	@Test
	public void kingElect() throws Exception {
		
		action = new ElectCouncillorAction("player0", "king", game.getBoard().getCouncillors().get(0).getColorName());
		game.getPlayer("player0").getTokens().setMain(1);
		
		List<Councillor> kingBalconyPre = new ArrayList<>();
		kingBalconyPre.addAll(game.getBoard().getKingBalcony().getCouncillors());
		int coinsPre = game.getPlayer("player0").getCoins();
		
		action.apply(game);
		
		assertFalse(kingBalconyPre.equals(game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()));
		assertEquals(coinsPre + 4, game.getPlayer("player0").getCoins());
		
	}
	
}
