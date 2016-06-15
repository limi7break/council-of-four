package it.polimi.ingsw.ps13.model.council;

import java.awt.*;
import java.awt.color.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CouncillorTest {

    Councillor councillor;

    @Before
    public void setUp() throws Exception {
        councillor = new Councillor(Color.black,"black");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getColor() throws Exception {
        assertEquals(councillor.getColor(),Color.black);
    }

    @Test
    public void getColorName() throws Exception {
        assertEquals(councillor.getColorName(),"black");

    }

}