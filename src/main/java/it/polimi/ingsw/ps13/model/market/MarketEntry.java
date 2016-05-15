package it.polimi.ingsw.ps13.model.market;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This is a wrapper class that represents a market entry.
 * It contains a reference to the player who created the entry, a list of
 * marketable items and their overall price in coins.
 * 
 * A player can put up marketable items for sale during the market phase.
 *
 */
public class MarketEntry implements Serializable {
	
	public static final long serialVersionUID = 0L;
	private final Player seller;
	private final List<Marketable> itemList;
	private final int price;
	
	public MarketEntry(Player seller, Collection<Marketable> items, int price) {
		
		this.seller = seller;
		itemList = new ArrayList<>();
		itemList.addAll(items);
		this.price = price;
		
	}
	
	/**
	 * 
	 * @return the player who created the market entry
	 */
	public Player getSeller() {
		
		return seller;
		
	}
	
	/**
	 * 
	 * @return an unmodifiable list of the items in the market entry
	 */
	public List<Marketable> getItemList() {
		
		return Collections.unmodifiableList(itemList);
		
	}
	
	/**
	 * 
	 * @return the price of the items
	 */
	public int getPrice() {
		
		return price;
		
	}

}
