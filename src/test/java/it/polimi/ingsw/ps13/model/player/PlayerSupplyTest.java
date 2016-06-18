package it.polimi.ingsw.ps13.model.player;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;


public class PlayerSupplyTest {

    private PlayerSupply supply;
    private static final int NUMBER_OF_EMPORIUMS = 10;

    @Before
    public void setUp() throws Exception {
        supply = new PlayerSupply(0, Color.green);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getVictoryPoints() throws Exception {
        //initial situation
        assertEquals(supply.getVictoryPoints().getAmount(),0);
    }

    @Test
    public void getCoins() throws Exception {
        //initial situation
        assertEquals(supply.getCoins().getAmount(),10);
    }

    @Test
    public void getAssistants() throws Exception {
        //initial situation
        assertEquals(supply.getAssistants().getAmount(),1);
    }

    @Test
    public void getEmporiums() throws Exception {

        //creating the expected list of emporiums
        List<Emporium> emporiums = new ArrayList<>();
        for (int i=0; i<NUMBER_OF_EMPORIUMS; i++) {
            emporiums.add(new Emporium(Color.green));
        }

        //initial situation
        assertEquals(supply.getEmporiums().toString(),emporiums.toString());
    }

    @Test
    public void getPoliticsCards() throws Exception {
        List<PoliticsCard> politicsCards = new ArrayList<>();
        //initial situation
        assertEquals(supply.getPoliticsCards(),politicsCards);
    }

    @Test
    public void getPermitTiles() throws Exception {
        List<PermitTile> permitTiles = new ArrayList<>();
        //initial situation
        assertEquals(supply.getPermitTiles(),permitTiles);
    }

}