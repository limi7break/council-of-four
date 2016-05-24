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

        //it should put the 2 discarded cards in the draw pile again: draw one then 1 left in the drawPile
        PoliticsCard r1 = new PoliticsCard(Color.red);
        PoliticsCard r2 = new PoliticsCard(Color.red);
        deck.discardCard(r1);
        deck.discardCard(r2);
        assertFalse(deck.drawCard() == null);
        assertEquals(deck.toString(),"[PoliticsCardDeck]\n\nDraw Pile:\n[PoliticsCard] (255, 0, 0)\n\nDiscard Pile:\n");

    }

    @Test
    public void discardCard() throws Exception {

        createSmallDeck();
        PoliticsCard y1 = new PoliticsCard(Color.yellow);
        PoliticsCard o1 = new PoliticsCard(Color.orange);
        System.out.println(deck.toString());
        assertEquals(deck.toString(),"[PoliticsCardDeck]\n\nDraw Pile:\n[PoliticsCard] (255, 0, 0)\n[PoliticsCard] (0, 0, 255)\n\nDiscard Pile:\n");
        deck.discardCard(y1);
        System.out.println(deck.toString());
        assertEquals(deck.toString(),"[PoliticsCardDeck]\n\nDraw Pile:\n[PoliticsCard] (255, 0, 0)\n[PoliticsCard] (0, 0, 255)\n\nDiscard Pile:\n[PoliticsCard] (255, 255, 0)\n");
        deck.discardCard(o1);
        assertEquals(deck.toString(),"[PoliticsCardDeck]\n\nDraw Pile:\n[PoliticsCard] (255, 0, 0)\n[PoliticsCard] (0, 0, 255)\n\nDiscard Pile:\n[PoliticsCard] (255, 255, 0)\n[PoliticsCard] (255, 200, 0)\n");
        System.out.println(deck.toString());

    }

    @Test
    public void isEmpty() throws Exception {

        createEmpityDeck();
        assertTrue(emptyDeck.isEmpty());
        createSmallDeck();
        assertFalse(deck.isEmpty());

    }


    private void createSmallDeck() {

         PoliticsCard r1 = new PoliticsCard(Color.red);
         PoliticsCard b1 = new PoliticsCard(Color.blue);

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

        r1 = new PoliticsCard(Color.red);
        r2 = new PoliticsCard(Color.red);
        r3 = new PoliticsCard(Color.red);
        b1 = new PoliticsCard(Color.blue);
        b2 = new PoliticsCard(Color.blue);
        b3 = new PoliticsCard(Color.blue);
        y1 = new PoliticsCard(Color.yellow);
        y2 = new PoliticsCard(Color.yellow);
        y3 = new PoliticsCard(Color.yellow);
        p1 = new PoliticsCard(Color.pink);
        p2 = new PoliticsCard(Color.pink);
        p3 = new PoliticsCard(Color.pink);
        o1 = new PoliticsCard(Color.orange);
        o2 = new PoliticsCard(Color.orange);
        o3 = new PoliticsCard(Color.orange);
        g1 = new PoliticsCard(Color.green);
        g2 = new PoliticsCard(Color.green);
        g3 = new PoliticsCard(Color.green);

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


}