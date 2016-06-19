package it.polimi.ingsw.ps13.model.deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.resource.Coins;


public class PermitTileTest {

    PermitTile permitTile;
    
    Bonus bonus = BonusFactory.createEmptyBonus();
    Set<String> cityNames = new HashSet<>();
    

    @Before
    public void setUp() throws Exception {
    	
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

        assertTrue(permitTile.isUsable());
        permitTile.setUsable(false);
        assertFalse(permitTile.isUsable());

    }


    @Test
    public void giveTo() throws Exception {
        Player player = new Player("player1", Color.cyan,"cyan",10,null);
        Collection<PermitTile> cards = new ArrayList<>();
        cards.add(permitTile);
        permitTile.giveTo(player);
        assertEquals(player.getPermitTiles(),cards);

    }
    
    @Test
    public void equalsAndHashCode() throws Exception {
    	
    	Bonus differentBonus = new Coins(2);
    	Set<String> differentCityNames = new HashSet<>();
    	differentCityNames.add("swag");
    	
    	PermitTile permitTileTwo = new PermitTile(bonus, cityNames);
    	
    	assertEquals(permitTile, permitTile);
    	assertEquals(permitTile.hashCode(), permitTile.hashCode());
    	assertEquals(permitTile, permitTileTwo);
    	assertEquals(permitTile.hashCode(), permitTileTwo.hashCode());
    	assertNotEquals(permitTile, null);
    	
    	PermitTile ptWithDifferentBonus = new PermitTile(differentBonus, cityNames);
    	PermitTile ptWithDifferentCityNames = new PermitTile(bonus, differentCityNames);
    	assertNotEquals(permitTile, ptWithDifferentBonus);
    	assertNotEquals(permitTile.hashCode(), ptWithDifferentBonus.hashCode());
    	assertNotEquals(permitTile, ptWithDifferentCityNames);
    	assertNotEquals(permitTile.hashCode(), ptWithDifferentCityNames.hashCode());
    	
    	permitTileTwo.setUsable(false);
    	assertNotEquals(permitTile, permitTileTwo);
    	assertNotEquals(permitTile.hashCode(), permitTileTwo.hashCode());
    	
    }

}