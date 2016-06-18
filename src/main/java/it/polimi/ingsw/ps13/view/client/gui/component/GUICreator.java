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
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import it.polimi.ingsw.ps13.message.request.action.PassTurnRequestMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.board.KingRewardTile;
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

/**
 * This class reads data from the game model and creates every object displayed in the GUI.
 *
 */
public final class GUICreator {
	
	// These are saved references to every clickable GUI object
	private final Map<String, GUICity> cities = new HashMap<>();
	private final Map<String, GUIRegion> regions = new HashMap<>();
	private final List<GUICouncillor> availableCouncillors = new ArrayList<>();
	private final List<GUIPoliticsCard> playerCards = new ArrayList<>();
	private final List<GUIPermitTile> playerTiles = new ArrayList<>();
	private GUICouncillorBalcony kingBalcony;
	
	/**
	 * Creates the main top layer pane.
	 * The layout manager for this pane is MigLayout.
	 * 
	 * The pane returned by this method contains the map, the bonus tiles, the nobility track and basic public player stats.
	 * 
	 * @param game the game model from which GUI objects are created
	 * @return the created main top layer pane
	 */
	public GUIPanel createMainPane(Game game) {
		
		GUIPanel mainPane = new GUIPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(new MigLayout("", "", ""));
		
		GUIPanel mapPane = new GUIPanel(new GridLayout(1, 0, 40, 0));
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
				GUIBonusFactory.createBonus(c.getBonus(), bonusPanel);
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
			GUIBonusFactory.createBonus(krt.getBonus(), bonusPanel);
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
	
	/**
	 * Creates the right pane, to be added to the main top layer pane.
	 * The layout manager for this pane is MigLayout with vertical flow.
	 * 
	 * The pane returned by this method contains the input form, the king balcony, the available councillors,
	 * the action buttons, the politics cards and the permit tiles in the player's hand.
	 * 
	 * @param game the game model from which GUI objects are created
	 * @param form the input form to place in the panel
	 * @param playerName unique identifier of the player who is playing on this client
	 * @param connection the client connection used to communicate with the server
	 * @return the created right pane, ready to be added to the main pane
	 */
	public GUIPanel createRightPane(Game game, GUIForm form, String playerName, ClientConnection connection) {
		
		// Create right pane with another MiGLayout
		GUIPanel rightPane = new GUIPanel();
		rightPane.setLayout(new MigLayout("flowy", "", ""));
		rightPane.setTransparent(true);
		
		// Create and set text area and text field
		rightPane.add(form, "growx");
		
		kingBalcony = new GUICouncillorBalcony(game.getBoard().getKingBalcony());
		kingBalcony.setBorder(BorderFactory.createTitledBorder("King Balcony"));
		rightPane.add(kingBalcony, "cell 0 1, flowx");
		
		// Create panel showing available board councillors
		GUIPanel councillorsPanel = new GUIPanel(new FlowLayout());
		councillorsPanel.setBorder(BorderFactory.createTitledBorder("Councillors"));
		for (Councillor c : game.getBoard().getCouncillors()) {
			GUICouncillor councillor = new GUICouncillor(c);
			councillorsPanel.add(councillor);
			availableCouncillors.add(councillor);
		}
		rightPane.add(councillorsPanel, "cell 0 1");
		
		// Create and configure action buttons
		JButton confirmButton = new JButton("Confirm");
		
		ActionListener removeListeners = ae -> {
			for (GUICity c : cities.values())
				for ( MouseListener al : c.getMouseListeners())
					c.removeMouseListener(al);
			
			for (GUICouncillor c : availableCouncillors)
				for ( MouseListener al : c.getMouseListeners())
					c.removeMouseListener(al);
			
			for (GUIPoliticsCard c : playerCards)
				for ( MouseListener al : c.getMouseListeners())
					c.removeMouseListener(al);
					
			for (GUIPermitTile c : playerTiles)
				for ( MouseListener al : c.getMouseListeners())
					c.removeMouseListener(al);
					
			for (GUIRegion c : regions.values()) {
				
				GUICouncillorBalcony balcony = c.getCouncillorBalcony();
				for ( MouseListener al : balcony.getMouseListeners())
					balcony.removeMouseListener(al);
					
				List<GUIPermitTile> guitiles = c.getVisibleTiles();
				for (GUIPermitTile tile : guitiles)
					for ( MouseListener al : tile.getMouseListeners())
						tile.removeMouseListener(al);
						
			}
			
			for ( ActionListener al : confirmButton.getActionListeners())
				confirmButton.removeActionListener(al);
			
		};
		
		JButton corrupt = new JButton("Corrupt");
		corrupt.addActionListener(new AcquirePermitTileListener(regions.values(), playerCards, form, connection, confirmButton));
		corrupt.addActionListener(removeListeners);
		JButton build = new JButton("Build");
		build.addActionListener(new BuildEmporiumListener(playerTiles, cities.values(), form, connection, confirmButton));
		build.addActionListener(removeListeners);
		JButton elect = new JButton("Elect");
		elect.addActionListener(new ElectCouncillorListener(regions.values(), availableCouncillors, kingBalcony, form, connection, confirmButton));
		elect.addActionListener(removeListeners);
		JButton king = new JButton("King");
		king.addActionListener(new KingListener(cities.values(), playerCards, form, connection, confirmButton));
		king.addActionListener(removeListeners);
		JButton changetiles = new JButton("Change Tiles");
		changetiles.addActionListener(new ChangePermitTilesListener(regions.values(), form, connection, confirmButton));
		changetiles.addActionListener(removeListeners);
		JButton engageassistant = new JButton("Engage Assistant");
		engageassistant.addActionListener(new EngageAssistantListener(form, connection, confirmButton));
		engageassistant.addActionListener(removeListeners);
		JButton gainmainaction = new JButton("Gain Main Action");
		gainmainaction.addActionListener(new GainMainActionListener(form, connection, confirmButton));
		gainmainaction.addActionListener(removeListeners);
		JButton qelect = new JButton("Quick Elect");
		qelect.addActionListener(new QuickElectCouncillorListener(regions.values(), availableCouncillors, kingBalcony, form, connection, confirmButton));
		qelect.addActionListener(removeListeners);
		JButton gettile = new JButton("Get Tile");
		gettile.addActionListener(new GainVisiblePermitTileListener(regions.values(), form, connection, confirmButton));
		gettile.addActionListener(removeListeners);
		JButton gettb = new JButton("Get TB");
		gettb.addActionListener(new RegainPermitTileBonusListener(playerTiles, form, connection, confirmButton));
		gettb.addActionListener(removeListeners);
		JButton getrt = new JButton("Get RT");
		getrt.addActionListener(new RegainRewardTokenListener(cities.values(), form, connection, confirmButton));
		getrt.addActionListener(removeListeners);
		
		JButton passButton = new JButton("Pass");
		passButton.addActionListener(ae ->
			connection.sendMessage(new PassTurnRequestMsg())
		);
		passButton.addActionListener(removeListeners);
		
		GUIPanel mainActionButtons = new GUIPanel(new GridLayout(0, 1));
		mainActionButtons.setBorder(BorderFactory.createTitledBorder("Main (" + game.getPlayer(playerName).getTokens().getMain() + ")"));
		mainActionButtons.add(corrupt);
		mainActionButtons.add(build);
		mainActionButtons.add(elect);
		mainActionButtons.add(king);
		
		GUIPanel quickActionButtons = new GUIPanel(new GridLayout(0, 1));
		quickActionButtons.setBorder(BorderFactory.createTitledBorder("Quick"));
		quickActionButtons.add(changetiles);
		quickActionButtons.add(engageassistant);
		quickActionButtons.add(gainmainaction);
		quickActionButtons.add(qelect);
		
		GUIPanel bonusActionButtons = new GUIPanel(new GridLayout(0, 1));
		bonusActionButtons.setBorder(BorderFactory.createTitledBorder("Bonus"));
		bonusActionButtons.add(gettile);
		bonusActionButtons.add(gettb);
		bonusActionButtons.add(getrt);
		
		GUIPanel specialActionButtons = new GUIPanel(new GridLayout(0, 1));
		specialActionButtons.setBorder(BorderFactory.createTitledBorder("Special"));
		specialActionButtons.add(confirmButton);
		specialActionButtons.add(passButton);
		
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
		
		rightPane.add(mainActionButtons, "cell 0 2, flowx");
		rightPane.add(quickActionButtons, "cell 0 2");
		rightPane.add(bonusActionButtons, "cell 0 2");
		rightPane.add(specialActionButtons, "cell 0 2");
		
		// Create panel showing player's politics cards
		GUIPanel politicsCardsPanel = new GUIPanel(new FlowLayout());
		politicsCardsPanel.setTransparent(true);
		politicsCardsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 6, 6, 6), "Politics Cards"));
		for (PoliticsCard c : game.getPlayer(playerName).getPoliticsCards()) {
			GUIPoliticsCard card = new GUIPoliticsCard(c);
			politicsCardsPanel.add(card);
			playerCards.add(card);
		}
		JScrollPane politicsScrollPane = new JScrollPane(politicsCardsPanel);
		politicsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		politicsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rightPane.add(politicsScrollPane, "cell 0 3");
		
		// Create panel showing player's permit tiles
		GUIPanel tilesPanel = new GUIPanel(new FlowLayout());
		tilesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 6, 6, 6), "Permit Tiles"));
		for (int i=0; i<game.getPlayer(playerName).getPermitTiles().size(); i++) {
			PermitTile tile = game.getPlayer(playerName).getPermitTiles().get(i);
			GUIPermitTile t = new GUIPermitTile(tile, i);
			tilesPanel.add(t);
			playerTiles.add(t);
		}
		JScrollPane tilesScrollPane = new JScrollPane(tilesPanel);
		tilesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		tilesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rightPane.add(tilesScrollPane, "cell 0 4");
		
		return rightPane;
		
	}
	
	/**
	 * Creates the city connection pane, to be added as the bottom layer in the layered pane.
	 * 
	 * @param component the city center points will be relative to this component
	 * @param game the game model from which connections are created
	 * @return the created connection pane, ready to be added as the bottom layer in the layered pane
	 */
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
