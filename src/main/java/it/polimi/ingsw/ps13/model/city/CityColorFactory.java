package it.polimi.ingsw.ps13.model.city;

import java.util.Map;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.controller.GameController;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

/**
 * 
 *
 */
public final class CityColorFactory {

	private CityColorFactory() { }
	
	public static Map<String, CityColor> createCityColors(Document config) {
		
		Map<String, CityColor> cityColors = new HashMap<>();
		
		Element cityColorsElement = (Element) config.getElementsByTagName("citycolors").item(0);
		NodeList cityColorsNodeList = cityColorsElement.getChildNodes(); 
		for (int i=0; i<cityColorsNodeList.getLength(); i++) {
			if (cityColorsNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentColor = (Element) cityColorsNodeList.item(i);
				
				Element currentBonusElement = (Element) currentColor.getElementsByTagName("bonus").item(0);
				Bonus currentBonus = BonusFactory.createBonus(currentBonusElement);
				String currentColorName = currentColor.getAttribute("name");
				
				cityColors.put(currentColorName, new CityColor(GameController.getColors().get(currentColorName), currentBonus));
			}
		}
		
		return cityColors;
		
	}
	
}
