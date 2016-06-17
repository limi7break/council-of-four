package it.polimi.ingsw.ps13.model.region;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CityColorTest {

    private CityColor cityColor;

    @Before
    public void setUp() throws Exception {

        Bonus bonus = BonusFactory.createEmptyBonus();
        cityColor = new CityColor(Color.blue,"blue",bonus);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getColor() throws Exception {
        assertEquals(cityColor.getColor(),Color.blue);
    }

    @Test
    public void getColorName() throws Exception {
        assertEquals(cityColor.getColorName(),"blue");
    }

    @Test
    public void getBonus() throws Exception {

        Bonus bonus = BonusFactory.createEmptyBonus();
        assertEquals(cityColor.getBonus().toString(),bonus.toString());

    }

    @Test
    public void getCityNames() throws Exception {

        Set<String> cityNames = new HashSet<>();

        //TEST CASE: empty
        assertEquals(cityColor.getCityNames(),cityNames);

        cityNames.add("AA");

        //TEST CASE: 1 city
        cityColor.addCityName("AA");
        assertEquals(cityColor.getCityNames(),cityNames);

        cityNames.add("BB");

        //TEST CASE: 2 cities
        cityColor.addCityName("BB");
        assertEquals(cityColor.getCityNames(),cityNames);
    }

    @Test
    public void addCityName() throws Exception {

        Set<String> cityNames = new HashSet<>();
        cityNames.add("AA");

        //TEST CASE: 1 city
        cityColor.addCityName("AA");
        assertEquals(cityColor.getCityNames(),cityNames);

        cityNames.add("BB");

        //TEST CASE: 2 cities
        cityColor.addCityName("BB");
        assertEquals(cityColor.getCityNames(),cityNames);

        //actually this is already tested in getCityName test method
    }

    @Test
    public void isBonusAvailable() throws Exception {

        assertFalse(cityColor.isBonusAvailable());

    }

    @Test (expected = IllegalArgumentException.class)
    public void setBonusAvailable() throws IllegalArgumentException {

        cityColor.setBonusAvailable(true);
        assertFalse(cityColor.isBonusAvailable());

    }

}