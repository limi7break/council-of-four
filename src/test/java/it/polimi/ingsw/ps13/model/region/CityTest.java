package it.polimi.ingsw.ps13.model.region;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.bonus.NobilityPointsBonus;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.resource.Coins;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 15/06/16.
 */
public class CityTest {

    City city;

    Region region;

    @Before
    public void setUp() throws Exception {

        Bonus bonus = BonusFactory.createEmptyBonus();

        // creating stuff to initiate a region

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

        //creating a PermitTileDeck: it's empty
        Collection<PermitTile> cards = new ArrayList<>();
        PermitTileDeck deck = new PermitTileDeck(cards);

        region = new Region("hill",bonus,councillorBalcony,deck);


        CityColor cityColor = new CityColor(Color.green,"green",bonus);

        city = new City("AA",region,cityColor,bonus);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        assertEquals(city.getName(),"AA");
    }

    @Test
    public void getRegion() throws Exception {
        assertEquals(city.getRegion(),region);
    }

    @Test
    public void getColor() throws Exception {
        assertEquals(city.getColor(),Color.green);
    }

    @Test
    public void getCityColor() throws Exception {
        Bonus bonus = BonusFactory.createEmptyBonus();
        CityColor cityColor = new CityColor(Color.green,"green",bonus);

        assertEquals(city.getCityColor().toString(),cityColor.toString());
    }

    @Test
    public void getNeighbors() throws Exception {
        //TEST CASE: no neighbors
        Set<City> neighbors = new HashSet<>();
        assertEquals(city.getNeighbors(),neighbors);
    }

    @Test
    public void addNeighbor() throws Exception {

        Bonus bonus = BonusFactory.createEmptyBonus();
        CityColor cityColor = new CityColor(Color.green,"green",bonus);

        City neighbor1 = new City("N1",region,cityColor,bonus);
        City neighbor2 = new City("N2",region,cityColor,bonus);

        Set<City> neighbors = new HashSet<>();

        //TEST CASE: 1 neighbor
        city.addNeighbor(neighbor1);
        neighbors.add(neighbor1);
        assertEquals(city.getNeighbors(),neighbors);

        //TEST CASE: 2 neighbors
        city.addNeighbor(neighbor2);
        neighbors.add(neighbor2);
        assertEquals(city.getNeighbors(),neighbors);
    }

    @Test
    public void getEmporiums() throws Exception {

        //TEST CASE: 0 emporiums
        List<Emporium> emporiums = new LinkedList<>();
        assertEquals(city.getEmporiums(),emporiums);

    }

    @Test
    public void getNumberOfEmporiums() throws Exception {

        Emporium e1 = new Emporium(Color.blue);
        Emporium e2 = new Emporium(Color.green);

        //TEST CASE: 0 emporiums
        assertEquals(city.getNumberOfEmporiums(),0);

        //TEST CASE: 1 emporium
        city.addEmporium(e1);
        assertEquals(city.getNumberOfEmporiums(),1);

        //TEST CASE: 2 emporiums
        city.addEmporium(e2);
        assertEquals(city.getNumberOfEmporiums(),2);


    }

    @Test
    public void addEmporium() throws Exception {

        List<Emporium> emporiums = new LinkedList<>();
        Emporium e1 = new Emporium(Color.blue);
        Emporium e2 = new Emporium(Color.green);

        //TEST CASE: 1 emporium
        city.addEmporium(e1);
        emporiums.add(e1);
        assertEquals(city.getEmporiums(),emporiums);

        //TEST CASE: 2 emporiums
        city.addEmporium(e2);
        emporiums.add(e2);
        assertEquals(city.getEmporiums(),emporiums);

    }

    @Test
    public void getBonus() throws Exception {
        Bonus bonus = BonusFactory.createEmptyBonus();
        assertEquals(city.getBonus().toString(),bonus.toString());
    }

    @Test
    public void giveBonuses() throws Exception {

        Player player = new Player("player",Color.green,"green",0,null);
        //used to check the algorithm
        Coins coin = new Coins(1);
        List<Bonus> bonusList = new ArrayList<>();
        bonusList.add(coin);
        ConcreteBonus bonus = new ConcreteBonus(bonusList);

        // needed to create neighbors
        CityColor cityColor = new CityColor(Color.green,"green",bonus);

        City c = new City("CC",region,cityColor,bonus);
        City neighbor1 = new City("N1",region,cityColor,bonus);
        City neighbor2 = new City("N2",region,cityColor,bonus);

        City neighbor11 = new City("N11",region,cityColor,bonus);
        City neighbor12= new City("N12",region,cityColor,bonus);
        City neighbor21 = new City("N21",region,cityColor,bonus);
        City neighbor22 = new City("N22",region,cityColor,bonus);

        player.addCity("c");
        //TEST CASE: 0 neighbour
        c.giveBonuses(player);
        assertEquals(player.getCoins(),10 + 1);

        c.addNeighbor(neighbor1);
        c.addNeighbor(neighbor2);
        player.addCity("N1");
        player.addCity("N2");
        //TEST CASE: 2 neighbours
        c.giveBonuses(player);
        assertEquals(player.getCoins(),11 + 3);

        neighbor1.addNeighbor(neighbor11);
        neighbor1.addNeighbor(neighbor12);
        neighbor2.addNeighbor(neighbor21);
        neighbor2.addNeighbor(neighbor22);
        player.addCity("N11");
        player.addCity("N21");
        player.addCity("N12");
        player.addCity("N22");
        //TEST CASE: 2 neighbours, each one has 2 neighbors
        c.giveBonuses(player);
        assertEquals(player.getCoins(),14 + 7);

        neighbor11.addNeighbor(neighbor21);
        //TEST CASE: 2 neighbours, each one has 2 neighbors and there is a loop
        c.giveBonuses(player);
        assertEquals(player.getCoins(),21 + 7);


    }

}