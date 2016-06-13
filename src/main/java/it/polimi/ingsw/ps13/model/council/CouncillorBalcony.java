package it.polimi.ingsw.ps13.model.council;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
 * 
 */
public class CouncillorBalcony implements Serializable {

	public static final int COUNCILLORS_PER_BALCONY = 4;
	private static final int[] coinsPerMissingCard = new int[] {0, 4, 7, 10};
	private static final long serialVersionUID = 0L;
	private final LinkedList<Councillor> councillorList;
	
	/**
	 * Creates a council balcony with a fixed number of councillors.
	 * A shuffled list of councillors is passed, the constructor removes the first
	 * COUNCILLORS_PER_BALCONY councillors and adds them to the balcony.
	 * 
	 * @param councillors a collection of councillors to put in the balcony
	 */
	protected CouncillorBalcony(List<Councillor> councillors) {
		
		councillorList = new LinkedList<>();
		
		for (int i=0; i<COUNCILLORS_PER_BALCONY; i++) {
			councillorList.add(councillors.remove(0));
		}
		
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
	 * 
	 */
	public Councillor insertCouncillor(Councillor councillor){
		
		councillorList.addFirst(councillor);
		return councillorList.removeLast();
		
	}

	/**
	 * Counts the number of cards that match with a councillor.
	 * 
	 * @param cards a collection of politics cards
	 * @return the number of cards that match with a councillor
	 */
	protected int calculateNumberOfMatches(Collection<Color> colors) {
		
		List<Color> colorsCopy = new ArrayList<>(colors);
		
		int numberOfMatches = 0;
		boolean matchFound = false;
		
		for (Councillor councillor : councillorList) {
			for (Iterator<Color> it = colorsCopy.iterator(); it.hasNext() && !matchFound;) {
				Color color = it.next();
				if (councillor.getColor().equals(color)) {
					matchFound = true;
					it.remove();
					numberOfMatches++;
				}
			}
			matchFound = false;
		}
		
		return numberOfMatches;
		
	}

	/**
	 * Counts the number of multicolored cards in a collection of politics cards.
	 * 
	 * @param cards a collection of politics cards
	 * @return the number of multicolored cards in the passed collection of cards
	 */
	protected int calculateNumberOfMulticoloredCards(Collection<Color> colors) {
		
		int numberOfMulticoloredCards = 0;
		for (Iterator<Color> it = colors.iterator(); it.hasNext();) {
			Color color = it.next();
			if (color.equals(PoliticsCard.jollyColor)) {
				numberOfMulticoloredCards++;
			}
		}
		return numberOfMulticoloredCards;
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
	public boolean isSatisfiable(Collection<Color> colors, int playerCoins) {
		
		boolean satisfiable = true;
		int numberOfColors = colors.size();
		
		// The number of cards passed should never be greater than the
		// number of councillors in the balcony
		if ((numberOfColors <= 0) || (numberOfColors > COUNCILLORS_PER_BALCONY)) {
			throw new IllegalArgumentException("Politics cards used to satisfy a balcony should be at least 1 and at most " + COUNCILLORS_PER_BALCONY);
		}
				
		int numberOfMatches = calculateNumberOfMatches(colors);
		int numberOfMulticoloredCards = calculateNumberOfMulticoloredCards(colors);
		
		// If the number of matches is less than the number of cards played (except
		// for the multicolored cards), then the player has selected some extra card(s)
		// that didn't match with any of the councillor's colors
		if (numberOfMatches != numberOfColors - numberOfMulticoloredCards) {
			satisfiable = false;
		}
		
		// A council is satisfiable if among the played cards there is
		// at least one multicolored card, OR at least one card whose color
		// matches the color of one of the councillors...
		if ( (numberOfMulticoloredCards == 0) && (numberOfMatches == 0) ) {
			satisfiable = false;
		}
		
		// ...provided the player has got enough coins to make up for
		// the missing cards.
		if ( satisfiable && (playerCoins - coinsToPay(colors)) < 0 ) {
			satisfiable = false;
		}
		
		return satisfiable;
		
	}
	
	/**
	 * This method returns the number of coins the player has to pay in order to satisfy
	 * the council. The coins are paid to make up for any missing cards.
	 * 
	 * @param cards the cards selected by the player
	 * @return the number of coins the player has to pay in order to satisfy the council
	 */
	public int coinsToPay(Collection<Color> colors) {
		
		if (colors.isEmpty()) {
			throw new IllegalArgumentException("You cannot satisfy a council without using at least a politics card");
		} else {
			return coinsPerMissingCard[COUNCILLORS_PER_BALCONY - colors.size()] + calculateNumberOfMulticoloredCards(colors);
		}
		
	}
	
	/**
	 * @return a list of councillors belonging to this balcony.
	 */
	public Collection<Councillor> getCouncillors(){
		
		return Collections.unmodifiableList(councillorList);
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return councillorList.toString();
		
	}
	
	/**
	 * 
	 * THESE METHODS ARE HERE ONLY TO AVOID ERRORS IN TEST CLASSES AND SHOULD BE
	 * REMOVED, SOONER OR LATER.
	 * 
	 * 
	 * 
	 */
	
	protected int calculateNumberOfMatches(List<? extends PoliticsCard> cards) {
		
		Collection<Color> colors = cardsToColors(cards);
		
		return calculateNumberOfMatches(colors);
		
	}
	
	protected int calculateNumberOfMulticoloredCards(List<? extends PoliticsCard> cards) {
		
		Collection<Color> colors = cardsToColors(cards);
		
		return calculateNumberOfMulticoloredCards(colors);
		
	}
	
	public int coinsToPay(List<? extends PoliticsCard> cards) {
		
		Collection<Color> colors = cardsToColors(cards);
		
		return coinsToPay(colors);
		
	}
	
	public boolean isSatisfiable(List<? extends PoliticsCard> cards, int playerCoins) {
		
		Collection<Color> colors = cardsToColors(cards);
		
		return isSatisfiable(colors, playerCoins);
		
	}
	
	private Collection<Color> cardsToColors(List<? extends PoliticsCard> cards) {
		
		Collection<Color> colors = new ArrayList<>();
		
		for (PoliticsCard card : cards) {
			colors.add(card.getColor());
		}
		
		return colors;
		
	}

}
