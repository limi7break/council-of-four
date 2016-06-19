package it.polimi.ingsw.ps13.model.deck;
import org.junit.After;
import org.junit.Test;
import java.awt.Color;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 21/05/16.
 */
public class PoliticsCardDeckTest  {

    private Collection<PoliticsCard> cards;
    private PoliticsCardDeck deck;
    private PoliticsCardDeck emptyDeck;


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void drawCard() throws Exception {

        //draw all the 2 cards in the small deck and then should return null
        createSmallDeck();
        assertFalse(deck.drawCard() == null);
        assertFalse(deck.drawCard() == null);
        assertTrue(deck.drawCard() == null);

        //a try to draw from an empty deck should return null
        createEmpityDeck();
        assertTrue(emptyDeck.drawCard() == null);

        //it should put the 2 discarded cards in the draw pile again. Draw one, and 1 card left in the drawPile
        PoliticsCard r1 = new PoliticsCard(Color.red, "red");
        PoliticsCard r2 = new PoliticsCard(Color.red, "red");
        deck.discardCard(r1);
        deck.discardCard(r2);
        assertFalse(deck.drawCard() == null);
        assertEquals(deck.drawCard().getColor(),Color.red);

    }

    @Test
    public void discardCard() throws Exception {

        createSmallDeck();

        PoliticsCard y1 = new PoliticsCard(Color.yellow, "yellow");
        PoliticsCard o1 = new PoliticsCard(Color.orange, "orange");

        //creating the test deck made of the expected cards
        cards.clear();
        PoliticsCardDeck testDeck = new PoliticsCardDeck(cards);
        testDeck.addCardToDrawPile(new PoliticsCard(Color.red, "red"));
        testDeck.addCardToDrawPile(new PoliticsCard(Color.blue, "blue"));

        //TEST CASE: initial situation: empty discard pile and the 2 cards in the draw pile
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getDiscardPile().toString(),testDeck.getDiscardPile().toString());


        deck.discardCard(y1);

        //creating the test deck made of the expected cards
        testDeck.addCardToDiscardPile(y1);

        //TEST CASE: 1 discarded card and the 2 cards in the draw pile
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getDiscardPile().toString(),testDeck.getDiscardPile().toString());


        deck.discardCard(o1);

        //creating the test deck made of the expected cards
        testDeck.addCardToDiscardPile(o1);

        //TEST CASE: 2 discarded card and the 2 cards in the draw pile
        assertEquals(deck.getDrawPile().toString(),testDeck.getDrawPile().toString());
        assertEquals(deck.getDiscardPile().toString(),testDeck.getDiscardPile().toString());

    }

    @Test
    public void isEmpty() throws Exception {

        createEmpityDeck();
        assertTrue(emptyDeck.isEmpty());
        createSmallDeck();
        assertFalse(deck.isEmpty());

    }


    private void createSmallDeck() {

        PoliticsCard r1;
        r1 = new PoliticsCard(Color.red, "red");
        PoliticsCard b1;
        b1 = new PoliticsCard(Color.blue, "blue");

        cards = new ArrayList<>();

        cards.add(r1);
        cards.add(b1);

        deck = new PoliticsCardDeck(cards);

    }


    private void createEmpityDeck(){

        Collection<PoliticsCard> emptyCards;
        emptyCards = new ArrayList<>();
        emptyDeck = new PoliticsCardDeck(emptyCards);

    }


    @SuppressWarnings("unused")
    private void createDeck() {                                 //unused for now

        PoliticsCard r1;
        PoliticsCard r2;
        PoliticsCard r3;
        PoliticsCard b1;
        PoliticsCard b2;
        PoliticsCard b3;
        PoliticsCard y1;
        PoliticsCard y2;
        PoliticsCard y3;
        PoliticsCard p1;
        PoliticsCard p2;
        PoliticsCard p3;
        PoliticsCard o1;
        PoliticsCard o2;
        PoliticsCard o3;
        PoliticsCard g1;
        PoliticsCard g2;
        PoliticsCard g3;

        r1 = new PoliticsCard(Color.red, "red");
        r2 = new PoliticsCard(Color.red, "red");
        r3 = new PoliticsCard(Color.red, "red");
        b1 = new PoliticsCard(Color.blue, "blue");
        b2 = new PoliticsCard(Color.blue, "blue");
        b3 = new PoliticsCard(Color.blue, "blue");
        y1 = new PoliticsCard(Color.yellow, "yellow");
        y2 = new PoliticsCard(Color.yellow, "yellow");
        y3 = new PoliticsCard(Color.yellow, "yellow");
        p1 = new PoliticsCard(Color.pink, "pink");
        p2 = new PoliticsCard(Color.pink, "pink");
        p3 = new PoliticsCard(Color.pink, "pink");
        o1 = new PoliticsCard(Color.orange, "orange");
        o2 = new PoliticsCard(Color.orange, "orange");
        o3 = new PoliticsCard(Color.orange, "orange");
        g1 = new PoliticsCard(Color.green, "green");
        g2 = new PoliticsCard(Color.green, "green");
        g3 = new PoliticsCard(Color.green, "green");

        cards = new ArrayList<>();

        cards.add(r1);
        cards.add(r2);
        cards.add(r3);
        cards.add(b1);
        cards.add(b2);
        cards.add(b3);
        cards.add(y1);
        cards.add(y2);
        cards.add(y3);
        cards.add(p1);
        cards.add(p2);
        cards.add(p3);
        cards.add(o1);
        cards.add(o2);
        cards.add(o3);
        cards.add(g1);
        cards.add(g2);
        cards.add(g3);

        deck = new PoliticsCardDeck(cards);

    }

    @Test
    public void toStringTest() throws Exception {
    	
    	PoliticsCard c1 = new PoliticsCard(Color.BLACK, "black");
    	PoliticsCard c2 = new PoliticsCard(Color.GREEN, "green");
    	PoliticsCard c3 = new PoliticsCard(Color.PINK, "pink");
    	
    	Collection<PoliticsCard> cards = new ArrayList<>();
    	cards.add(c1);
    	cards.add(c2);
    	cards.add(c3);
    	
    	PoliticsCardDeck deck1 = new PoliticsCardDeck(cards);
    	
    	PoliticsCardDeck deck2 = new PoliticsCardDeck(cards);
    	
    	assertEquals(deck1.toString(), deck2.toString());
    	
    }
    
}