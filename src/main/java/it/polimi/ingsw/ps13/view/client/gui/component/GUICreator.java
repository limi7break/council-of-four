package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.KingRewardTile;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;
import it.polimi.ingsw.ps13.model.region.Region;
import net.miginfocom.swing.MigLayout;

public final class GUICreator {
	
	private Map<String, GUICity> cities = new HashMap<>();
	private Map<String, GUIRegion> regions = new HashMap<>();
	
	public GUIPanel createGUI(Game game) {
		
		GUIPanel mainPane = new GUIPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(new MigLayout("", "", ""));
		
		GUIPanel mapPane = new GUIPanel(new GridLayout(1, 0, 50, 0));
		mapPane.setBorder(BorderFactory.createTitledBorder("Map"));
		mapPane.setTransparent(true);
		
		// Create regions
		for (Region region : game.getBoard().getRegions().values()) {
			GUIRegion regionPane = new GUIRegion(region);
			
			regions.put(region.getName(), regionPane);
			mapPane.add(regionPane);
		}
		
		// Create cities
		for (City city : game.getBoard().getCities().values()) {
			GUICity cityPane = new GUICity(city);
			
			cities.put(city.getName(), cityPane);
			regions.get(city.getRegion().getName()).addCity(cityPane);
		}
		
		GUICity kingCity = cities.get(game.getBoard().getKing().getCity().getName());
		kingCity.setKing();
		
		mainPane.add(mapPane);
		
		// Create players
		GUIPanel players = new GUIPanel(new GridLayout(0, 1));
		players.setTransparent(true);
		for (Player player : game.getPlayers().values()) {
			GUIPlayer playerPane = new GUIPlayer(player);
			
			players.add(playerPane);
		}
		
		players.setBorder(BorderFactory.createTitledBorder("Players"));
		mainPane.add(players, "cell 0 0, growx, top");
		
		// Create bonus tiles relative to city colors
		GUIPanel colorTiles = new GUIPanel(new FlowLayout());
		colorTiles.setBorder(BorderFactory.createTitledBorder("Bonus Tiles"));
		for (CityColor c : game.getBoard().getCityColors().values()) {
			if (!c.getBonus().isEmpty()) {	
				GUIPanel bonusPanel = new GUIPanel(new GridLayout(1, 0));
				GUIBonusFactory.createBonus((ConcreteBonus)c.getBonus(), bonusPanel);
				bonusPanel.setPreferredSize(new Dimension(60, 30));
				bonusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				if (c.isBonusAvailable()) {
					bonusPanel.setBackground(new Color(c.getColor().getRed(), c.getColor().getGreen(), c.getColor().getBlue(), 96));
				} else {
					bonusPanel.setTransparent(true);
				}
				colorTiles.add(bonusPanel);
			}
		}
		
		// Create king's reward tiles
		GUIPanel kingTiles = new GUIPanel(new FlowLayout());
		kingTiles.setBorder(BorderFactory.createTitledBorder("King's Reward Tiles"));
		int i=0;
		for (KingRewardTile krt : game.getBoard().getKingRewardTiles()) {
			i++;
			GUIPanel bonusPanel = new GUIPanel(new GridLayout(1, 0));
			bonusPanel.add(new JLabel(i + "\u00b0"));
			GUIBonusFactory.createBonus((ConcreteBonus)krt.getBonus(), bonusPanel);
			bonusPanel.setPreferredSize(new Dimension(70, 30));
			bonusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			if (krt.isAvailable()) {
				bonusPanel.setBackground(new Color(147, 112, 219, 96));
			} else {
				bonusPanel.setTransparent(true);
			}
			kingTiles.add(bonusPanel);
		}
		
		mainPane.add(colorTiles, "cell 0 1");
		mainPane.add(kingTiles, "cell 0 1");
		
		// Create nobility track
		GUINobilityTrack nobilityTrack = new GUINobilityTrack(game.getBoard().getNobilityTrack());
		mainPane.add(nobilityTrack, "cell 0 2");
		
		return mainPane;
		
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
