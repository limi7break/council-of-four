package it.polimi.ingsw.ps13.controller.actions.quick;

import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.model.ColorFactory;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.RegionFactoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 */
public class EngageAssistantActionTest {

    Player player0;
    Player player1;
    Player player2;

    Document config;
    Game game;

    Board board;

    EngageAssistantAction engageAssistantAction;
    @Before
    public void setUp() throws Exception {
        final String TESTCONFIG = "configTest.xml";

        final Logger LOG = Logger.getLogger(RegionFactoryTest.class.getName());
        String testFilePath = TESTCONFIG;

        try {
            File testFile = new File(testFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            config = dBuilder.parse(testFile);
            config.getDocumentElement().normalize();
        } catch(Exception e) {
            LOG.log(Level.SEVERE, "An error occured while reading the file.", e);
        }

        Map<String, Color> colors = new LinkedHashMap<>();

        ColorFactory.createColors(config, colors);
        board = BoardFactory.createBoard(config, colors);

        player0 = new Player("player0", Color.BLACK, "black", 0, board);
        player1 = new Player("player1", Color.BLUE, "blue", 1, board);
        player2 = new Player("player2", Color.RED, "red", 2, board);

        java.util.List<String> Players = new ArrayList<>();
        Players.add(player0.getName());
        Players.add(player1.getName());
        Players.add(player2.getName());

        game = new Game(config, Players);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isLegal() throws Exception {
        engageAssistantAction = new EngageAssistantAction(game.getCurrentPlayer().getName());

        //10 coins
        //System.out.println(game.getCurrentPlayer().getCoins());
        assertTrue(engageAssistantAction.isLegal(game));
    }

    @Test
    public void isLegalPlayerHasNoToken() throws IllegalActionException {

        engageAssistantAction = new EngageAssistantAction(game.getCurrentPlayer().getName());
        game.getCurrentPlayer().consumeQuickAction();       //1 by default, 0 then

        boolean playerHasNoToken = false;
        try {
            engageAssistantAction.isLegal(game);

        } catch (IllegalActionException e) {
            playerHasNoToken = true;
        }
        assertTrue(playerHasNoToken);

    }

    @Test
    public void isLegalPlayerHasNotEnoughCoins() throws IllegalActionException {

        engageAssistantAction = new EngageAssistantAction(game.getCurrentPlayer().getName());
        game.getCurrentPlayer().consumeCoins(9);            //1 coin remaining, 3 needed

        boolean playerHasNotEnoughCoins = false;
        try {
            engageAssistantAction.isLegal(game);

        } catch (IllegalActionException e) {
            playerHasNotEnoughCoins = true;
        }
        assertTrue(playerHasNotEnoughCoins);

    }

    @Test
    public void apply() throws Exception {
        engageAssistantAction = new EngageAssistantAction(game.getCurrentPlayer().getName());

        assertEquals(game.getCurrentPlayer().getCoins(),10);
        assertEquals(game.getCurrentPlayer().getAssistants(),1);
        assertEquals(game.getCurrentPlayer().getTokens().getQuick(),1);

        engageAssistantAction.apply(game);

        assertEquals(game.getCurrentPlayer().getCoins(),7);
        assertEquals(game.getCurrentPlayer().getAssistants(),2);
        assertEquals(game.getCurrentPlayer().getTokens().getQuick(),0);

    }

}