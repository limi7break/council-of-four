package it.polimi.ingsw.ps13.controller.actions.quick;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Player;

public class ChangePermitTilesActionTest {

    Player player0;
    Player player1;
    Player player2;

    Document config;
    Game game;

    Board board;

    ChangePermitTilesAction changePermitTilesAction;

    @Before
    public void setUp() throws Exception {
        final String TESTCONFIG = "configTest.xml";

        final Logger LOG = Logger.getLogger(ChangePermitTilesActionTest.class.getName());
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
        changePermitTilesAction = new ChangePermitTilesAction(game.getCurrentPlayer().getName(),game.getBoard().getRegion("hill").getName());

        assertTrue(changePermitTilesAction.isLegal(game));
    }

    @Test
    public void isLegalPlayerHasNoToken() throws IllegalActionException {

        changePermitTilesAction = new ChangePermitTilesAction(game.getCurrentPlayer().getName(),game.getBoard().getRegion("hill").getName());
        game.getCurrentPlayer().consumeQuickAction();

        boolean playerHasNoToken = false;
        try {
            changePermitTilesAction.isLegal(game);

        } catch (IllegalActionException e) {
            playerHasNoToken = true;
        }
        assertTrue(playerHasNoToken);

    }

    @Test
    public void isLegalIllegalRegion() throws IllegalActionException {

        changePermitTilesAction = new ChangePermitTilesAction(game.getCurrentPlayer().getName(),"XXX");
        //System.out.println(game.getBoard().getRegion("hill").getName());

        boolean illigalRegion = false;
        try {
            changePermitTilesAction.isLegal(game);

        } catch (IllegalActionException e) {
            illigalRegion = true;
            //System.out.println(e.getMessage());
        }
        assertTrue(illigalRegion);

    }

    @Test
    public void isLegalNotEnoughAssistants() throws IllegalActionException {

        changePermitTilesAction = new ChangePermitTilesAction(game.getCurrentPlayer().getName(),"hill");
        //System.out.println(game.getBoard().getRegion("hill").getName());
        game.getCurrentPlayer().consumeAssistants(1);

        boolean notEnoutghAssistants = false;
        try {
            changePermitTilesAction.isLegal(game);

        } catch (IllegalActionException e) {
            notEnoutghAssistants = true;
           // System.out.println(e.getMessage());
        }
        assertTrue(notEnoutghAssistants);

    }
    @Test
    public void apply() throws Exception {
        changePermitTilesAction = new ChangePermitTilesAction(game.getCurrentPlayer().getName(),"hill");

        assertEquals(game.getCurrentPlayer().getAssistants(),1);

        assertEquals(game.getCurrentPlayer().getTokens().getQuick(),1);

        java.util.List<PermitTile> tempPT = new ArrayList<>();
        tempPT.addAll(game.getBoard().getRegion("hill").getPermitTileDeck().getVisibleTiles());

        changePermitTilesAction.apply(game);

        assertEquals(game.getCurrentPlayer().getTokens().getQuick(),0);
        assertEquals(game.getCurrentPlayer().getAssistants(),0);
       assertNotEquals(game.getBoard().getRegion("hill").getPermitTileDeck().getVisibleTiles(),tempPT);

    }

}