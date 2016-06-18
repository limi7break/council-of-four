package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.market.MarketEntry;
import it.polimi.ingsw.ps13.model.market.Marketable;
import it.polimi.ingsw.ps13.model.resource.Assistants;
import it.polimi.ingsw.ps13.model.resource.Coins;

/**
 * This is the GUI representation of a market entry.
 *
 */
public class GUIMarketEntry extends GUIPanel {

	private static final long serialVersionUID = 0L;
	
	private final int number;
	private final JButton selectButton;

	/**
	 * Creates the GUI representation of the market entry passed as parameter.
	 * 
	 * @param entry the market entry from which the GUI representation is created
	 * @param number the number of the market entry
	 */
	protected GUIMarketEntry(MarketEntry entry, int number) {
		
		super();
		this.number = number;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		setBorder(BorderFactory.createTitledBorder(entry.getSeller().getName()));
		
		GUIPanel assistants = new GUIPanel(new FlowLayout());
		GUIPanel cards = new GUIPanel(new FlowLayout());
		GUIPanel tiles = new GUIPanel(new FlowLayout());
		
		for (Marketable item : entry.getItemList()) {
			if (item instanceof Assistants) {
				GUIBonusFactory.createBonus((Assistants)item, assistants);
			}
			else if (item instanceof PoliticsCard) {
				cards.add(new GUIPoliticsCard((PoliticsCard)item));
			}
			else if (item instanceof PermitTile) {
				tiles.add(new GUIPermitTile((PermitTile)item, 0));
			}
		}
		
		GUIPanel price = new GUIPanel(new FlowLayout());
		price.add(new JLabel("Price: "));
		GUIBonusFactory.createBonus(new Coins(entry.getPrice()), price);
		
		selectButton = new JButton("Select");
		
		add(assistants);
		add(cards);
		add(tiles);
		add(price);
		add(selectButton);
		
	}
	
	/**
	 * Returns the number of the market entry.
	 * 
	 * @return the number of the market entry.
	 */
	public int getNumber() {
		
		return number;
		
	}
	
	/**
	 * Returns the select button of the market entry.
	 * 
	 * @return the select button of the market entry.
	 */
	public JButton getSelectButton() {
		
		return selectButton;
		
	}
	
}
