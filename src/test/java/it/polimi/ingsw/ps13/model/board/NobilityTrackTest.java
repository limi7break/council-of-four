package it.polimi.ingsw.ps13.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

/**
 * Created by Tommy on 26/05/16.
 */
public class NobilityTrackTest {

	Map<Integer, Bonus> bonuses;
	NobilityTrack nobilityTrack;

    @Before
    public void setUp() throws Exception {

        Bonus b1 = BonusFactory.createEmptyBonus();
        bonuses = new HashMap<>();
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
    public void getBonusMap() {
        Bonus b = BonusFactory.createEmptyBonus();
        Map<Integer, Bonus> bonusMap;
        bonusMap = new HashMap<>();
        bonusMap.put(new Integer(3),b);
        bonusMap.put(new Integer(6),b);
        bonusMap.put(new Integer(9),b);
        bonusMap.put(new Integer(12),b);

        assertEquals(nobilityTrack.getBonusMap().toString(),bonusMap.toString());

    }


    @Test
    public void getBonus() throws Exception {

        assertEquals(nobilityTrack.getBonus(1),null);
        assertEquals(nobilityTrack.getBonus(-1),null);
        assertTrue(nobilityTrack.getBonus(3).isEmpty());
        assertTrue(nobilityTrack.getBonus(12).isEmpty());

    }
    
    @Test
    public void toStringTest() throws Exception {
    	
    	NobilityTrack equalNobilityTrack = new NobilityTrack(bonuses);
    	assertEquals(nobilityTrack.toString(), equalNobilityTrack.toString());
    	
    }

}