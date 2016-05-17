package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 * Creates colors from the data in the XML configuration file. 
 *
 */
public final class ColorFactory { 
	
	private ColorFactory() { }
	
	/**
	 * Populates the map with entries based on data read from the XML configuration file.
	 * See the configuration file for details on the XML syntax.
	 * 
	 * @param colors a map which associates a name to a color.
	 * @param config the parsed configuration XML file
	 */
	public static void createColors(Map<String, Color> colors, Document config) {
		
		Element colorsElement = (Element) config.getElementsByTagName("colors").item(0);
		NodeList colorsNodeList = colorsElement.getElementsByTagName("color");
		
		for (int i=0; i<colorsNodeList.getLength(); i++) {
			Element currentColor = (Element) colorsNodeList.item(i);
			String colorName = currentColor.getAttribute("name");
			int r = Integer.parseInt(currentColor.getAttribute("r"));
			int g = Integer.parseInt(currentColor.getAttribute("g"));
			int b = Integer.parseInt(currentColor.getAttribute("b"));
			
			colors.put(colorName, new Color(r, g, b));
		}
		
	}
	
}
