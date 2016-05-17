package it.polimi.ingsw.ps13.model.bonus;

import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import it.polimi.ingsw.ps13.model.resource.Coins;
import it.polimi.ingsw.ps13.model.resource.Assistants;
import it.polimi.ingsw.ps13.model.resource.VictoryPoints;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.bonus.NobilityPointsBonus;
import it.polimi.ingsw.ps13.model.bonus.PoliticsCardsBonus;
import it.polimi.ingsw.ps13.model.bonus.MainActionsBonus;
import it.polimi.ingsw.ps13.model.bonus.RegainPermitTileBonus;
import it.polimi.ingsw.ps13.model.bonus.RegainRewardTokenBonus;
import it.polimi.ingsw.ps13.model.bonus.VisiblePermitTileBonus;

/**
 * Creates a bonus from the data in the XML configuration file.
 *
 */
public final class BonusFactory {
	
	private BonusFactory() { }
	
	/**
	 * Creates a bonus from structured XML data from the configuration file.
	 * This method is called every time a bonus tag is encountered when analyzing
	 * the XML configuration file.
	 * 
	 * @param bonus the XML element containing the bonus structure
	 * @return the bonus object created
	 */
	@SuppressWarnings("all")
	public static Bonus createBonus(Element bonusElement) {

		List<Bonus> bonusList = new ArrayList<>();
		
		NodeList nodeList = bonusElement.getChildNodes();
		for (int i=0; i<nodeList.getLength(); i++) {
			
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {	
				Element currentElement = (Element) nodeList.item(i);
			
				switch (currentElement.getTagName().toLowerCase()) {
					case "coins":
						bonusList.add(new Coins(Integer.parseInt(currentElement.getAttribute("value"))));
						break;
					case "victorypoints":
						bonusList.add(new VictoryPoints(Integer.parseInt(currentElement.getAttribute("value"))));
						break;
					case "assistants":
						bonusList.add(new Assistants(Integer.parseInt(currentElement.getAttribute("value"))));
						break;
					case "politicscards":
						bonusList.add(new PoliticsCardsBonus(Integer.parseInt(currentElement.getAttribute("value"))));
						break;
					case "nobilitypoints":
						bonusList.add(new NobilityPointsBonus(Integer.parseInt(currentElement.getAttribute("value"))));
						break;
					case "mainactions":
						bonusList.add(new MainActionsBonus(Integer.parseInt(currentElement.getAttribute("value"))));
						break;
					case "regainpermittilebonus":
						bonusList.add(new RegainPermitTileBonus());
						break;
					case "regainrewardtokenbonus":
						bonusList.add(new RegainRewardTokenBonus());
						break;
					case "visiblepermittile":
						bonusList.add(new VisiblePermitTileBonus());
						break;
					default:
						// no action is taken when unknown tag is encountered
						break;
				} // end switch
			} // end if
			
		} // end for
		
		return new ConcreteBonus(bonusList);
		
	}
	
}
