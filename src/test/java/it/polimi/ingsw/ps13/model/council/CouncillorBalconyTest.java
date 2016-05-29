package it.polimi.ingsw.ps13.model.council;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 26/05/16.
 */
public class CouncillorBalconyTest {

    CouncillorBalcony councillorBalcony;

    @Before
    public void setUp() throws Exception {

        Councillor councillorBlack = new Councillor(Color.BLACK);
        Councillor councillorWhite = new Councillor(Color.WHITE);
        Councillor councillorPink = new Councillor(Color.PINK);
        Councillor councillorGreen = new Councillor(Color.GREEN);
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

        councillorBalcony.insertCouncillor(new Councillor(Color.BLUE));

        //creating expected result
        Councillor councillorBlue = new Councillor(Color.BLUE);
        Councillor councillorBlack = new Councillor(Color.BLACK);
        Councillor councillorWhite = new Councillor(Color.WHITE);
        Councillor councillorPink = new Councillor(Color.PINK);
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
        PoliticsCard b = new PoliticsCard(Color.BLUE);
        PoliticsCard black = new PoliticsCard(Color.BLACK);
        PoliticsCard w = new PoliticsCard(Color.WHITE);
        PoliticsCard p = new PoliticsCard(Color.PINK);
        PoliticsCard g = new PoliticsCard(Color.GREEN);
        PoliticsCard multi = new PoliticsCard(null);

        Collection<PoliticsCard> c = new ArrayList<>();

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
        Councillor councillorBlack = new Councillor(Color.BLACK);
        Councillor councillorWhite = new Councillor(Color.WHITE);
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
        PoliticsCard b = new PoliticsCard(Color.BLUE);
        PoliticsCard multi = new PoliticsCard(null);

        Collection<PoliticsCard> c = new ArrayList<>();

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
        PoliticsCard b = new PoliticsCard(Color.BLUE);
        PoliticsCard black = new PoliticsCard(Color.BLACK);
        PoliticsCard w = new PoliticsCard(Color.WHITE);
        PoliticsCard p = new PoliticsCard(Color.PINK);
        PoliticsCard g = new PoliticsCard(Color.GREEN);
        PoliticsCard multi = new PoliticsCard(null);

        Collection<PoliticsCard> c = new ArrayList<>();

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
        PoliticsCard b = new PoliticsCard(Color.BLUE);

        Collection<PoliticsCard> c = new ArrayList<>();

        //TEST CASE: 0 cards
        assertFalse(councillorBalcony.isSatisfiable(c,0));

        //TEST CASE: 5 cards
        assertFalse(councillorBalcony.isSatisfiable(c,0));

    }

    @Test
    public void coinsToPay() throws Exception {

        //create some politics cards for the testing
        PoliticsCard b = new PoliticsCard(Color.BLUE);
        PoliticsCard black = new PoliticsCard(Color.BLACK);
        PoliticsCard w = new PoliticsCard(Color.WHITE);
        @SuppressWarnings("unused")
        PoliticsCard p = new PoliticsCard(Color.PINK);
        PoliticsCard g = new PoliticsCard(Color.GREEN);
        PoliticsCard multi = new PoliticsCard(null);

        Collection<PoliticsCard> c = new ArrayList<>();

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
        Councillor councillorBlack = new Councillor(Color.BLACK);
        Councillor councillorWhite = new Councillor(Color.WHITE);
        Councillor councillorPink = new Councillor(Color.PINK);
        Councillor councillorGreen = new Councillor(Color.GREEN);
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