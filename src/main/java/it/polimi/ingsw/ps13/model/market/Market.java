package it.polimi.ingsw.ps13.model.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * The market is represented by this class, which contains a list of market entries.
 * A player can create a market entry during the market phase.
 * 
 * A single market entry contains:
 * 	- a reference to the seller
 * 	- a list of marketable objects
 * 	- the price of the goods (in coins)
 *
 */
public class Market implements Serializable {

	public static final long serialVersionUID = 0L;
	private final List<MarketEntry> entryList;
	private boolean enabled;
	
	/**
	 * Creates the market with an empty list of entries.
	 * 
	 */
	public Market() {
		
		entryList = new ArrayList<>();
		enabled = true;
		
	}
	
	/**
	 * Adds an entry to the market.
	 * 
	 * @param entry
	 */
	public void addEntry(MarketEntry entry) {
		
		entryList.add(entry);
		
	}
	
	/**
	 * Manages the transaction when a player selects an entry from the market.
	 * As a result, the entry is removed from the list.
	 * 
	 */
	public void manageTransaction(Player buyer, int selectedEntry) {
		
		// Removes selected entry from the market
		MarketEntry entry = entryList.remove(selectedEntry);
		
		// Subtracts coins from the buyer and adds them to the seller
		int price = entry.getPrice();
		buyer.consumeCoins(price);
		entry.getSeller().addCoins(price);
		
		// Gives the items to the buyer
		List<Marketable> itemList = entry.getItemList();
		for (Marketable item : itemList) {
			item.giveTo(buyer);
		}
		
	}
	
	/**
	 * This method is called when the market phase ends. If there are entries
	 * which haven't been selected by any player, they are removed and the items
	 * contained in those entries are given back to the seller.
	 * 
	 */
	public void closeMarket() {
		
		if (!entryList.isEmpty()) {
			for (Iterator<MarketEntry> it = entryList.iterator(); it.hasNext();) {
				MarketEntry entry = it.next();
				
				for (Marketable item : entry.getItemList()) {
					item.giveTo(entry.getSeller());
				}
				
				it.remove();
			}
		}
		
	}
	
	/**
	 * 
	 * @return an unmodifiable map containing players and the market entries they have created
	 */
	public List<MarketEntry> getEntryList() {
		
		return Collections.unmodifiableList(entryList);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		
		return enabled;
		
	}
	
	/**
	 * 
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled) {
		
		enabled = isEnabled;
		
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[Market]\n");
		
		if (!enabled) {
			sb.append("disabled\n");
		} else {
			if (entryList.isEmpty()) {
				sb.append("empty\n");
			} else {
				for (int i=0; i<entryList.size(); i++) {
					sb.append("[" + i + "]");
					sb.append(entryList.get(i).toString());
				}
			}
		}
		
		return sb.toString();
		
	}
	
}
