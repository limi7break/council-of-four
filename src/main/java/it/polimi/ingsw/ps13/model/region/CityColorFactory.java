package it.polimi.ingsw.ps13.model.region;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

/**
 * 
 *
 */
public final class CityColorFactory {

	private CityColorFactory() { }
	
	public static Map<String, CityColor> createCityColors(Document config, Map<String, Color> colors) {
		
		Map<String, CityColor> cityColors = new HashMap<>();
		
		Element cityColorsElement = (Element) config.getElementsByTagName("citycolors").item(0);
		NodeList colorsElementList = cityColorsElement.getElementsByTagName("color"); 
		for (int i=0; i<colorsElementList.getLength(); i++) {
			Element currentColor = (Element) colorsElementList.item(i);
			
			Element currentBonusElement = (Element) currentColor.getElementsByTagName("bonus").item(0);
			Bonus currentBonus = BonusFactory.createBonus(currentBonusElement);
			String currentColorName = currentColor.getAttribute("name");
			
			cityColors.put(currentColorName, new CityColor(colors.get(currentColorName), currentBonus));
		}
		
		return cityColors;
		
	}
	
}
