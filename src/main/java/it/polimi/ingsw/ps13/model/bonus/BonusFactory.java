package it.polimi.ingsw.ps13.model.bonus;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import it.polimi.ingsw.ps13.model.bonus.Bonus;

/**
 * Creates a bonus from the data in the XML configuration file.
 *
 */
public class BonusFactory {
	
	private BonusFactory() { }
	
	/**
	 * Creates a bonus from structured XML data from the configuration file.
	 * This method is called every time a bonus tag is encountered when analyzing
	 * the XML configuration file. 
	 * 
	 * @param bonus the XML element containing the bonus structure
	 * @return the bonus object created
	 */
	public static void createBonus(Element bonus) {
		
		// implement this method
		// change return type to Bonus
		
	}
	
}
