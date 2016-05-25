package it.polimi.ingsw.ps13.model.deck;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 24/05/16.
 */
public class PermitTileDeckTest {

    PermitTileDeck deck;
    PermitTileDeck emptyDeck;


    @Before
    public void setUp() throws Exception {

        createDeck();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test (expected = NoSuchElementException.class)
    public void drawCardFromEmptyDeck() throws NoSuchElementException {

        createEmptyDeck();
        //TEST CASE: empty deck
        //it should throw NoSuchElementException
        assertTrue(emptyDeck.drawCard() == null);

    }

    @Test
    public void drawCard(){

        assertFalse(deck.drawCard() == null);
        assertFalse(deck.drawCard() == null);
        assertFalse(deck.drawCard() == null);
        assertFalse(deck.drawCard() == null);
        //now the deck should be empty
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Empty as my wallet.\n" +
                "\n" +
                "Visible Tiles:\n");

        System.out.println(deck.toString());

    }

    @Test
    public void discardCard() throws Exception {

        //create a test card
        Set<String> cityNames = new HashSet<>();
        Bonus bonus = BonusFactory.createEmptyBonus();
        PermitTile t = new PermitTile(bonus,cityNames);

        //TEST CASE: discard a card to an empty deck
        createEmptyDeck();
        emptyDeck.discardCard(t);
        assertEquals(emptyDeck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Draw Pile:\n" +
                "[PermitTile]\n" +
                "Cities: []\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "\n" +
                "Visible Tiles:\n");

        //TEST CASE: discard a card to a full deck

        deck.discardCard(t);
        System.out.println(deck.toString());
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Draw Pile:\n" +
                "[PermitTile]\n" +
                "Cities: [AA]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [BB]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [CC]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [DD]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: []\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "\n" +
                "Visible Tiles:\n");


    }

    @Test
    public void isEmpty() throws Exception {

        assertFalse(deck.isEmpty());
        createEmptyDeck();
        assertTrue(emptyDeck.isEmpty());

    }

    @Test
    public void changeTiles() throws Exception {

        //test case: draw pile 4, visible pile 0

        deck.changeTiles();
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Draw Pile:\n" +
                "[PermitTile]\n" +
                "Cities: [CC]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [DD]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "\n" +
                "Visible Tiles:\n" +
                "[PermitTile]\n" +
                "Cities: [AA]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [BB]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n");


         // test case: draw pile 2, visible pile 2

        deck.changeTiles();
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Draw Pile:\n" +
                "[PermitTile]\n" +
                "Cities: [AA]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [BB]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "\n" +
                "Visible Tiles:\n" +
                "[PermitTile]\n" +
                "Cities: [CC]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [DD]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n");

        System.out.println(deck.toString());



    }

    @Test
    public void takeTile() throws Exception {

        //TEST CASE: draw pile 2, visible pile 2
        //NOTE: it should never happen that this function is called when the draw pile is full and the visible pile is empty
        deck.changeTiles();
        deck.takeTile(0);
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Draw Pile:\n" +
                "[PermitTile]\n" +
                "Cities: [DD]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "\n" +
                "Visible Tiles:\n" +
                "[PermitTile]\n" +
                "Cities: [BB]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n" +
                "[PermitTile]\n" +
                "Cities: [CC]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n");

        //TEST CASE: draw pile 0, visible pile 2
        deck.takeTile(0);
        deck.takeTile(0);
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Draw Pile:\n" +
                "Empty as my brain right now.\n" +
                "\n" +
                "Visible Tiles:\n" +
                "[PermitTile]\n" +
                "Cities: [DD]\n" +
                "Bonus:\n" +
                "Wow! It's fucking nothing!\n" +
                "\n" +
                "used = false\n" +
                "\n");

        //TEST CASE: draw pile 0, visible pile 1
        deck.takeTile(0);
        assertEquals(deck.toString(),"[PermitTileDeck]\n" +
                "\n" +
                "Empty as my wallet.\n" +
                "\n" +
                "Visible Tiles:\n");



    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void takeTileFromEmptyDeck() throws Exception {

        createEmptyDeck();
        assertTrue(emptyDeck.takeTile(0) == null);

    }

    private void createDeck(){

        Set<String> cityNames1 = new HashSet<>();
        Set<String> cityNames2 = new HashSet<>();
        Set<String> cityNames3 = new HashSet<>();
        Set<String> cityNames4 = new HashSet<>();
        cityNames1.add("AA");
        cityNames2.add("BB");
        cityNames3.add("CC");
        cityNames4.add("DD");
        Bonus bonus = BonusFactory.createEmptyBonus();
        PermitTile t1 = new PermitTile(bonus,cityNames1);
        PermitTile t2 = new PermitTile(bonus,cityNames2);
        PermitTile t3 = new PermitTile(bonus,cityNames3);
        PermitTile t4 = new PermitTile(bonus,cityNames4);
        Collection<PermitTile> cards = new ArrayList<>();
        cards.add(t1);
        cards.add(t2);
        cards.add(t3);
        cards.add(t4);
        deck = new PermitTileDeck(cards);
    }

    private void createEmptyDeck(){

        Collection<PermitTile> cards = new ArrayList<>();
        emptyDeck = new PermitTileDeck(cards);

    }

}