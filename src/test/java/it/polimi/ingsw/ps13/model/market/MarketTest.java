package it.polimi.ingsw.ps13.model.market;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.resource.Assistants;

public class MarketTest {

	private Market market;
	private Player seller;
	private Player buyer;
	
	@Before
	public void setUp() throws Exception {
		
		market = new Market();
		
		market.setEnabled(false);
		assertFalse(market.isEnabled());
		assertEquals(market.toString(), "[Market]\ndisabled\n");
		market.setEnabled(true);
		assertEquals(market.toString(), "[Market]\nempty\n");
		
		seller = new Player("player0", Color.BLACK, "black", 0, null);
		buyer = new Player("player1", Color.BLUE, "blue", 1, null);
		
		List<Marketable> items = new ArrayList<>();
		items.add(new Assistants(2));
		market.addEntry(new MarketEntry(seller, items, 5));
		assertEquals(market.toString(), "[Market]\n\n[0]" + market.getEntryList().get(0).toString());
		
		assertEquals(market.getEntryList().size(), 1);
		assertEquals(market.getEntryList().get(0).getSeller(), seller);
		assertEquals(market.getEntryList().get(0).getPrice(), 5);
		
	}
	
	@Test
	public void manageTransaction() throws Exception {
		
		int buyerAssistantsPreTransaction = buyer.getAssistants();
		market.manageTransaction(buyer, 0);
		
		assertEquals(buyer.getAssistants(), buyerAssistantsPreTransaction + ((Assistants)market.getEntryList().get(0).getItemList().get(0)).getAmount());
		
		List<Integer> entriesToBeRemoved = new ArrayList<>();
		entriesToBeRemoved.add(new Integer(0));
		market.removeEntries(entriesToBeRemoved);
		assertTrue(market.getEntryList().isEmpty());
		
	}
	
	@Test
	public void closeMarket() throws Exception {
		
		List<Marketable> items = new ArrayList<>();
		items.add(new PoliticsCard(Color.black, "black"));
		market.addEntry(new MarketEntry(seller, items, 0));
		
		int sellerPoliticsCardsPreClose = seller.getPoliticsCards().size();
		market.closeMarket();
		
		assertEquals(seller.getPoliticsCards().size(), sellerPoliticsCardsPreClose + 1);
		assertEquals(seller.getPoliticsCards().get(0).getColor(), Color.black);
		
	}

	@After
	public void tearDown() throws Exception { }

}
