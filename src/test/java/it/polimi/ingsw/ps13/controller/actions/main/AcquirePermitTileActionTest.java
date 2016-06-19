package it.polimi.ingsw.ps13.controller.actions.main;

import static org.junit.Assert.*;

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
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class AcquirePermitTileActionTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	AcquirePermitTileAction action;
	
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
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void noToken() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setMain(0);
		
		cards = new ArrayList<>(); 
		for(Councillor c : game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()){
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
			
		}
		
		action = new AcquirePermitTileAction("player0", "hill", 0, cards);
		
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
	public void invalidRegion() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		cards = new ArrayList<>(); 
		for(Councillor c : game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()){
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
			
		}
		
		action = new AcquirePermitTileAction("player0", "molise", 0, cards);
		
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
	public void invalidPermitTile() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		cards = new ArrayList<>(); 
		for(Councillor c : game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()){
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
			
		}
		
		action = new AcquirePermitTileAction("player0", "hill", 10, cards);
		
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
	public void invalideCards() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		cards = new ArrayList<>();
		cards.add("jolly");
		cards.add("Machoke");
		
		action = new AcquirePermitTileAction("player0", "hill", 0, cards);
		
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
		p0.getTokens().setMain(1);
		
		cards = new ArrayList<>(); 
		
		String colorNotPresent = null;
		
		boolean isNotIn = false;
		Iterator<String> it = colors.keySet().iterator();
		
			while(it.hasNext() && isNotIn==false){
				
				isNotIn = true;
				String currentColor = it.next();
				for(Councillor c : game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()){
					
					if(c.getColorName().equals(currentColor))
						isNotIn = false;
					
				}
				colorNotPresent=currentColor;
			}
		
		cards.add(colorNotPresent);	
			
		action = new AcquirePermitTileAction("player0", "hill", 0, cards);
		
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
	public void cantSatisfy() throws Exception {
		
		Player p0 = game.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		cards = new ArrayList<>(); 
		for(Councillor c : game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()){
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
			
		}
		
		cards.remove(0);
		
		while(p0.getCoins()>0){
			p0.consumeCoins(1);
		}
		
		action = new AcquirePermitTileAction("player0", "hill", 0, cards);
		
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
		p0.getTokens().setMain(1);
		
		cards = new ArrayList<>(); 
		for(Councillor c : game.getBoard().getRegion("hill").getCouncillorBalcony().getCouncillors()){
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
			
		}
		
		action = new AcquirePermitTileAction("player0", "hill", 0, cards);
	
		assertTrue(action.isLegal(game));
		
		List<PoliticsCard> cardsPre = new ArrayList<>();
		cardsPre.addAll(p0.getPoliticsCards());
		
		List<PermitTile> tilesPre = new ArrayList<>();
		tilesPre.addAll(p0.getPermitTiles());
		
		action.apply(game);
		
		assertEquals(tilesPre.size() + 1, p0.getPermitTiles().size());
		
	}
	
}