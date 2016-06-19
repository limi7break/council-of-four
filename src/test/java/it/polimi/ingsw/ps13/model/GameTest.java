package it.polimi.ingsw.ps13.model;

import static org.junit.Assert.assertEquals;
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
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.Region;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;

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
	
	Game game;
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
		
		List<String> twoPlayers = new ArrayList<>();
		twoPlayers.add(player0.getName());
		twoPlayers.add(player1.getName());

		
		game = new Game(config, fourPlayers);
		twoPlayerGame = new Game(config, twoPlayers);
		
	}
	
	@After
	public void tearDown() throws Exception {}
	
	@Test
	public void getConnectedPlayers() throws Exception {
		
		assertEquals(game.getConnectedPlayers(), 4);
		
	}
	
	
	@Test
	public void getBoard() throws Exception {
			
		for(String city : board.getCities().keySet()){
			assertTrue(game.getBoard().getCities().keySet().contains(city));
			}
		
		assertTrue(game.getBoard().getCouncillors().size() == board.getCouncillors().size());
		
		for(String region : board.getRegions().keySet()){
				assertTrue(game.getBoard().getRegions().keySet().contains(region));
			}
		
		assertEquals(board.getKingRewardTiles().size(), game.getBoard().getKingRewardTiles().size());
		assertEquals(board.getKingBalcony().getCouncillors().size(), game.getBoard().getKingBalcony().getCouncillors().size());
		
		for(Region r : board.getRegions().values()){
				for(Region rg : game.getBoard().getRegions().values()){
				
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
		
		Player preCurrentPlayer = game.getCurrentPlayer();
		int preCurrentPlayerID = game.getCurrentPlayerID();
		int preNextPlayerID = game.getNextPlayerID(preCurrentPlayerID);
		game.passTurn();
		assertEquals(game.getCurrentPlayerID(), preNextPlayerID);
		assertEquals(preCurrentPlayer.getTokens().getBuy(), 0);
		assertEquals(preCurrentPlayer.getTokens().getMain(), 0);
		assertEquals(preCurrentPlayer.getTokens().getQuick(), 0);
		assertEquals(preCurrentPlayer.getTokens().getRewardToken(), 0);
		assertEquals(preCurrentPlayer.getTokens().getSell(), 0);
		assertEquals(preCurrentPlayer.getTokens().getTakeTile(), 0);
		assertEquals(preCurrentPlayer.getTokens().getTileBonus(), 0);
		
	}
	
	@Test
	public void isCouncillorAvailable() throws Exception {
		
		for(Councillor c : game.getBoard().getCouncillors()){	
			assertTrue(game.isCouncillorAvailable(c.getColor()));	
		}	
	}
	
	@Test
	public void getColorName() throws Exception {
		
		for(Map.Entry<String, Color> entry : game.getColors().entrySet()){	
			assertEquals(game.getColorName(entry.getValue()), entry.getKey());	
		}	
	}
	
	@Test
	public void getPlayer() throws Exception {
		
		assertEquals(twoPlayerGame.getPlayer("player0").getName(), "player0");
		
	}
	
	/**@Test
	public void playerWhoBuiltLastEmporium() throws Exception {
		
		game.setPlayerWhoBuiltLastEmporium(0);
		assertEquals(game.getPlayerWhoBuiltLastEmporium(), 0);
		
	}
	*/
	/**@Test
	public void finalizeGame() throws Exception {
		
		game.finalizeGame();	
		for(Player p : game.getPlayers().values()){
			
			assertEquals(p.getTokens().getBuy(), 0);
			assertEquals(p.getTokens().getMain(), 0);
			assertEquals(p.getTokens().getQuick(), 0);
			assertEquals(p.getTokens().getRewardToken(), 0);
			assertEquals(p.getTokens().getSell(), 0);
			assertEquals(p.getTokens().getTakeTile(), 0);
			assertEquals(p.getTokens().getTileBonus(), 0);
			
			}
	}
	*/
	@Test
	public void doubleSecondPlace() throws Exception {
		
		game.getPlayer("player0").nobilityAdvance();
		game.getPlayer("player0").nobilityAdvance();
		game.getPlayer("player1").nobilityAdvance();
		game.getPlayer("player2").nobilityAdvance();
		
		int victoryPointsPre0 = game.getPlayer("player0").getVictoryPoints();
		int victoryPointsPre1 = game.getPlayer("player1").getVictoryPoints();
		int victoryPointsPre2 = game.getPlayer("player2").getVictoryPoints();
		int victoryPointsPre3 = game.getPlayer("player3").getVictoryPoints();
		
		game.finalizeGame();
		
		assertEquals(game.getPlayer("player0").getVictoryPoints(), victoryPointsPre0 + 5 + 3);
		assertEquals(game.getPlayer("player1").getVictoryPoints(), victoryPointsPre1 + 2 + 3);
		assertEquals(game.getPlayer("player2").getVictoryPoints(), victoryPointsPre2 + 2 + 3);
		assertEquals(game.getPlayer("player3").getVictoryPoints(), victoryPointsPre3 + 3);
		
	}
	
	@Test
	public void doubleFirstPlace() throws Exception {
		
		game.getPlayer("player0").nobilityAdvance();
		game.getPlayer("player0").nobilityAdvance();
		game.getPlayer("player1").nobilityAdvance();
		game.getPlayer("player1").nobilityAdvance();
		game.getPlayer("player2").nobilityAdvance();
		
		int victoryPointsPre0 = game.getPlayer("player0").getVictoryPoints();
		int victoryPointsPre1 = game.getPlayer("player1").getVictoryPoints();
		int victoryPointsPre2 = game.getPlayer("player2").getVictoryPoints();
		int victoryPointsPre3 = game.getPlayer("player3").getVictoryPoints();
		
		game.finalizeGame();
		
		assertEquals(game.getPlayer("player0").getVictoryPoints(), victoryPointsPre0 + 5 + 3);
		assertEquals(game.getPlayer("player1").getVictoryPoints(), victoryPointsPre1 + 5 + 3);
		assertEquals(game.getPlayer("player2").getVictoryPoints(), victoryPointsPre2 + 3);
		assertEquals(game.getPlayer("player3").getVictoryPoints(), victoryPointsPre3 + 3);
		
	}
	
	@Test
	public void overrideSecondPlace() throws Exception{
		
		List<Player> sortedPlayersPre = new ArrayList<>();
		sortedPlayersPre.addAll(game.getPlayers().values()); 
		
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
		
		game.finalizeGame();	
		
		boolean singleFirst = false;
		boolean doubleSecond = false;
		
		int seconds = 0;
		for(Player p : game.getPlayers().values()){
			
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