package it.polimi.ingsw.ps13.model.council;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps13.model.deck.PoliticsCard;

/**
 * Created by Tommy on 26/05/16.
 */
@SuppressWarnings("all")
public class CouncillorBalconyTest {

    CouncillorBalcony councillorBalcony;

    @Before
    public void setUp() throws Exception {

        Councillor councillorBlack = new Councillor(Color.BLACK, "black");
        Councillor councillorWhite = new Councillor(Color.WHITE, "white");
        Councillor councillorPink = new Councillor(Color.PINK, "pink");
        Councillor councillorGreen = new Councillor(Color.GREEN, "green");
        List<Councillor> councillors = new LinkedList<>();
        councillors.add(councillorBlack);
        councillors.add(councillorWhite);
        councillors.add(councillorPink);
        councillors.add(councillorGreen);

        councillorBalcony = new CouncillorBalcony(councillors);

    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void insertCouncillor() throws Exception {

        councillorBalcony.insertCouncillor(new Councillor(Color.BLUE, "blue"));

        //creating expected result
        Councillor councillorBlue = new Councillor(Color.BLUE, "blue");
        Councillor councillorBlack = new Councillor(Color.BLACK, "black");
        Councillor councillorWhite = new Councillor(Color.WHITE, "white");
        Councillor councillorPink = new Councillor(Color.PINK, "pink");
        List<Councillor> councillors = new LinkedList<>();
        councillors.add(councillorBlue);
        councillors.add(councillorBlack);
        councillors.add(councillorWhite);
        councillors.add(councillorPink);

        assertEquals(councillorBalcony.getCouncillors().toString(),councillors.toString());

    }

    @Test
    public void calculateNumberOfMatches() throws Exception {

        //create some politics cards for the testing
        PoliticsCardTest b = new PoliticsCardTest(Color.BLUE, "blue");
        PoliticsCardTest black = new PoliticsCardTest(Color.BLACK, "black");
        PoliticsCardTest w = new PoliticsCardTest(Color.WHITE, "white");
        PoliticsCardTest p = new PoliticsCardTest(Color.PINK, "pink");
        PoliticsCardTest g = new PoliticsCardTest(Color.GREEN, "green");
        PoliticsCardTest multi = new PoliticsCardTest(PoliticsCardTest.jollyColor, "JOLLY");

        List<PoliticsCardTest> c = new ArrayList<>();
        
        //TEST CASE: 0 cards
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),0);

        c.add(b);
        c.add(b);
        //TEST CASE: 0 match, <4 cards
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),0);

        c.add(b);
        c.add(b);
        //TEST CASE: 0 match, 4 cards
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),0);

        c.add(b);
        c.add(b);
        //TEST CASE: 0 match, >4 cards
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),0);
        c.clear();

        c.add(black);
        c.add(w);
        c.add(g);
        c.add(p);
        //TEST CASE: all match perfectly
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),4);

        c.add(black);
        //TEST CASE: >4 matching cards      | this should never happen
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),4);

        c.remove(black);
        c.remove(black);
        //TEST CASE: 3 cards, 3 matches
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),3);

        c.add(b);
        //TEST CASE: 4 cards, 3 matches
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),3);


        c.clear();
        c.add(multi);
        c.add(multi);
        c.add(multi);
        c.add(multi);
        //TEST CASE: 4 multicolored cards  | this should never happen
        assertEquals(councillorBalcony.calculateNumberOfMatches(c),0);


        //creating an alternative councillorBalcony that has the same color councillor 3 times
        Councillor councillorBlack = new Councillor(Color.BLACK, "black");
        Councillor councillorWhite = new Councillor(Color.WHITE, "white");
        List<Councillor> councillors = new LinkedList<>();
        councillors.add(councillorBlack);
        councillors.add(councillorWhite);
        councillors.add(councillorWhite);
        councillors.add(councillorWhite);

        CouncillorBalcony cb = new CouncillorBalcony(councillors);

        c.clear();
        c.add(w);
        //TEST CASE: 1 matching card, but 3 councillor of the same color. It should count it just once.
        assertEquals(cb.calculateNumberOfMatches(c),1);

        c.add(w);
        //TEST CASE: 2 matching card, but 3 councillor of the same color. It should count it just twice.
        assertEquals(cb.calculateNumberOfMatches(c),2);

    }

    @Test
    public void calculateNumberOfMulticoloredCards() throws Exception {

        //create some politics cards for the testing
        PoliticsCardTest b = new PoliticsCardTest(Color.BLUE, "blue");
        PoliticsCardTest multi = new PoliticsCardTest(PoliticsCard.jollyColor, "JOLLY");

        List<PoliticsCardTest> c = new ArrayList<>();

        //TEST CASE: 0 cards
        assertEquals(councillorBalcony.calculateNumberOfMulticoloredCards(c),0);

        c.add(b);
        c.add(b);
        //TEST CASE: 2 cards, 0 matches
        assertEquals(councillorBalcony.calculateNumberOfMulticoloredCards(c),0);

        c.add(multi);
        c.add(multi);
        //TEST CASE: 4 cards, 2 matches
        assertEquals(councillorBalcony.calculateNumberOfMulticoloredCards(c),2);

        c.remove(b);
        c.remove(b);
        c.add(multi);
        c.add(multi);
        //TEST CASE: 4 cards, 4 matches
        assertEquals(councillorBalcony.calculateNumberOfMulticoloredCards(c),4);

    }

    @Test
    public void isSatisfiable() {

        //create some politics cards for the testing
        PoliticsCardTest b = new PoliticsCardTest(Color.BLUE, "blue");
        PoliticsCardTest black = new PoliticsCardTest(Color.BLACK, "black");
        PoliticsCardTest w = new PoliticsCardTest(Color.WHITE, "white");
        PoliticsCardTest p = new PoliticsCardTest(Color.PINK, "pink");
        PoliticsCardTest g = new PoliticsCardTest(Color.GREEN, "green");
        PoliticsCardTest multi = new PoliticsCardTest(PoliticsCard.jollyColor, "JOLLY");

        List<PoliticsCardTest> c = new ArrayList<>();

        c.add(b);
        //TEST CASE: 0 matching cards 0 coins
        assertFalse(councillorBalcony.isSatisfiable(c,0));

        c.clear();
        c.add(g);
        //TEST CASE: 1 matching cards 0 coins
        assertFalse(councillorBalcony.isSatisfiable(c,0));

        //TEST CASE: 1 matching cards 10 coins
        assertTrue(councillorBalcony.isSatisfiable(c,10));

        c.add(w);
        //TEST CASE: 2 matching cards 7 coins
        assertTrue(councillorBalcony.isSatisfiable(c,7));

        c.add(b);
        //TEST CASE: 3 cards, 2 matching cards
        assertFalse(councillorBalcony.isSatisfiable(c,0));
        assertFalse(councillorBalcony.isSatisfiable(c,10)); //no matter how much coins

        c.remove(b);
        c.add(black);
        c.add(p);
        //TEST CASE: 4 matching cards
        assertTrue(councillorBalcony.isSatisfiable(c,0));

        c.clear();
        c.add(g);
        c.add(g);
        c.add(w);
        c.add(black);
        //TEST CASE: 3 matching cards out of 4, 1 is double, should be counted just once
        assertFalse(councillorBalcony.isSatisfiable(c,0));
        assertFalse(councillorBalcony.isSatisfiable(c,10)); //no matter how much coins

        c.clear();
        c.add(multi);
        //TEST CASE: 1 multicolored card
        assertFalse(councillorBalcony.isSatisfiable(c,10));
        assertTrue(councillorBalcony.isSatisfiable(c,11));


    }

    @Test (expected = IllegalArgumentException.class)
    public void isSatisfiableWrongNumberOfCards() throws IllegalArgumentException {

        //create some politics cards for the testing
    	@SuppressWarnings("unused")
        PoliticsCardTest b = new PoliticsCardTest(Color.BLUE, "blue");

        List<PoliticsCardTest> c = new ArrayList<>();

        //TEST CASE: 0 cards
        assertFalse(councillorBalcony.isSatisfiable(c,0));

        //TEST CASE: 5 cards
        assertFalse(councillorBalcony.isSatisfiable(c,0));

    }

    @Test
    public void coinsToPay() throws Exception {

        //create some politics cards for the testing
        PoliticsCardTest b = new PoliticsCardTest(Color.BLUE, "blue");
        PoliticsCardTest black = new PoliticsCardTest(Color.BLACK, "black");
        PoliticsCardTest w = new PoliticsCardTest(Color.WHITE, "white");
        @SuppressWarnings("unused")
        PoliticsCardTest p = new PoliticsCardTest(Color.PINK, "pink");
        PoliticsCardTest g = new PoliticsCardTest(Color.GREEN, "green");
        PoliticsCardTest multi = new PoliticsCardTest(PoliticsCard.jollyColor, "JOLLY");

        List<PoliticsCardTest> c = new ArrayList<>();

        c.add(g);
        //TEST CASE: 1 matching card
        assertEquals(councillorBalcony.coinsToPay(c),10);

        c.add(w);
        //TEST CASE: 2 matching cards
        assertEquals(councillorBalcony.coinsToPay(c),7);

        c.add(b);
        //TEST CASE: 3 cards, 2 matching cards
        assertEquals(councillorBalcony.coinsToPay(c),4);

        c.add(black);
        //TEST CASE: 3 cards, 2 matching cards
        assertEquals(councillorBalcony.coinsToPay(c),0);

        c.clear();
        c.add(multi);
        //TEST CASE: 1 multicolored card
        assertEquals(councillorBalcony.coinsToPay(c),11);

        c.add(multi);
        c.add(multi);
        c.add(multi);
        //TEST CASE: 4 multicolored cards
        assertEquals(councillorBalcony.coinsToPay(c),4);

    }

    @Test
    public void getCouncillors() throws Exception {

        //creating the expected list of councillors
        Councillor councillorBlack = new Councillor(Color.BLACK, "black");
        Councillor councillorWhite = new Councillor(Color.WHITE, "white");
        Councillor councillorPink = new Councillor(Color.PINK, "pink");
        Councillor councillorGreen = new Councillor(Color.GREEN, "green");
        List<Councillor> c = new LinkedList<>();
        c.add(councillorBlack);
        c.add(councillorWhite);
        c.add(councillorPink);
        c.add(councillorGreen);

        assertEquals(councillorBalcony.getCouncillors().toString(),c.toString());

    }

    @SuppressWarnings("unused")
    private void createEmptyCouncillorBalcony(){                                    //unused for now

        List<Councillor> councillors = new LinkedList<>();
        CouncillorBalcony emptyCouncillorBalcony = new CouncillorBalcony(councillors);

    }

}