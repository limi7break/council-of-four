package it.polimi.ingsw.ps13.model;

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

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.Region;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;
import it.polimi.ingsw.ps13.model.resource.Assistants;

/**
 * Test for the class Game. 
 *
 */
public class GameTest {

	private static final String TESTCONFIG = "configTest.xml";
	Document config;
	final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
	String testFilePath = TESTCONFIG;
	
	Map<String, Color> colors;
	Board board;
	
	Game fourPlayerGame;
	Game threePlayerGame;
	Game twoPlayerGame;
	
	@Before
	public void setUp() throws Exception {
		
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
		
		Player player0 = new Player("player0", Color.BLACK, "black", 0, board);
		Player player1 = new Player("player1", Color.BLUE, "blue", 1, board);
		Player player2 = new Player("player2", Color.GREEN, "green", 2, board);
		Player player3 = new Player("player3", Color.PINK, "pink", 3, board);
		
		List<String> fourPlayers = new ArrayList<>();
		fourPlayers.add(player0.getName());
		fourPlayers.add(player1.getName());
		fourPlayers.add(player2.getName());
		fourPlayers.add(player3.getName());
		
		List<String> threePlayers = new ArrayList<>();
		threePlayers.add(player0.getName());
		threePlayers.add(player1.getName());
		threePlayers.add(player2.getName());
		
		List<String> twoPlayers = new ArrayList<>();
		twoPlayers.add(player0.getName());
		twoPlayers.add(player1.getName());

		fourPlayerGame = new Game(config, fourPlayers);
		threePlayerGame = new Game(config, threePlayers);
		twoPlayerGame = new Game(config, twoPlayers);
		
		assertTrue(fourPlayerGame.getMarket().isEnabled());
	    assertEquals(fourPlayerGame.getNumberOfPlayers(), 4);
	    assertEquals(twoPlayerGame.getNumberOfPlayers(), 2);
	    
	    assertEquals(fourPlayerGame.getColorName(new Color(1, 2, 3)), null);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void getConnectedPlayers() throws Exception {
		
		assertEquals(fourPlayerGame.getConnectedPlayers(), 4);
		
	}
	
	
	@Test
	public void getBoard() throws Exception {
			
		for(String city : board.getCities().keySet()){
			assertTrue(fourPlayerGame.getBoard().getCities().keySet().contains(city));
			}
		
		assertTrue(fourPlayerGame.getBoard().getCouncillors().size() == board.getCouncillors().size());
		
		for(String region : board.getRegions().keySet()){
				assertTrue(fourPlayerGame.getBoard().getRegions().keySet().contains(region));
			}
		
		assertEquals(board.getKingRewardTiles().size(), fourPlayerGame.getBoard().getKingRewardTiles().size());
		assertEquals(board.getKingBalcony().getCouncillors().size(), fourPlayerGame.getBoard().getKingBalcony().getCouncillors().size());
		
		for(Region r : board.getRegions().values()){
				for(Region rg : fourPlayerGame.getBoard().getRegions().values()){
				
				assertEquals(r.getCouncillorBalcony().getCouncillors().size(), rg.getCouncillorBalcony().getCouncillors().size());
				
			}	
		}
		
	}
	
	@Test
	public void twoPlayerSetup() throws Exception {
		
		boolean emporiumExists = false;
		
		Iterator<City> it = twoPlayerGame.getBoard().getCities().values().iterator();
		while(it.hasNext() && emporiumExists == false){
			
			if(it.next().getNumberOfEmporiums() == 1)
				emporiumExists = true;
			
		}
		
		assertTrue(emporiumExists);
		
	}
	
	@Test
	public void getPlayers() throws Exception {
		
		assertEquals(twoPlayerGame.getPlayers().size(), 2);
		
	}
	
	@Test
	public void passTurn() throws Exception {
		
		int firstPlayerID = threePlayerGame.getCurrentPlayerID();
		Player firstPlayer = threePlayerGame.getCurrentPlayer();
		int secondPlayerID = threePlayerGame.getNextPlayerID(firstPlayerID);
		Player secondPlayer = threePlayerGame.getPlayers().get(secondPlayerID);
		int thirdPlayerID = threePlayerGame.getNextPlayerID(secondPlayerID);
		Player thirdPlayer = threePlayerGame.getPlayers().get(thirdPlayerID);
        
		assertEquals(threePlayerGame.getCurrentPlayerName(), firstPlayer.getName());
        
        threePlayerGame.passTurn(); // Second player's turn, normal game phase
        assertEquals(threePlayerGame.getCurrentPlayerID(), secondPlayerID);
		assertTrue(firstPlayer.getTokens().isEmpty());
		assertTrue(thirdPlayer.getTokens().isEmpty());
		assertFalse(threePlayerGame.isSellMarketPhase());
		assertFalse(threePlayerGame.isBuyMarketPhase());
		
		threePlayerGame.passTurn(); // Third player's turn, normal game phase
		assertEquals(threePlayerGame.getCurrentPlayerID(), thirdPlayerID);
		assertTrue(firstPlayer.getTokens().isEmpty());
		assertTrue(secondPlayer.getTokens().isEmpty());
		assertFalse(threePlayerGame.isSellMarketPhase());
		assertFalse(threePlayerGame.isBuyMarketPhase());
		
		threePlayerGame.passTurn(); // First player's turn, sell market phase
		assertEquals(threePlayerGame.getCurrentPlayerID(), firstPlayerID);
		assertEquals(firstPlayer.getTokens().getSell(), 1);
		assertTrue(secondPlayer.getTokens().isEmpty());
		assertTrue(thirdPlayer.getTokens().isEmpty());
		assertTrue(threePlayerGame.isSellMarketPhase());
		assertFalse(threePlayerGame.isBuyMarketPhase());
		
		threePlayerGame.passTurn(); // Second player's turn, sell market phase
		threePlayerGame.passTurn(); // Third player's turn, sell market phase
		threePlayerGame.passTurn(); // First player's turn, normal game phase
									// No buy market phase because market is empty,
									// there is nothing to buy
		assertEquals(threePlayerGame.getCurrentPlayerID(), firstPlayerID);
		assertTrue(secondPlayer.getTokens().isEmpty());
		assertTrue(thirdPlayer.getTokens().isEmpty());
		assertFalse(threePlayerGame.isSellMarketPhase());
		assertFalse(threePlayerGame.isBuyMarketPhase());
		
		threePlayerGame.passTurn(); // Second player's turn, normal game phase
		threePlayerGame.passTurn(); // Third player's turn, normal game phase
		threePlayerGame.passTurn(); // First player's turn, sell market phase
		threePlayerGame.passTurn(); // Second player's turn, sell market phase
		threePlayerGame.passTurn(); // Third player's turn, sell market phase
		
		List<Marketable> items = new ArrayList<>();
		items.add(new Assistants(2));
		MarketEntry entry = new MarketEntry(firstPlayer, items, 5);
		threePlayerGame.getMarket().addEntry(entry);
		
		threePlayerGame.passTurn(); // First player's turn, buy market phase
									// Now market is not empty
		assertEquals(threePlayerGame.getCurrentPlayerID(), firstPlayerID);
		assertEquals(firstPlayer.getTokens().getBuy(), 1);
		assertTrue(secondPlayer.getTokens().isEmpty());
		assertTrue(thirdPlayer.getTokens().isEmpty());
		assertFalse(threePlayerGame.isSellMarketPhase());
		assertTrue(threePlayerGame.isBuyMarketPhase());
		
		threePlayerGame.passTurn(); // Second player's turn, buy market phase
		threePlayerGame.passTurn(); // Third player's turn, buy market phase
		threePlayerGame.passTurn(); // First player's turn, normal game phase
		threePlayerGame.passTurn(); // Second player's turn, normal game phase
		
		threePlayerGame.setPlayerWhoBuiltLastEmporium(secondPlayerID);
		assertEquals(threePlayerGame.getPlayerWhoBuiltLastEmporium(), secondPlayerID);
		threePlayerGame.passTurn(); // Third player's turn, normal game phase
		threePlayerGame.passTurn(); // First player's turn, normal game phase
									// If a player has built the last emporium,
									// market phases are skipped.
									// Check if we are in the normal game phase
		assertEquals(threePlayerGame.getCurrentPlayerID(), firstPlayerID);
		assertTrue(secondPlayer.getTokens().isEmpty());
		assertTrue(thirdPlayer.getTokens().isEmpty());
		assertFalse(threePlayerGame.isSellMarketPhase());
		assertFalse(threePlayerGame.isBuyMarketPhase());
		// Check if the game is not finished yet
		assertFalse(threePlayerGame.isFinished());
		
		threePlayerGame.passTurn();
		// Now the game should be finished. Second player built the last emporium
		// and every other player (third, first) played its last turn.
		assertTrue(threePlayerGame.isFinished());
		
		boolean illegalPassTurn = false;
		try {
			threePlayerGame.passTurn();
		} catch (IllegalStateException e) {
			illegalPassTurn = true;
		}
		assertTrue(illegalPassTurn);
		
	}
	
	@Test
	public void getNextPlayerIDWithDisconnection() {
		
		int firstPlayerID = 0;
		int secondPlayerID = 1;
		int thirdPlayerID = 2;
		
		// Everyone connected
		assertEquals(threePlayerGame.getNextPlayerID(firstPlayerID), secondPlayerID); // First -> Second
		assertEquals(threePlayerGame.getNextPlayerID(secondPlayerID), thirdPlayerID); // Second -> Third
		assertEquals(threePlayerGame.getNextPlayerID(thirdPlayerID), firstPlayerID); // Third -> First
		
		threePlayerGame.getPlayers().get(secondPlayerID).setConnected(false);
		
		// Player two disconnected
		assertEquals(threePlayerGame.getNextPlayerID(firstPlayerID), thirdPlayerID); // First -> Third
		
	}
	
	@Test
	public void isCouncillorAvailable() throws Exception {
		
		for(Councillor c : fourPlayerGame.getBoard().getCouncillors()){	
			assertTrue(fourPlayerGame.getBoard().isCouncillorAvailable(c.getColor()));	
		}	
	}
	
	@Test
	public void getColorName() throws Exception {
		
		for(Map.Entry<String, Color> entry : fourPlayerGame.getColors().entrySet()){	
			assertEquals(fourPlayerGame.getColorName(entry.getValue()), entry.getKey());	
		}	
	}
	
	@Test
	public void getPlayer() throws Exception {
		
		assertEquals(twoPlayerGame.getPlayer("player0").getName(), "player0");
		
	}
	
	@Test
	public void playerWhoBuiltLastEmporium() throws Exception {
		
		fourPlayerGame.setPlayerWhoBuiltLastEmporium(0);
		assertEquals(fourPlayerGame.getPlayerWhoBuiltLastEmporium(), 0);
		
	}
	
	@Test
	public void finalizeGame() throws Exception {
		
		fourPlayerGame.finalizeGame();	
		for(Player p : fourPlayerGame.getPlayers().values()){
			
			assertEquals(p.getTokens().getBuy(), 0);
			assertEquals(p.getTokens().getMain(), 0);
			assertEquals(p.getTokens().getQuick(), 0);
			assertEquals(p.getTokens().getRewardToken(), 0);
			assertEquals(p.getTokens().getSell(), 0);
			assertEquals(p.getTokens().getTakeTile(), 0);
			assertEquals(p.getTokens().getTileBonus(), 0);
			
			}
	}
	
	@Test
	public void doubleSecondPlace() throws Exception {
		
		fourPlayerGame.getPlayer("player0").nobilityAdvance();
		fourPlayerGame.getPlayer("player0").nobilityAdvance();
		fourPlayerGame.getPlayer("player1").nobilityAdvance();
		fourPlayerGame.getPlayer("player2").nobilityAdvance();
		
		int victoryPointsPre0 = fourPlayerGame.getPlayer("player0").getVictoryPoints();
		int victoryPointsPre1 = fourPlayerGame.getPlayer("player1").getVictoryPoints();
		int victoryPointsPre2 = fourPlayerGame.getPlayer("player2").getVictoryPoints();
		int victoryPointsPre3 = fourPlayerGame.getPlayer("player3").getVictoryPoints();
		
		fourPlayerGame.finalizeGame();
		
		assertEquals(fourPlayerGame.getPlayer("player0").getVictoryPoints(), victoryPointsPre0 + 5 + 3);
		assertEquals(fourPlayerGame.getPlayer("player1").getVictoryPoints(), victoryPointsPre1 + 2 + 3);
		assertEquals(fourPlayerGame.getPlayer("player2").getVictoryPoints(), victoryPointsPre2 + 2 + 3);
		assertEquals(fourPlayerGame.getPlayer("player3").getVictoryPoints(), victoryPointsPre3 + 3);
		
	}
	
	@Test
	public void doubleFirstPlace() throws Exception {
		
		fourPlayerGame.getPlayer("player0").nobilityAdvance();
		fourPlayerGame.getPlayer("player0").nobilityAdvance();
		fourPlayerGame.getPlayer("player1").nobilityAdvance();
		fourPlayerGame.getPlayer("player1").nobilityAdvance();
		fourPlayerGame.getPlayer("player2").nobilityAdvance();
		
		int victoryPointsPre0 = fourPlayerGame.getPlayer("player0").getVictoryPoints();
		int victoryPointsPre1 = fourPlayerGame.getPlayer("player1").getVictoryPoints();
		int victoryPointsPre2 = fourPlayerGame.getPlayer("player2").getVictoryPoints();
		int victoryPointsPre3 = fourPlayerGame.getPlayer("player3").getVictoryPoints();
		
		fourPlayerGame.finalizeGame();
		
		assertEquals(fourPlayerGame.getPlayer("player0").getVictoryPoints(), victoryPointsPre0 + 5 + 3);
		assertEquals(fourPlayerGame.getPlayer("player1").getVictoryPoints(), victoryPointsPre1 + 5 + 3);
		assertEquals(fourPlayerGame.getPlayer("player2").getVictoryPoints(), victoryPointsPre2 + 3);
		assertEquals(fourPlayerGame.getPlayer("player3").getVictoryPoints(), victoryPointsPre3 + 3);
		
	}
	
	@Test
	public void overrideSecondPlace() throws Exception{
		
		List<Player> sortedPlayersPre = new ArrayList<>();
		sortedPlayersPre.addAll(fourPlayerGame.getPlayers().values()); 
		
		int i = 0;
		for(Player p : sortedPlayersPre){
			
			
			
			switch(i){
			
			case 0: p.nobilityAdvance();	//player whose position is 0 has 4 nobility points
					p.nobilityAdvance();
					p.nobilityAdvance();
					p.nobilityAdvance();
					
			break;
			case 1: p.nobilityAdvance();	//player whose position is 1 has 1 nobility points
			
			break;
			case 2: p.nobilityAdvance();	//player whose position is 2 has 2 nobility points
					p.nobilityAdvance();
					
			break;
			case 3: p.nobilityAdvance();	//player whose position is 3 has 2 nobility points
					p.nobilityAdvance();
					
			break;
			
			}
			i++;
		}
		
		fourPlayerGame.finalizeGame();	
		
		boolean singleFirst = false;
		boolean doubleSecond = false;
		
		int seconds = 0;
		for(Player p : fourPlayerGame.getPlayers().values()){
			
			if(p.getVictoryPoints() == 8 && singleFirst == false)
				singleFirst = true;
			else{
			if(p.getVictoryPoints() == 8 && singleFirst == true)
				singleFirst = false;
			else{
			if(p.getVictoryPoints() == 5 && seconds == 0 && doubleSecond == false)
				seconds++;
			else{
			if(p.getVictoryPoints() == 5 && seconds == 1 && doubleSecond == false)
				doubleSecond = true;
			else{
			if(p.getVictoryPoints() == 5 && doubleSecond == true)
				doubleSecond = false;
			
		}}}}}
		
	}
	
}