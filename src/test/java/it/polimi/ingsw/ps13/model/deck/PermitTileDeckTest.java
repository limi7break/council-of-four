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

    private PermitTileDeck deck;
    private PermitTileDeck emptyDeck;

    private Set <String> cityNames1;
    private Set <String> cityNames2;
    private Set <String> cityNames3;
    private Set <String> cityNames4;
    private Bonus bonus;
    private PermitTile t1;
    private PermitTile t2;
    private PermitTile t3;
    private PermitTile t4;
    private Collection<PermitTile> cards = new ArrayList<>();



    @Before
    public void setUp() throws Exception {

        cityNames1 = new HashSet<>();
        cityNames2 = new HashSet<>();
        cityNames3 = new HashSet<>();
        cityNames4 = new HashSet<>();
        bonus = BonusFactory.createEmptyBonus();
        cityNames1.add("AA");
        cityNames2.add("BB");
        cityNames3.add("CC");
        cityNames4.add("DD");
        t1 = new PermitTile(bonus,cityNames1);
        t2 = new PermitTile(bonus,cityNames2);
        t3 = new PermitTile(bonus,cityNames3);
        t4 = new PermitTile(bonus,cityNames4);

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

        assertFalse(deck.isEmpty());
        assertFalse(deck.drawCard() == null);
        assertFalse(deck.isEmpty());
        assertFalse(deck.drawCard() == null);
        assertFalse(deck.isEmpty());
        assertFalse(deck.drawCard() == null);
        assertFalse(deck.isEmpty());
        assertFalse(deck.drawCard() == null);

        //now the deck should be empty
        assertTrue(deck.isEmpty());

        //System.out.println(deck.toString());

    }

    @Test
    public void discardCard() throws Exception {

        //create a test card
        Set<String> cityNames = new HashSet<>();
        Bonus bonus = BonusFactory.createEmptyBonus();
        PermitTile t = new PermitTile(bonus,cityNames);

        //create a temporary list just for the assertEquals method
        Deque<PermitTile> temp = new LinkedList<>();
        temp.add(t);

        //TEST CASE: discard a card to an empty deck
        createEmptyDeck();
        emptyDeck.discardCard(t);
        assertEquals(temp,emptyDeck.getDrawPile());

        //initiate again the temp list
        temp.remove(t);
        Set<String> cityNames1 = new HashSet<>();
        Set<String> cityNames2 = new HashSet<>();
        Set<String> cityNames3 = new HashSet<>();
        Set<String> cityNames4 = new HashSet<>();
        cityNames1.add("AA");
        cityNames2.add("BB");
        cityNames3.add("CC");
        cityNames4.add("DD");
        PermitTile t1 = new PermitTile(bonus,cityNames1);
        PermitTile t2 = new PermitTile(bonus,cityNames2);
        PermitTile t3 = new PermitTile(bonus,cityNames3);
        PermitTile t4 = new PermitTile(bonus,cityNames4);
        temp.add(t1);
        temp.add(t2);
        temp.add(t3);
        temp.add(t4);
        temp.addLast(t);

        //TEST CASE: discard a card to a non-empty deck
        deck.discardCard(t);
        System.out.println(deck.toString());
        assertEquals(temp.toString(),deck.getDrawPile().toString()); //temporarly use toString() because of an error coming from nowhere

    }



    @Test
    public void isEmpty() throws Exception {

        assertFalse(deck.isEmpty());
        createEmptyDeck();
        assertTrue(emptyDeck.isEmpty());

    }

    @Test
    public void changeTiles() throws Exception {

        //creating the expected deck
        PermitTileDeck testDeck = new PermitTileDeck(cards);
        testDeck.setVisibleTiles(t1,t2);
        cards.add(t3);
        cards.add(t4);
        testDeck.setDrawPile(cards);

        //test case: draw pile 4, visible pile 0

        deck.changeTiles();
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getVisibleTiles().toString(),testDeck.getVisibleTiles().toString());

        //creating the expected deck
        testDeck.setVisibleTiles(t3,t4);
        testDeck.clearDrawPile();
        cards.clear();
        cards.add(t1);
        cards.add(t2);
        testDeck.setDrawPile(cards);

        // TEST CASE: draw pile 2, visible pile 2

        deck.changeTiles();
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getVisibleTiles().toString(),testDeck.getVisibleTiles().toString());

    }

    @Test
    public void takeTile() throws Exception {

        //creating the expected deck
        PermitTileDeck testDeck = new PermitTileDeck(cards);
        testDeck.setVisibleTiles(t2,t3);
        cards.add(t4);
        testDeck.setDrawPile(cards);

        //TEST CASE: draw pile 2, visible pile 2
        //NOTE: it should never happen that this function is called when the draw pile is full and the visible pile is empty
        deck.changeTiles();
        deck.takeTile(0);
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getVisibleTiles().toString(),testDeck.getVisibleTiles().toString());


        //creating the expected deck
        testDeck.setVisibleTile(t4);
        testDeck.clearDrawPile();

        //TEST CASE: draw pile 0, visible pile 2
        deck.takeTile(0);
        deck.takeTile(0);
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getVisibleTiles().toString(),testDeck.getVisibleTiles().toString());


        //creating the expected deck
        testDeck.clearVisibleTiles();

        //TEST CASE: draw pile 0, visible pile 1
        deck.takeTile(0);
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getVisibleTiles().toString(),testDeck.getVisibleTiles().toString());


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