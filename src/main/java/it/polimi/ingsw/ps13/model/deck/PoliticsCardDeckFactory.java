package it.polimi.ingsw.ps13.model.deck;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.controller.GameController;

/**
 * 
 *
 */
public class PoliticsCardDeckFactory {

	private static final int CARDS_PER_COLOR = 13;
	private static final int MULTICOLORED_CARDS = 12;
	
	private PoliticsCardDeckFactory () { }
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	public static PoliticsCardDeck createPoliticsCardDeck(Document config) {
		
		List<PoliticsCard> cards = new ArrayList<>();
		
		Element politicsColorsElement = (Element) config.getElementsByTagName("politicscolors").item(0);
		NodeList politicsColorsNodeList = politicsColorsElement.getChildNodes();
		for (int i=0; i<politicsColorsNodeList.getLength(); i++) {
			if (politicsColorsNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentColor = (Element) politicsColorsNodeList.item(i);
				String currentColorName = currentColor.getAttribute("name");
				for (int j=0; j<CARDS_PER_COLOR; j++) {
					cards.add(new PoliticsCard(GameController.getColors().get(currentColorName)));
				}
			}
		}
		
		for (int i=0; i<MULTICOLORED_CARDS; i++) {
			cards.add(new PoliticsCard(null));
		}
		
		return new PoliticsCardDeck(cards);
		
	}
	
}
