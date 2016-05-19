package it.polimi.ingsw.ps13.model.council;

import java.util.ArrayList;
import java.util.Collections;
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
public final class CouncillorBalconyFactory {

	private CouncillorBalconyFactory() { }
	
	public static List<Councillor> createCouncillorBalconies(int quantity, List<CouncillorBalcony> councillorBalconies, Document config) {
		
		// Create councillors
		List<Councillor> councillors = new ArrayList<>();
		
		Element politicsColorsElement = (Element) config.getElementsByTagName("politicscolors").item(0);
		NodeList politicsColorsNodeList = politicsColorsElement.getChildNodes();
		for (int i=0; i<politicsColorsNodeList.getLength(); i++) {
			if (politicsColorsNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentColor = (Element) politicsColorsNodeList.item(i);
				String currentColorName = currentColor.getAttribute("name");
				councillors.add(new Councillor(GameController.getColors().get(currentColorName)));
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
