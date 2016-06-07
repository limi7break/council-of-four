package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.border.EmptyBorder;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.Region;
import net.miginfocom.swing.MigLayout;

public final class GUICreator {
	
	private Map<String, GUICity> cities = new HashMap<>();
	private Map<String, GUIRegion> regions = new HashMap<>();
	
	public GUIPanel createGUI(Game game) {
		
		GUIPanel contentPane = new GUIPanel();
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
	
	public GUIPanel createConnections(Component component, Game game) {
		
		for (GUICity city : cities.values()) {
			city.setCenterPointRelativeTo(component);
		}
			
		List<Line> lines = new ArrayList<>();
		for (City c : game.getBoard().getCities().values()) {
			for (City n : c.getNeighbors()) {
				Point first = cities.get(c.getName()).getCenterPoint();
				Point second = cities.get(n.getName()).getCenterPoint();
				Line line = new Line(first, second);
				
				lines.add(line);
			}
		}
		
		return new ConnectionPane(lines);
		
	}
	
}
