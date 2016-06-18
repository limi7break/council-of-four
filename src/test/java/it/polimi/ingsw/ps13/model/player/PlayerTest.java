package it.polimi.ingsw.ps13.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;


public class PlayerTest {

    Player player;


    @Before
    public void setUp() throws Exception {

        player = new Player("player", Color.green,"green",0,null);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getVictoryPoints() throws Exception {
        //initial situation
        assertEquals(player.getVictoryPoints(),0);

        //further cases  are tested in addVictoryPoints()
    }

    @Test
    public void addVictoryPoints() throws Exception {
        player.addVictoryPoints(5);
        assertEquals(player.getVictoryPoints(),5);
        player.addVictoryPoints(5);
        assertEquals(player.getVictoryPoints(),10);

    }

    @Test
    public void getCoins() throws Exception {
        //initial situation
        assertEquals(player.getCoins(),10 + player.getID());
        //further cases  are tested in addCoins()

    }

    @Test
    public void addCoins() throws Exception {

        player.addCoins(5);
        assertEquals(player.getCoins(),10 + player.getID() + 5);

    }

    @Test
    public void consumeCoins() throws Exception {
        player.consumeCoins(5);
        assertEquals(player.getCoins(),10 + player.getID() - 5);

    }

    @Test
    public void getAssistants() throws Exception {
        assertEquals(player.getAssistants(),1 + player.getID());
    }

    @Test
    public void addAssistants() throws Exception {
        player.addAssistants(5);
        assertEquals(player.getAssistants(),1 + player.getID() + 5);

    }

    @Test
    public void consumeAssistants() throws Exception {
        player.consumeAssistants(1);
        assertEquals(player.getAssistants(),1 + player.getID() - 1);

    }

    @Test
    public void getNumberOfEmporiums() throws Exception {
        assertEquals(player.getNumberOfEmporiums(),10);
    }

    @Test
    public void removeEmporium() throws Exception {
        player.removeEmporium();
        assertEquals(player.getNumberOfEmporiums(),10 - 1);
    }

    @Test
    public void getPoliticsCards() throws Exception {
        Collection<PoliticsCard> politicsCardList = new ArrayList<>();

        assertEquals(player.getPoliticsCards(),politicsCardList);

    }

    @Test
    public void receivePoliticsCard() throws Exception {
        PoliticsCard politicsCard1 = new PoliticsCard(Color.blue,"blue");
        PoliticsCard politicsCard2 = new PoliticsCard(Color.red,"red");

        Collection<PoliticsCard> politicsCardList = new ArrayList<>();
        politicsCardList.add(politicsCard1);
        politicsCardList.add(politicsCard2);

        player.receivePoliticsCard(politicsCard1);
        player.receivePoliticsCard(politicsCard2);
        assertEquals(player.getPoliticsCards(),politicsCardList);


    }

    @Test
    public void receivePermitTile() throws Exception {
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("AA");
        PermitTile permitTile = new PermitTile(bonus,cityNames);

        Collection<PermitTile> permitTiles = new ArrayList<>();
        permitTiles.add(permitTile);

        player.receivePermitTile(permitTile);
        assertEquals(player.getPermitTiles(),permitTiles);

    }

    @Test
    public void getID() throws Exception {
        assertEquals(player.getID(),0);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(player.getName(),"player");

    }

    @Test
    public void getColor() throws Exception {
        assertEquals(player.getColor(),Color.green);

    }

    @Test
    public void getNobilityPosition() throws Exception {
        //initial situation
        assertEquals(player.getNobilityPosition(),0);
        //further cases  are tested in nobilityAdvance()

    }

    @Test
    public void nobilityAdvance() throws Exception {

        player.nobilityAdvance();
        assertEquals(player.getNobilityPosition(),1);

    }

    @Test
    public void getCityNames() throws Exception {
        Set<String> cityNames = new HashSet<>();
        assertEquals(player.getCityNames(),cityNames);

    }

    @Test
    public void hasBuiltOn() throws Exception {

        player.addCity("AA");

        assertTrue(player.hasBuiltOn("AA"));

        assertFalse(player.hasBuiltOn("BB"));

    }

    @Test
    public void addCity() throws Exception {

        // creating the known result

        Set<String> cityNames = new HashSet<>();
        assertEquals(player.getCityNames(),cityNames);
        cityNames.add("AA");

        player.addCity("AA");

        assertEquals(player.getCityNames(),cityNames);


    }

    @Test
    public void getPermitTiles() throws Exception {
        Collection<PermitTile> permitTiles = new ArrayList<>();
        assertEquals(player.getPermitTiles(),permitTiles);
    }

    @Test
    public void getTokens() throws Exception {
        ActionTokens tokens = new ActionTokens();

        assertEquals(player.getTokens().toString(),tokens.toString());
    }

    @Test
    public void getBoard() throws Exception {
        assertEquals(player.getBoard(),null);

    }

    @Test
    public void addMainActions() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setMain(1);

        player.addMainActions(1);
        assertEquals(player.getTokens().toString(),t.toString());
    }

    @Test
    public void consumeMainAction() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setMain(1);

        player.addMainActions(2);
        player.consumeMainAction();
        assertEquals(player.getTokens().toString(),t.toString());
    }

    @Test (expected =  IndexOutOfBoundsException.class)
    public void consumeQuickAction() throws  IndexOutOfBoundsException {
        player.consumeQuickAction();
    }

    @Test (expected =  IndexOutOfBoundsException.class)
    public void consumeSellAction() throws IndexOutOfBoundsException {
        player.consumeSellAction();

    }

    @Test (expected =  IndexOutOfBoundsException.class)
    public void consumeBuyAction() throws IndexOutOfBoundsException {
        player.consumeBuyAction();
    }

    @Test
    public void addTileBonusToken() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setTileBonus(1);

        player.addTileBonusToken(1);
        assertEquals(player.getTokens().toString(),t.toString());

    }

    @Test
    public void consumeTileBonusToken() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setTileBonus(1);

        player.addTileBonusToken(2);
        player.consumeTileBonusToken();
        assertEquals(player.getTokens().toString(),t.toString());
    }

    @Test (expected =  IndexOutOfBoundsException.class)
    public void consumeTileBonusTokenIllegalCall() throws IndexOutOfBoundsException {
        player.consumeTileBonusToken();
    }

    @Test
    public void addRewardTokenToken() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setRewardToken(1);

        player.addRewardTokenToken(1);
        assertEquals(player.getTokens().toString(),t.toString());

    }

    @Test
    public void consumeRewardTokenToken() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setRewardToken(1);

        player.addRewardTokenToken(2);
        player.consumeRewardTokenToken();
        assertEquals(player.getTokens().toString(),t.toString());
    }

    @Test (expected =  IndexOutOfBoundsException.class)
    public void consumeRewardTokenTokenIllegalCall() throws IndexOutOfBoundsException {
        player.consumeRewardTokenToken();
    }

    @Test
    public void addTakeTileToken() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setTakeTile(1);

        player.addTakeTileToken(1);
        assertEquals(player.getTokens().toString(),t.toString());
    }

    @Test
    public void consumeTakeTileToken() throws Exception {
        ActionTokens t = new ActionTokens();
        t.setTakeTile(1);

        player.addTakeTileToken(2);
        player.consumeTakeTileToken();
        assertEquals(player.getTokens().toString(),t.toString());
    }

    @Test
    public void isConnected() throws Exception {
        //default values
        assertTrue(player.isConnected());
    }

    @Test
    public void setConnected() throws Exception {
        player.setConnected(false);
        assertFalse(player.isConnected());
    }

    @Test (expected = UnsupportedOperationException.class)
    public void setConnectedReconnection() throws UnsupportedOperationException {
        player.setConnected(false);
        player.setConnected(true);
    }

    @Test
    public void drawPoliticsCards() throws Exception {
        // hard to test because player.board is null and instantiating a new board needs everything in the model
    }

}