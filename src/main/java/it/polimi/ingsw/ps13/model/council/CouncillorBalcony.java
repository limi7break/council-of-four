package it.polimi.ingsw.ps13.model.council;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;

/**
 * This class represents a council balcony as a list of councillors.
 * 
 * The number of councillors is fixed and specified by the constant COUNCILLORS.
 * In the original game, there are 4 in each balcony.
 * 
 * Whenever a councillor has to be inserted in the balcony, it's added in the
 * first position, and the one in the last position is dropped, effectively
 * causing a 'slide' of the councillors.
 */
public class CouncillorBalcony implements Serializable {

	private static final int COUNCILLORS = 4;
	private static final int[] coinsPerMissingCards = new int[] {0, 4, 7, 10};
	private static final long serialVersionUID = 0L;
	private final LinkedList<Councillor> councillorList;
	
	/**
	 * Creates a council balcony with a fixed number of councillors,
	 * specified in the constant COUNCILLORS.
	 * 
	 * @param councillors a collection of councillors to put in the balcony
	 */
	public CouncillorBalcony(Collection<Councillor> councillors){
		
		// Checks if the size of the collection of Councillor
		// matches the intended number of councillors.
		if (councillors.size() != COUNCILLORS) {
			throw new IllegalArgumentException("Number of councillors in a balcony should be " + COUNCILLORS);
		}
		
		councillorList = new LinkedList<>();
		councillorList.addAll(councillors);
		
	}

	/**
	 * Inserts a councillor in the balcony:
	 * the new one is added to the list at the first position
	 * and it's going to replace the one at the last position,
	 * which is removed and returned.
	 * 
	 * This method does not change the number of councillors in the balcony,
	 * which indeed should never change after the object is constructed.
	 * 
	 * This method needs to be invoked in order to accomplish the main action "Elect Councillor".
	 * 
	 * @param councillor the councillor to be added in the first position
	 * @return the last councillor of the balcony, which has been removed
	 */
	public Councillor insertCouncillor(Councillor councillor){
		
		councillorList.addFirst(councillor);
		return councillorList.removeLast();
		
	}
	
	/**
	 * Checks if the council can be satisfied with the passed collection
	 * of cards and amount of coins.
	 * 
	 * Note that there is a multicolored politics card which matches every councillor.
	 * 
	 * @param cards the collection of politics cards selected to satisfy the council
	 * @param playerCoins the amount of coins of the player
	 * @return true, if the council is satisfiable with the given politics cards
	 */
	public boolean isSatisfiable(Collection<PoliticsCard> cards, int playerCoins) {
		
		// The number of cards passed should never be greater than the
		// number of councillors in the balcony
		if ((cards.isEmpty()) || (cards.size() > COUNCILLORS)) {
			throw new IllegalArgumentException("Politics cards used to satisfy a balcony should be at least 1 and at most " + COUNCILLORS);
		}
				
		int numberOfMulticoloredCards = calculateNumberOfMulticoloredCards(cards);
		int numberOfMatches = calculateNumberOfMatches(cards);
		
		// A council is satisfiable if the collection of passed cards has
		// at least one multicolored card OR one card whose color matches
		// the color of one of the councillors...
		if ( (numberOfMulticoloredCards == 0) && (numberOfMatches == 0) ) {
			return false;
		}
		
		// ...provided the player has enough coins to make up for the possibly
		// missing cards
		if ( (playerCoins - coinsToPay(numberOfMatches, numberOfMulticoloredCards)) < 0) {
			return false;
		}
		
		return true;
		
	}

	/**
	 * Counts the number of cards that match with a councillor.
	 * 
	 * @param cards a collection of politics cards
	 * @return the number of cards that match with a councillor
	 */
	public int calculateNumberOfMatches(Collection<PoliticsCard> cards) {
		
		int numberOfMatches = 0;
		int numberOfCards = cards.size();
		boolean found = false;
		for (Councillor councillor : councillorList) {
			for (Iterator<PoliticsCard> it = cards.iterator(); it.hasNext() && !found;) {
				PoliticsCard card = it.next();
				if (councillor.getColor() == card.getColor()) {
					found = true;
					it.remove();
					numberOfMatches++;
				}
			}
			found = false;
		}
		
		if (numberOfMatches != numberOfCards) {
			// throw new CHECKED exception (declared in method)
			return -1;
		} else {
			return numberOfMatches;
		}
		
	}

	/**
	 * Counts the number of multicolored cards in a collection of politics cards.
	 * 
	 * @param cards a collection of politics cards
	 * @return the number of multicolored cards in the passed collection of cards
	 */
	public int calculateNumberOfMulticoloredCards(Collection<PoliticsCard> cards) {
		
		int numberOfMulticoloredCards = 0;
		for (Iterator<PoliticsCard> it = cards.iterator(); it.hasNext();) {
			PoliticsCard card = it.next();
			if (card.isMultiColored()) {
				it.remove();
				numberOfMulticoloredCards++;
			}
		}
		return numberOfMulticoloredCards;
	}
	
	/**
	 * This method returns the number of coins the player has to pay in order to satisfy
	 * the council. The coins are paid to make up for any missing cards.
	 * 
	 * @param numberOfMatches the number of selected cards matching with a councillor color
	 * @param numberOfMulticoloredCards the number of selected multicolored cards
	 * @return the number of coins the player has to pay in order to satisfy the council
	 */
	public int coinsToPay(int numberOfMatches, int numberOfMulticoloredCards) {
		
		if ((numberOfMatches == 0) && (numberOfMulticoloredCards == 0)) {
			throw new IllegalArgumentException("Number of matches and number of multicolored cards cannot be both zero (council is not satisfiable)");
		} else {
			return coinsPerMissingCards[COUNCILLORS - numberOfMatches - numberOfMulticoloredCards] + numberOfMulticoloredCards;
		}
		
	}
	
	/**
	 * @return a list of councillors belonging to this balcony.
	 */
	public Collection<Councillor> getCouncillors(){
		
		return councillorList;
		
	}

}
