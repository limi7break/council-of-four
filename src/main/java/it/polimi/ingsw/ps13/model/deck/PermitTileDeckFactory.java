package it.polimi.ingsw.ps13.model.deck;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

/**
 * 
 *
 */
public final class PermitTileDeckFactory {

	private PermitTileDeckFactory() { }
	
	/**
	 * 
	 * @return the created permit tile deck
	 */
	public static PermitTileDeck createPermitTileDeck(NodeList permitTileElementList) {
		
		List<PermitTile> permitTiles = new ArrayList<>();
		
		for (int i=0; i<permitTileElementList.getLength(); i++) {
			Element currentPermitTile = (Element) permitTileElementList.item(i);
			Element bonus = (Element) currentPermitTile.getElementsByTagName("bonus").item(0);
			
			Set<String> cityNames = new HashSet<>();
			NodeList cityElementList = currentPermitTile.getElementsByTagName("city");
			for (int j=0; j<cityElementList.getLength(); j++) {
				Element currentCity = (Element) cityElementList.item(j);
				String currentCityName = currentCity.getAttribute("name");
				cityNames.add(currentCityName);
			}
			
			permitTiles.add(new PermitTile(BonusFactory.createBonus(bonus), cityNames));
		}
		
		return new PermitTileDeck(permitTiles);
		
	}
	
}
