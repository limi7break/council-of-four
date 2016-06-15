package it.polimi.ingsw.ps13.model.deck;

import it.polimi.ingsw.ps13.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.color.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;


public class PoliticsCardTest {

    PoliticsCard politicsCard;

    @Before
    public void setUp() throws Exception {

        politicsCard = new PoliticsCard(Color.blue,"blue");

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getColor() throws Exception {
        assertEquals(politicsCard.getColor(),Color.blue);
    }

    @Test
    public void getColorName() throws Exception {
        assertEquals(politicsCard.getColorName(),"blue");
    }

    @Test
    public void isMultiColored() throws Exception {
        Color jollyColor = new Color(6, 6, 6);
        PoliticsCard jolly = new PoliticsCard(jollyColor,"jollyColor");

        assertTrue(jolly.isMultiColored());
        assertFalse(politicsCard.isMultiColored());
    }

    @Test
    public void giveTo() throws Exception {

        //creating a player, and a collection of politics card for the comparison
        Player player = new Player("player1",Color.cyan,"cyan",10,null);
        politicsCard.giveTo(player);
        Collection<PoliticsCard> cards = new ArrayList<>();
        cards.add(politicsCard);

        assertEquals(player.getPoliticsCards(),cards);

    }

}