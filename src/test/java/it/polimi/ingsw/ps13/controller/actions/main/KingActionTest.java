package it.polimi.ingsw.ps13.controller.actions.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

public class KingActionTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	List<String> cards;
	Map<String, Color> colors;
	Board board;
	
	Player player0;
	Player player1;
	Player player2;
	Player player3;
	
	Game fourPlayerGame;
	KingAction kingAction;
	
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
		
		fourPlayerGame = new Game(config, fourPlayers);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void noTokens() throws Exception {
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(0);
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		boolean thrown = false;
		try{
			
			kingAction.isLegal(fourPlayerGame);
			
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void noEmporiumsLeft() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		for(int i = 0; i<10; i++){
			
			p0.removeEmporium();
			
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		boolean thrown = false;
		try{
			
			kingAction.isLegal(fourPlayerGame);
			
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void notValidCity() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		kingAction = new KingAction(p0.getName(), "Busnago", cards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void alreadyBuiltOnThatCity() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		City city = fourPlayerGame.getBoard().getCity("Graden");
		
		Emporium emporium = p0.removeEmporium();
		city.addEmporium(emporium);
		p0.addCity("Graden");
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void noValidCards() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		Collection<String> invalidCards = new ArrayList<>();
		invalidCards.add("jolly");
		invalidCards.add("unBelColore");
		invalidCards.add("unBelColminuti");
		
		kingAction = new KingAction(p0.getName(), "Graden", invalidCards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);
		}
		catch(IllegalActionException e){
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void noMatchFound() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
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
			
		List<String> invalidCards = new ArrayList<>();
		invalidCards.add(colorNotPresent);
		
		kingAction = new KingAction(p0.getName(), "Graden", invalidCards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);	
		}
		catch(IllegalActionException e){
			thrown = true;	
		}
		assertTrue(thrown);
	}
	
	@Test
	public void notSatisfiableCouncil() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		while(p0.getCoins()>0){
			p0.consumeCoins(1);	
		}
		cards.remove(0);
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);	
		}
		catch(IllegalActionException e){
			thrown = true;	
		}
		assertTrue(thrown);
		
	}

	@Test
	public void noCoinsToMoveKing() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		while(p0.getCoins()>0){
			p0.consumeCoins(1);	
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);	
		}
		catch(IllegalActionException e){
			thrown = true;	
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void noAssistantsEnough() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		while(p0.getAssistants()>0){
			p0.consumeAssistants(1);
		}
		
		Emporium built = fourPlayerGame.getPlayer("player1").removeEmporium();
		
		fourPlayerGame.getBoard().getCity("Graden").addEmporium(built);
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		boolean thrown = false;
		try{
			kingAction.isLegal(fourPlayerGame);	
		}
		catch(IllegalActionException e){
			thrown = true;	
		}
		assertTrue(thrown);
		
	}

	@Test
	public void isLegal() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		
		assertTrue(kingAction.isLegal(fourPlayerGame));
		
	}
	
	@Test
	public void apply() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		assertTrue(kingAction.isLegal(fourPlayerGame));
		kingAction.apply(fourPlayerGame);
		
		assertEquals(fourPlayerGame.getBoard().getKing().getCity().getName(), "Graden");
		
	}
	
	@Test
	public void completeRegion() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		assertTrue(kingAction.isLegal(fourPlayerGame));
		
		for(City c : fourPlayerGame.getBoard().getCities().values()){
			
			if(c.getRegion().getName().equals("hill") && !c.getName().equals("Graden")){
				c.addEmporium(p0.removeEmporium());
				p0.addCity(c.getName());
			}
				
		}
		
		assertTrue(fourPlayerGame.getBoard().getRegion("hill").isBonusAvailable());
		kingAction.apply(fourPlayerGame);
		assertFalse(fourPlayerGame.getBoard().getRegion("hill").isBonusAvailable());
		
	}
	
	@Test
	public void completeCityColor() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		assertTrue(kingAction.isLegal(fourPlayerGame));
		
		CityColor gradens = fourPlayerGame.getBoard().getCity("Graden").getCityColor();
		for(City c : fourPlayerGame.getBoard().getCities().values()){
			
			if(c.getCityColor().equals(gradens) && !c.getName().equals("Graden")){
				c.addEmporium(p0.removeEmporium());
				p0.addCity(c.getName());
			}
				
		}
		
		assertTrue(fourPlayerGame.getBoard().getCityColor(gradens.getColorName()).isBonusAvailable());
		kingAction.apply(fourPlayerGame);
		assertFalse(fourPlayerGame.getBoard().getCityColor(gradens.getColorName()).isBonusAvailable());
		
	}
	
	@Test
	public void buildLastEmporium() throws Exception {
		
		Player p0 = fourPlayerGame.getPlayer("player0");
		p0.getTokens().setMain(1);
		
		List<Councillor> kingCouncillors = new ArrayList<>();
		kingCouncillors.addAll(fourPlayerGame.getBoard().getKingBalcony().getCouncillors());
		List<String> cards = new ArrayList<>();
		for(Councillor c : kingCouncillors){	
			
			p0.receivePoliticsCard(new PoliticsCard(c.getColor(), c.getColorName()));
			cards.add(c.getColorName());
		}
		
		kingAction = new KingAction(p0.getName(), "Graden", cards);
		assertTrue(kingAction.isLegal(fourPlayerGame));
		
		while(p0.getNumberOfEmporiums()!=1){	
			p0.removeEmporium();
		}
		
		kingAction.apply(fourPlayerGame);
		
		assertEquals(fourPlayerGame.getPlayerWhoBuiltLastEmporium(), p0.getID());
	}
	
}