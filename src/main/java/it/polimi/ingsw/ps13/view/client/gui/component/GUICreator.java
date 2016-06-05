package it.polimi.ingsw.ps13.view.client.gui.component;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.Region;
import net.miginfocom.swing.*;

public final class GUICreator {
	
	private GUICreator() { }
	
	public static JPanel createGUI(Game game) {
		
		Map<String, GUICity> cities = new HashMap<>();
		Map<String, GUIRegion> regions = new HashMap<>();
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new MigLayout("", "[]50[]", "[]50[]"));
		
		// Create regions
		for (Region region : game.getBoard().getRegions().values()) {
			GUIRegion regionPane = new GUIRegion(region);
			
			regions.put(region.getName(), regionPane);
			contentPane.add(regionPane);
		}
		
		// Create cities
		for (City city : game.getBoard().getCities().values()) {
			GUICity cityPane = new GUICity(city);
			
			cities.put(city.getName(), cityPane);
			regions.get(city.getRegion().getName()).addCity(cityPane);
		}
		
		return contentPane;
		
	}
	
}
