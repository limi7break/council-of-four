package it.polimi.ingsw.ps13.model.board;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

/**
 * Created by Tommy on 26/05/16.
 */
public class NobilityTrackTest {

    NobilityTrack nobilityTrack;



    @Before
    public void setUp() throws Exception {

        Bonus b1 = BonusFactory.createEmptyBonus();
        Map<Integer, Bonus> bonuses = new TreeMap<>();
        bonuses.put(new Integer(3),b1);
        bonuses.put(new Integer(6),b1);
        bonuses.put(new Integer(9),b1);
        bonuses.put(new Integer(12),b1);
        nobilityTrack = new NobilityTrack(bonuses);

    }

    @After
    public void tearDown() throws Exception {

    }



    @Test
    public void getBonus() throws Exception {

        assertEquals(nobilityTrack.getBonus(1),null);
        assertEquals(nobilityTrack.getBonus(-1),null);
        assertEquals(nobilityTrack.getBonus(3).toString(),"Wow! It's fucking nothing!\n");
        assertEquals(nobilityTrack.getBonus(12).toString(),"Wow! It's fucking nothing!\n");

    }

}