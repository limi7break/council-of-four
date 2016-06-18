package it.polimi.ingsw.ps13.model.deck;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 15/06/16.
 */
public class PermitTileTest {

    PermitTile permitTile;

    @Before
    public void setUp() throws Exception {
        Bonus bonus = BonusFactory.createEmptyBonus();
        Set<String> cityNames = new HashSet<>();
        cityNames.add("AA");
        permitTile = new PermitTile(bonus,cityNames);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getBonus() throws Exception {
        Bonus bonus = BonusFactory.createEmptyBonus();
        assertEquals(permitTile.getBonus().toString(),bonus.toString());
    }

    @Test
    public void getCityNames() throws Exception {

        Set<String> cityNames = new HashSet<>();
        cityNames.add("AA");
        assertEquals(permitTile.getCityNames(),cityNames);

    }

    @Test
    public void isUsable() throws Exception {
        assertTrue(permitTile.isUsable());
        permitTile.setUsable(false);
        assertFalse(permitTile.isUsable());
    }

    @Test
    public void setUsable() throws Exception {

        assertEquals(permitTile.isUsable(), true);
        permitTile.setUsable(false);
        assertEquals(permitTile.isUsable(), false);

    }

    @Test
    public void giveTo() throws Exception {
        Player player = new Player("player1", Color.cyan,"cyan",10,null);
        Collection<PermitTile> cards = new ArrayList<>();
        cards.add(permitTile);
        permitTile.giveTo(player);
        assertEquals(player.getPermitTiles(),cards);

    }

}