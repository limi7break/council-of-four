package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps13.message.request.action.OfferSelectionRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.PassTurnRequestMsg;
import it.polimi.ingsw.ps13.message.request.action.TradeProposalRequestMsg;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.market.Market;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import net.miginfocom.swing.MigLayout;

public class GUIMarket extends JFrame {

	private static final long serialVersionUID = 0L;
	
	private final Player player;
	private final Market market;
	
	private final transient ClientConnection connection;

	private int assistants = 0;
	private Collection<Integer> tiles;
	private Collection<String> cards;
	private int price = 0;
	
	private Collection<Integer> entries;
	
	/**
	 * 
	 * @param market
	 */
	public GUIMarket(Player player, Market market, ClientConnection connection) {
		
		this.player = player;
		this.market = market;
		
		this.connection = connection;
		
		tiles = new ArrayList<>();
		cards = new ArrayList<>();
		
		entries = new ArrayList<>();
		
		setResizable(true);
		setAlwaysOnTop(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				connection.sendMessage(new PassTurnRequestMsg());
			}
		});
		
	}
	
	/**
	 * 
	 */
	public void showSellPhase() {
		
		setTitle("Council of Four Market: Sell Phase");
		
		GUIPanel sellPanel = new GUIPanel(new MigLayout("", "[grow, 50%][grow, 50%]", ""));
		
		GUIPanel assistantsLeftPanel = new GUIPanel(new MigLayout("", "grow", ""));
		assistantsLeftPanel.setBorder(BorderFactory.createTitledBorder("Assistants"));
		GUIPanel ap = new GUIPanel();
		ap.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/assistants.png")), SwingConstants.CENTER));
		JLabel leftAssistants = new JLabel(String.valueOf(player.getAssistants()));
		ap.add(leftAssistants);
		assistantsLeftPanel.add(ap, "grow");
		
		GUIPanel assistantsRightPanel = new GUIPanel(new MigLayout("", "grow", ""));
		assistantsRightPanel.setBorder(BorderFactory.createTitledBorder("Assistants"));
		GUIPanel bp = new GUIPanel();
		bp.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/assistants.png")), SwingConstants.CENTER));
		JLabel rightAssistants = new JLabel(String.valueOf(0));
		bp.add(rightAssistants);
		assistantsRightPanel.add(bp, "grow");
		
		JButton setAssistants = new JButton(">");
		setAssistants.addActionListener(ae -> {
			if (Integer.parseInt(leftAssistants.getText()) > 0) {
				leftAssistants.setText(String.valueOf(Integer.parseInt(leftAssistants.getText())-1));
				rightAssistants.setText(String.valueOf(Integer.parseInt(rightAssistants.getText())+1));
				assistants++;
			}
		});
		assistantsLeftPanel.add(setAssistants, "east");
		
		JButton removeAssistants = new JButton("<");
		removeAssistants.addActionListener(ae -> {
			if (Integer.parseInt(rightAssistants.getText()) > 0) {
				leftAssistants.setText(String.valueOf(Integer.parseInt(leftAssistants.getText())+1));
				rightAssistants.setText(String.valueOf(Integer.parseInt(rightAssistants.getText())-1));
				assistants--;
			}
		});
		assistantsRightPanel.add(removeAssistants, "west");
		
		GUIPanel cardsLeftPanel = new GUIPanel();
		cardsLeftPanel.setBorder(BorderFactory.createTitledBorder("Politics Cards"));
		GUIPanel cardsRightPanel = new GUIPanel();
		cardsRightPanel.setBorder(BorderFactory.createTitledBorder("Politics Cards"));
		GUIPanel tilesLeftPanel = new GUIPanel();
		tilesLeftPanel.setBorder(BorderFactory.createTitledBorder("Permit Tiles"));
		GUIPanel tilesRightPanel = new GUIPanel();
		tilesRightPanel.setBorder(BorderFactory.createTitledBorder("Permit Tiles"));
		
		for (PoliticsCard pc : player.getPoliticsCards()) {
			GUIPoliticsCard card = new GUIPoliticsCard(pc);
			
			card.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (cardsLeftPanel.isAncestorOf(card)) {
						SwingUtilities.invokeLater(() -> {
							cardsLeftPanel.remove(card);
							cardsRightPanel.add(card);
							revalidate();
							repaint();
						});
						cards.add(card.getColorName());
					}
					else if (cardsRightPanel.isAncestorOf(card)) {
						SwingUtilities.invokeLater(() -> {
							cardsRightPanel.remove(card);
							cardsLeftPanel.add(card);
							revalidate();
							repaint();
						});
						cards.remove(card.getColorName());
					}
				}
			});
			
			cardsLeftPanel.add(card);
		}
		
		for (int i=0; i<player.getPermitTiles().size(); i++) {
			PermitTile tile = player.getPermitTiles().get(i);
			GUIPermitTile t = new GUIPermitTile(tile, i);
			
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (tilesLeftPanel.isAncestorOf(t)) {
						SwingUtilities.invokeLater(() -> {
							tilesLeftPanel.remove(t);
							tilesRightPanel.add(t);
							revalidate();
							repaint();
						});
						tiles.add(t.getNumber());
					}
					else if (tilesRightPanel.isAncestorOf(t)) {
						SwingUtilities.invokeLater(() -> {
							tilesRightPanel.remove(t);
							tilesLeftPanel.add(t);
							revalidate();
							repaint();
						});
						tiles.remove(t.getNumber());
					}
				}
			});
			
			tilesLeftPanel.add(t);
		}
		
		GUIPanel coinsPanel = new GUIPanel(new MigLayout("", "grow", ""));
		coinsPanel.setBorder(BorderFactory.createTitledBorder("Price"));
		GUIPanel dp = new GUIPanel();
		dp.add(new JLabel(new ImageIcon(GUIBonusFactory.class.getResource("/it/polimi/ingsw/ps13/resource/image/bonus/coins.png")), SwingConstants.CENTER));
		JLabel coins = new JLabel(String.valueOf(0));
		dp.add(coins);
		coinsPanel.add(dp, "grow");
		
		JButton lessCoins = new JButton("-");
		lessCoins.addActionListener(ae -> {
			if (Integer.parseInt(coins.getText()) > 0) {
				coins.setText(String.valueOf(Integer.parseInt(coins.getText())-1));
				price--;
			}
		});
		coinsPanel.add(lessCoins, "west");
		
		JButton moreCoins = new JButton("+");
		moreCoins.addActionListener(ae -> {
			coins.setText(String.valueOf(Integer.parseInt(coins.getText())+1));
			price++;
		});
		coinsPanel.add(moreCoins, "east");
		
		JButton confirmButton = new JButton("SELL");
		confirmButton.addActionListener(ae -> {
			connection.sendMessage(new TradeProposalRequestMsg(assistants, tiles, cards, price));
			connection.sendMessage(new PassTurnRequestMsg());
			dispose();
		});
		
		sellPanel.add(new JLabel("Your items"));
		sellPanel.add(new JLabel("Up for sale"), "wrap");
		sellPanel.add(assistantsLeftPanel, "grow");
		sellPanel.add(assistantsRightPanel, "grow, wrap");
		sellPanel.add(cardsLeftPanel, "grow");
		sellPanel.add(cardsRightPanel, "grow, wrap");
		sellPanel.add(tilesLeftPanel, "grow");
		sellPanel.add(tilesRightPanel, "grow, wrap");
		sellPanel.add(coinsPanel, "cell 1 4, grow");
		sellPanel.add(confirmButton, "cell 1 5, bottom, grow");
		
		setContentPane(sellPanel);
		setVisible(true);
		
	}
	
	/**
	 * 
	 */
	public void showBuyPhase() {
		
		setTitle("Council of Four Market: Buy Phase");
		
		GUIPanel buyPanel = new GUIPanel(new MigLayout("flowy", "[grow]", "[grow]push[]"));
		
		JButton confirmButton = new JButton("BUY");
		confirmButton.addActionListener(ae -> {
			connection.sendMessage(new OfferSelectionRequestMsg(entries));
			connection.sendMessage(new PassTurnRequestMsg());
			dispose();
		});
		
		GUIPanel entryPanel = new GUIPanel(new FlowLayout());
		for (int i=0; i<market.getEntryList().size(); i++) {
			GUIMarketEntry me = new GUIMarketEntry(market.getEntryList().get(i), i);
			
			me.getSelectButton().addActionListener(ae -> {
				if (!entries.contains(me.getNumber())) {
					entries.add(me.getNumber());
					me.getSelectButton().setText("Remove");
				} else {
					entries.remove(me.getNumber());
					me.getSelectButton().setText("Select");
				}
				confirmButton.setText("BUY " + entries.toString());
			});
			
			entryPanel.add(me);
		}
		
		buyPanel.add(entryPanel, "grow, top, left");
		buyPanel.add(confirmButton, "bottom, right");
		
		setContentPane(buyPanel);
		setVisible(true);
		
	}

}
