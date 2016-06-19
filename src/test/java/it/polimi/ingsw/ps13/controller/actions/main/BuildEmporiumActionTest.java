package it.polimi.ingsw.ps13.controller.actions.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;

public class BuildEmporiumActionTest {

    BuildEmporiumAction buildEmporiumAction;

    Player player0;
    Player player1;
    Player player2;

    Document config;
    Game game;

    Board board;


    @Before
    public void setUp() throws Exception {

        final String TESTCONFIG = "configTest.xml";

        final Logger LOG = Logger.getLogger(BuildEmporiumActionTest.class.getName());
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

        List<String> Players = new ArrayList<>();
        Players.add(player0.getName());
        Players.add(player1.getName());
        Players.add(player2.getName());


        game = new Game(config, Players);



    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isLegalPlayerHasNoToken() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        game.getCurrentPlayer().consumeMainAction();

        boolean playerHasNoTokens = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            playerHasNoTokens = true;
        }
        assertTrue(playerHasNoTokens);

    }

    @Test
    public void isLegalPlayerHasntTheTile() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        boolean playerHasntTheTile = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            playerHasntTheTile = true;
        }
        assertTrue(playerHasntTheTile);

    }

    @Test
    public void isLegalPlayerHasNoMoreEmporiums() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        for (int i = 0; i<10; i++){
            game.getCurrentPlayer().removeEmporium();
        }

        boolean playerHasNoMoreEmporiums = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            System.out.println(e.getMessage());
            playerHasNoMoreEmporiums = true;
        }
        assertTrue(playerHasNoMoreEmporiums);

    }

    @Test
    public void isLegalNonValidCity() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"AAAA");

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        //the city should not be valid even if it has a corresponding name in the permit tile!
        cityNames.add("AAAA");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        boolean nonValidCity = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            System.out.println(e.getMessage());
            nonValidCity = true;
        }
        assertTrue(nonValidCity);

    }

    @Test
    public void isLegalPlayerHasAlreadyBuilt() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        game.getCurrentPlayer().addCity("Osium");



        boolean playerHasAlreadyBuilt = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            System.out.println(e.getMessage());
            playerHasAlreadyBuilt = true;
        }
        assertTrue(playerHasAlreadyBuilt);

    }

    @Test
    public void isLegalNonMatchingCity() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        //the city should not be valid even if it has a corresponding name in the permit tile!
        cityNames.add("AAAA");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        boolean nonMatchingCity = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            System.out.println(e.getMessage());
            nonMatchingCity = true;
        }
        assertTrue(nonMatchingCity);

    }

    @Test
    public void isLegalPlayerHasNotEnoughAssistants() throws IllegalActionException {

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        game.getBoard().getCity("Osium").addEmporium(new Emporium(Color.green));
        game.getBoard().getCity("Osium").addEmporium(new Emporium(Color.yellow));
        game.getBoard().getCity("Osium").addEmporium(new Emporium(Color.blue));
        game.getBoard().getCity("Osium").addEmporium(new Emporium(Color.orange));

        boolean playerHasNotEnoughAssistants = false;
        try {
            buildEmporiumAction.isLegal(game);
        }
        catch (IllegalActionException e){
            System.out.println(e.getMessage());
            playerHasNotEnoughAssistants = true;
        }
        assertTrue(playerHasNotEnoughAssistants);

    }

    @Test
    public void isLegal() throws IllegalActionException {

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        assertTrue(buildEmporiumAction.isLegal(game));


    }

    @Test
    public void apply() throws Exception {

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        System.out.println(game.getBoard().getCity("Osium").getEmporiums());
        assertEquals(game.getBoard().getCity("Osium").getEmporiums().size(),0);

        buildEmporiumAction.apply(game);

        assertEquals(game.getBoard().getCity("Osium").getEmporiums().size(),1);

    }

    @Test
    public void applyPayingAssistants() throws Exception {

        //creating a permit tile
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");

        game.getBoard().getCity("Osium").addEmporium(new Emporium(Color.orange));
        game.getBoard().getCity("Osium").addEmporium(new Emporium(Color.yellow));

        game.getCurrentPlayer().addAssistants(1);

        assertEquals(game.getCurrentPlayer().getAssistants(),2);

        buildEmporiumAction.apply(game);

        assertEquals(game.getBoard().getCity("Osium").getEmporiums().size(),3);

    }

    @Test
    public void completeRegion() throws Exception {
    	
    	Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");
        
        for(City c : game.getBoard().getCities().values()){
			
			if(c.getRegion().getName().equals("mountain") && !c.getName().equals("Osium")){
				c.addEmporium(game.getCurrentPlayer().removeEmporium());
				game.getCurrentPlayer().addCity(c.getName());
			}
				
		}
        
        assertTrue(game.getBoard().getRegion("mountain").isBonusAvailable());
        buildEmporiumAction.apply(game);
        assertFalse(game.getBoard().getRegion("mountain").isBonusAvailable());
          
    }
        
    @Test
    public void completeCityColor() throws Exception {
    	
    	Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");
        
        CityColor osiums = game.getBoard().getCity("Osium").getCityColor();
        for(City c : game.getBoard().getCities().values()){
			
			if(c.getCityColor().equals(osiums) && !c.getName().equals("Osium")){
				c.addEmporium(game.getCurrentPlayer().removeEmporium());
				game.getCurrentPlayer().addCity(c.getName());
			}
				
		}
        
        assertTrue(game.getBoard().getCityColor(osiums.getColorName()).isBonusAvailable());
        buildEmporiumAction.apply(game);
        assertFalse(game.getBoard().getCityColor(osiums.getColorName()).isBonusAvailable());
          
    }
    
    @Test
	public void buildLastEmporium() throws Exception {
		
    	Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("Osium");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        game.getCurrentPlayer().receivePermitTile(permitTile);

        buildEmporiumAction = new BuildEmporiumAction(game.getCurrentPlayer().getName(),0,"Osium");
        
        while(game.getCurrentPlayer().getNumberOfEmporiums()!=1){	
			game.getCurrentPlayer().removeEmporium();
		}
    	
        buildEmporiumAction.apply(game);
        assertEquals(game.getPlayerWhoBuiltLastEmporium(), game.getCurrentPlayerID());
        
	}
    
}  