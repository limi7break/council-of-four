package it.polimi.ingsw.ps13.controller.actions.quick;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.Game;

public class GainMainActionActionTest {

	private static final String testFilePath = "configTest.xml";
	private static final Logger LOG = Logger.getLogger(GainMainActionActionTest.class.getName());
	Document config;
	
	Game game;
	GainMainActionAction action;
	
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
		
		List<String> players = new ArrayList<>();
		players.add("player0");
		players.add("player1");
		
		game = new Game(config, players);
		
		action = new GainMainActionAction(game.getCurrentPlayerName());
		
	}
	
	@Test
	public void noTokens() throws Exception {
		
		game.getCurrentPlayer().getTokens().setQuick(0);
		
		boolean thrown = false;
		try {
			action.isLegal(game);
		} catch(IllegalActionException e) {
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void noAssistants() throws Exception {
		
		// In the first turn of a two player game both players have less than 3 assistants, so
		// we can immediately proceed with the check
		
		boolean thrown = false;
		try {
			action.isLegal(game);
		} catch(IllegalActionException e) {
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void isLegal() throws Exception {
		
		game.getCurrentPlayer().addAssistants(2);
		
		boolean isLegal = action.isLegal(game);
		assertTrue(isLegal);
		
	}

	@Test
	public void apply() throws Exception {
		
		// The first player of the game should have:
		//		- 1 main action token
		//		- 1 quick action token
		//		- 1 assistant
		
		game.getCurrentPlayer().addAssistants(2); // Now current player has 3 assistants and should be able to perform the action
		
		action.apply(game);
		
		assertEquals(game.getCurrentPlayer().getTokens().getMain(), 2);
		assertEquals(game.getCurrentPlayer().getTokens().getQuick(), 0);
		assertEquals(game.getCurrentPlayer().getAssistants(), 0);
		
	}
	
	@After
	public void tearDown() throws Exception { }

}
