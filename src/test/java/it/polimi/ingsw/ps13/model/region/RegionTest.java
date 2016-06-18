package it.polimi.ingsw.ps13.model.region;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 */
public class RegionTest {

    Region region;

    @Before
    public void setUp() throws Exception {

        // creating an empty bonus
        Bonus bonus = BonusFactory.createEmptyBonus();

        //creating a CouncillorBalcony
        Councillor councillorBlack = new Councillor(Color.BLACK, "black");
        Councillor councillorWhite = new Councillor(Color.WHITE, "white");
        Councillor councillorPink = new Councillor(Color.PINK, "pink");
        Councillor councillorGreen = new Councillor(Color.GREEN, "green");
        java.util.List<Councillor> councillors = new LinkedList<>();
        councillors.add(councillorBlack);
        councillors.add(councillorWhite);
        councillors.add(councillorPink);
        councillors.add(councillorGreen);
        CouncillorBalcony councillorBalcony = new CouncillorBalcony(councillors);

        //creating a PermitTileDeck: it contains 1 card
        Collection<PermitTile> cards = new ArrayList<>();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("ZZZZ");
        @SuppressWarnings("unused")
        PermitTile permitTile = new PermitTile(bonus,cityNames);
        PermitTileDeck deck = new PermitTileDeck(cards);

        region = new Region("hill",bonus,councillorBalcony,deck);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        assertEquals(region.getName(),"hill");
    }

    @Test
    public void getBonus() throws Exception {
        Bonus bonus = BonusFactory.createEmptyBonus();
        assertEquals(region.getBonus().toString(),bonus.toString());
    }

    @Test
    public void getCityNames() throws Exception {
        Set<String> citynames = new HashSet<>();
        assertEquals(region.getCityNames(),citynames);
    }

    @Test
    public void addCityName() throws Exception {
        Set<String> citynames = new HashSet<>();
        //TEST CASE: 1 cityName
        region.addCityName("AA");
        citynames.add("AA");
        assertEquals(region.getCityNames(),citynames);
        //TEST CASE: 2 cityNames
        region.addCityName("BB");
        citynames.add("BB");
        assertEquals(region.getCityNames(),citynames);

        //this tests also some other cases of getCityNames()
    }

    @Test
    public void isBonusAvailable() throws Exception {
        assertTrue(region.isBonusAvailable());
    }

    @Test
    public void setBonusAvailable() throws Exception {
        region.setBonusAvailable(false);
        assertFalse(region.isBonusAvailable());
    }
    @Test (expected = IllegalArgumentException.class)
    public void setBonusAvailableIlligalCall() throws IllegalArgumentException {
        region.setBonusAvailable(false);
        assertFalse(region.isBonusAvailable());
        region.setBonusAvailable(true);
        assertFalse(region.isBonusAvailable());     //still false
    }

    @Test
    public void getCouncillorBalcony() throws Exception {
        //creating the expected CouncillorBalcony
        Councillor councillorBlack = new Councillor(Color.BLACK, "black");
        Councillor councillorWhite = new Councillor(Color.WHITE, "white");
        Councillor councillorPink = new Councillor(Color.PINK, "pink");
        Councillor councillorGreen = new Councillor(Color.GREEN, "green");
        java.util.List<Councillor> councillors = new LinkedList<>();
        councillors.add(councillorBlack);
        councillors.add(councillorWhite);
        councillors.add(councillorPink);
        councillors.add(councillorGreen);
        CouncillorBalcony councillorBalcony = new CouncillorBalcony(councillors);

        assertEquals(region.getCouncillorBalcony().toString(),councillorBalcony.toString());
    }

    @Test
    public void getPermitTileDeck() throws Exception {
        // creating an ampty bonus
        Bonus bonus = BonusFactory.createEmptyBonus();

        //creating teh expected PermitTileDeck
        Collection<PermitTile> cards = new ArrayList<>();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("ZZZZ");
        @SuppressWarnings("unused")
        PermitTile permitTile = new PermitTile(bonus,cityNames);
        PermitTileDeck deck = new PermitTileDeck(cards);

        assertEquals(region.getPermitTileDeck().toString(),deck.toString());
    }

}