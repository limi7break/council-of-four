package it.polimi.ingsw.ps13.model.resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 29/05/16.
 */
public class AssistantsTest {

    Assistants assistants;

    @Before
    public void setUp() throws Exception {

        assistants = new Assistants(10);

    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void add() throws Exception {

        assistants.add(1);
        assertEquals(assistants.getAmount(),11);

    }

    @Test
    public void consume() throws Exception {

        assistants.consume(1);
        assertEquals(assistants.getAmount(),9);

    }

    @Test
    public void getAmount() throws Exception {

        assertEquals(assistants.getAmount(),10);

    }

    @Test
    public void isEmpty() throws Exception {

        assertFalse(assistants.isEmpty());

        Assistants empityAssistants = new Assistants(0);
        assertTrue(empityAssistants.isEmpty());

    }

}