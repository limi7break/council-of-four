package it.polimi.ingsw.ps13.model.resource;

import it.polimi.ingsw.ps13.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;


public class CoinsTest {

    Coins coins;

    @Before
    public void setUp() throws Exception {

        coins = new Coins(10);

    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void add() throws Exception {

        coins.add(1);
        assertEquals(coins.getAmount(),11);

    }

    @Test
    public void consume() throws Exception {

        coins.consume(1);
        assertEquals(coins.getAmount(),9);

    }

    @Test
    public void getAmount() throws Exception {

        assertEquals(coins.getAmount(),10);

    }

    @Test
    public void isEmpty() throws Exception {

        assertFalse(coins.isEmpty());

        Coins empityCoins = new Coins(0);
        assertTrue(empityCoins.isEmpty());

    }

    @Test
    public void giveTo(){
        Player p = new Player("player", Color.green,"green",0,null);
        coins.giveTo(p);
        assertEquals(p.getCoins(),10 + 10);
    }


}