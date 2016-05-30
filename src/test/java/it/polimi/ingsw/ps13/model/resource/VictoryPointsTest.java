package it.polimi.ingsw.ps13.model.resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 29/05/16.
 */
public class VictoryPointsTest {

    VictoryPoints victoryPoints;

    @Before
    public void setUp() throws Exception {

        victoryPoints = new VictoryPoints(10);

    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void add() throws Exception {

        victoryPoints.add(1);
        assertEquals(victoryPoints.getAmount(),11);

    }

    @Test
    public void consume() throws Exception {

        victoryPoints.consume(1);
        assertEquals(victoryPoints.getAmount(),9);

    }

    @Test
    public void getAmount() throws Exception {

        assertEquals(victoryPoints.getAmount(),10);

    }

    @Test
    public void isEmpty() throws Exception {

        assertFalse(victoryPoints.isEmpty());

        VictoryPoints empity = new VictoryPoints(0);
        assertTrue(empity.isEmpty());

    }


}