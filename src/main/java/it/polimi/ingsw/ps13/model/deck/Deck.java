package it.polimi.ingsw.ps13.model.deck;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.io.Serializable;

/**
 * This is an abstract class whose attributes and methods are in common
 * between the two types of Deck (PoliticsCardDeck and PermitTileDeck).
 * 
 */
public abstract class Deck<E extends Serializable> implements Serializable {

	private static final long serialVersionUID = 0L;
	protected final Deque<E> drawPile;
	
	protected Deck(Collection<E> cards) {
		
		drawPile = new LinkedList<>();
        drawPile.addAll(cards);
		
	}
	
	abstract E drawCard();
	
	abstract void discardCard(E card);
	
	abstract boolean isEmpty();
	
	/**
     * Checks if the drawPile of the deck is empty.
     *
     * @return true, if the deck is empty
     */
    public boolean isDrawPileEmpty() {

        return drawPile.isEmpty();

    }
	
    /**
     * Shuffle the deck of cards.
     */
    public void shuffleDeck() {

        Collections.shuffle((LinkedList<?>) drawPile);

    }
}
