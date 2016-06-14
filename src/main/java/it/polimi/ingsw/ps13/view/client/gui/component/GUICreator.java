package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import it.polimi.ingsw.ps13.message.request.action.PassTurnRequestMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.KingRewardTile;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.region.City;
import it.polimi.ingsw.ps13.model.region.CityColor;
import it.polimi.ingsw.ps13.model.region.Region;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.bonus.GainVisiblePermitTileListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.bonus.RegainPermitTileBonusListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.bonus.RegainRewardTokenListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.main.AcquirePermitTileListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.main.BuildEmporiumListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.main.ElectCouncillorListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.main.KingListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.quick.ChangePermitTilesListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.quick.EngageAssistantListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.quick.GainMainActionListener;
import it.polimi.ingsw.ps13.view.client.gui.actions.quick.QuickElectCouncillorListener;
import net.miginfocom.swing.MigLayout;

public final class GUICreator {
	
	private final Map<String, GUICity> cities = new HashMap<>();
	private final Map<String, GUIRegion> regions = new HashMap<>();
	private final List<GUICouncillor> councillors = new ArrayList<>();
	private final List<GUIPoliticsCard> cards = new ArrayList<>();
	private final List<GUIPermitTile> tiles = new ArrayList<>();
	
	public GUIPanel createMainPane(Game game) {
		
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
	
	public GUIPanel createRightPane(Game game, GUIForm form, String playerName, ClientConnection connection) {
		
		// Create right pane with another MiGLayout
		GUIPanel rightPane = new GUIPanel();
		rightPane.setLayout(new MigLayout("flowy", "", ""));
		rightPane.setTransparent(true);
		
		// Create and set text area and text field
		rightPane.add(form, "growx");
		
		GUICouncillorBalcony kingBalcony = new GUICouncillorBalcony(game.getBoard().getKingBalcony());
		kingBalcony.setBorder(BorderFactory.createTitledBorder("King Balcony"));
		rightPane.add(kingBalcony, "cell 0 1, flowx");
		
		// Create panel showing available board councillors
		GUIPanel councillorsPanel = new GUIPanel(new FlowLayout());
		councillorsPanel.setBorder(BorderFactory.createTitledBorder("Councillors"));
		for (Councillor c : game.getBoard().getCouncillors()) {
			GUICouncillor councillor = new GUICouncillor(c);
			councillorsPanel.add(councillor);
			councillors.add(councillor);
		}
		rightPane.add(councillorsPanel, "cell 0 1");
		
		// Create and configure action buttons
		JButton confirmButton = new JButton("confirm");
		
		ActionListener removeListeners = ae -> {
			for (GUICity c : cities.values()) {
				for ( MouseListener al : c.getMouseListeners()) {
					c.removeMouseListener(al);
				}
			}
			for (GUICouncillor c : councillors) {
				for ( MouseListener al : c.getMouseListeners()) {
					c.removeMouseListener(al);
				}
			}
			for (GUIPoliticsCard c : cards) {
				for ( MouseListener al : c.getMouseListeners()) {
					c.removeMouseListener(al);
				}
			}
			for (GUIPermitTile c : tiles) {
				for ( MouseListener al : c.getMouseListeners()) {
					c.removeMouseListener(al);
				}
			}
			for (GUIRegion c : regions.values()) {
				GUICouncillorBalcony balcony = c.getCouncillorBalcony();
				for ( MouseListener al : balcony.getMouseListeners()) {
					balcony.removeMouseListener(al);
				}
				List<GUIPermitTile> guitiles = c.getVisibleTiles();
				for (GUIPermitTile tile : guitiles) {
					for ( MouseListener al : tile.getMouseListeners()) {
						tile.removeMouseListener(al);
					}
				}
			}
			for ( ActionListener al : confirmButton.getActionListeners()) {
				confirmButton.removeActionListener(al);
			}
		};
		
		JButton corrupt = new JButton("corrupt");
		corrupt.addActionListener(new AcquirePermitTileListener(regions.values(), cards, form, connection, confirmButton));
		corrupt.addActionListener(removeListeners);
		JButton build = new JButton("build");
		build.addActionListener(new BuildEmporiumListener(tiles, cities.values(), form, connection, confirmButton));
		build.addActionListener(removeListeners);
		JButton elect = new JButton("elect");
		elect.addActionListener(new ElectCouncillorListener(regions.values(), councillors, form, connection, confirmButton));
		elect.addActionListener(removeListeners);
		JButton king = new JButton("king");
		king.addActionListener(new KingListener(cities.values(), cards, form, connection, confirmButton));
		king.addActionListener(removeListeners);
		JButton changetiles = new JButton("changetiles");
		changetiles.addActionListener(new ChangePermitTilesListener(regions.values(), form, connection, confirmButton));
		changetiles.addActionListener(removeListeners);
		JButton engageassistant = new JButton("engageassistant");
		engageassistant.addActionListener(new EngageAssistantListener(form, connection, confirmButton));
		engageassistant.addActionListener(removeListeners);
		JButton gainmainaction = new JButton("gainmainaction");
		gainmainaction.addActionListener(new GainMainActionListener(form, connection, confirmButton));
		gainmainaction.addActionListener(removeListeners);
		JButton qelect = new JButton("qelect");
		qelect.addActionListener(new QuickElectCouncillorListener(regions.values(), councillors, form, connection, confirmButton));
		qelect.addActionListener(removeListeners);
		JButton gettile = new JButton("get tile");
		gettile.addActionListener(new GainVisiblePermitTileListener(regions.values(), form, connection, confirmButton));
		gettile.addActionListener(removeListeners);
		JButton gettb = new JButton("get tb");
		gettb.addActionListener(new RegainPermitTileBonusListener(tiles, form, connection, confirmButton));
		gettb.addActionListener(removeListeners);
		JButton getrt = new JButton("get rt");
		getrt.addActionListener(new RegainRewardTokenListener(cities.values(), form, connection, confirmButton));
		getrt.addActionListener(removeListeners);
		
		GUIPanel mainActionButtons = new GUIPanel(new GridLayout(0, 1));
		mainActionButtons.setBorder(BorderFactory.createTitledBorder("Main (" + game.getPlayer(playerName).getTokens().getMain() + ")"));
		
		GUIPanel quickActionButtons = new GUIPanel(new GridLayout(0, 1));
		quickActionButtons.setBorder(BorderFactory.createTitledBorder("Quick"));
		
		GUIPanel bonusActionButtons = new GUIPanel(new GridLayout(0, 1));
		bonusActionButtons.setBorder(BorderFactory.createTitledBorder("Bonus"));
		
		GUIPanel specialActionButtons = new GUIPanel(new GridLayout(0, 1));
		specialActionButtons.setBorder(BorderFactory.createTitledBorder("Special"));
		
		mainActionButtons.add(corrupt);
		mainActionButtons.add(build);
		mainActionButtons.add(elect);
		mainActionButtons.add(king);
		quickActionButtons.add(changetiles);
		quickActionButtons.add(engageassistant);
		quickActionButtons.add(gainmainaction);
		quickActionButtons.add(qelect);
		bonusActionButtons.add(gettile);
		bonusActionButtons.add(gettb);
		bonusActionButtons.add(getrt);
		
		if (game.getPlayer(playerName).getTokens().getMain() <= 0) {
			corrupt.setEnabled(false);
			build.setEnabled(false);
			elect.setEnabled(false);
			king.setEnabled(false);
		}
		
		if (game.getPlayer(playerName).getTokens().getQuick() <= 0) {
			changetiles.setEnabled(false);
			engageassistant.setEnabled(false);
			gainmainaction.setEnabled(false);
			qelect.setEnabled(false);
		}
		
		if (game.getPlayer(playerName).getTokens().getTakeTile() <= 0) {
			gettile.setEnabled(false);
		}
		
		if (game.getPlayer(playerName).getTokens().getTileBonus() <= 0) {
			gettb.setEnabled(false);
		}

		if (game.getPlayer(playerName).getTokens().getRewardToken() <= 0) {
			getrt.setEnabled(false);
		}
		
		specialActionButtons.add(confirmButton);
		
		JButton pass = new JButton("pass");
		pass.addActionListener(ae -> {
			connection.sendMessage(new PassTurnRequestMsg());
		});
		pass.addActionListener(removeListeners);
		specialActionButtons.add(pass);
		
		rightPane.add(mainActionButtons, "cell 0 2, flowx");
		rightPane.add(quickActionButtons, "cell 0 2");
		rightPane.add(bonusActionButtons, "cell 0 2");
		rightPane.add(specialActionButtons, "cell 0 2");
		
		// Create panel showing player's politics cards
		GUIPanel politicsCardsPanel = new GUIPanel(new FlowLayout());
		politicsCardsPanel.setTransparent(true);
		politicsCardsPanel.setBorder(BorderFactory.createTitledBorder("Politics Cards"));
		for (PoliticsCard c : game.getPlayer(playerName).getPoliticsCards()) {
			GUIPoliticsCard card = new GUIPoliticsCard(c);
			politicsCardsPanel.add(card);
			cards.add(card);
		}
		rightPane.add(politicsCardsPanel, "cell 0 3");
		
		// Create panel showing player's permit tiles
		GUIPanel tilesPanel = new GUIPanel(new GridLayout(0, 5));
		tilesPanel.setBorder(BorderFactory.createTitledBorder("Permit Tiles"));
		for (int i=0; i<game.getPlayer(playerName).getPermitTiles().size(); i++) {
			PermitTile tile = game.getPlayer(playerName).getPermitTiles().get(i);
			GUIPermitTile t = new GUIPermitTile(tile, i);
			tilesPanel.add(t);
			tiles.add(t);
		}
		rightPane.add(tilesPanel, "cell 0 4");
		
		return rightPane;
		
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
