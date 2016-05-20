package it.polimi.ingsw.ps13.model.council;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 *
 */
public final class CouncillorBalconyFactory {

	private static final int COUNCILLORS_PER_COLOR = 4;
	
	private CouncillorBalconyFactory() { }
	
	public static List<Councillor> createCouncillorBalconies(int quantity, List<CouncillorBalcony> councillorBalconies, Map<String, Color> colors, Document config) {
		
		// Create councillors
		List<Councillor> councillors = new ArrayList<>();
		
		Element politicsColorsElement = (Element) config.getElementsByTagName("politicscolors").item(0);
		NodeList colorsElements = politicsColorsElement.getElementsByTagName("color");
		for (int i=0; i<colorsElements.getLength(); i++) {
			Element currentColor = (Element) colorsElements.item(i);
			String currentColorName = currentColor.getAttribute("name");
			for (int j=0; j<COUNCILLORS_PER_COLOR; j++) {
				councillors.add(new Councillor(colors.get(currentColorName)));
			}
		}
		
		// Shuffle councillors
		Collections.shuffle(councillors);
		
		// Create balconies (numberOfRegions+1)
		for (int i=0; i<quantity; i++) {
			councillorBalconies.add(new CouncillorBalcony(councillors));
		}
		
		// Return the list of leftover councillors
		return councillors;
		
	}
	
}
