package it.polimi.ingsw.ps13.model.deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.Serializable;

/**
 * This class models and manages a Deck of politics cards (deck and discarded
 * pile). Once a deck is created it is possible to draw cards, shuffle the deck,
 * discard used cards in a discard pile, etc. When there are no more cards in a
 * deck the discard pile (if not empty) is automatically shuffled and used.
 * 
 */
public class PoliticsCardDeck extends Deck<PoliticsCard> implements Serializable {
	
	private static final long serialVersionUID = 0L;
    private final List<PoliticsCard> discardPile;
    
    /**
     * Instantiates a new Deck populated with a Collection of politics cards.
     * The cards are shuffled when the deck is created.
     *
     * @param cards a Collection of politics cards to put inside the deck
     */
    public PoliticsCardDeck(Collection<PoliticsCard> cards) {

        super(cards);
        discardPile = new ArrayList<>();
        this.shuffleDeck();
        
    }
    
    /**
     * Draws a politics card by removing it from the stack. If the deck is empty it
     * checks if there are cards in the discardPile, if this empty then there
     * are no more cards and it returns null, otherwise the discardPile becomes
     * the new deck and cards are automatically shuffled.
     */
    @Override
    public PoliticsCard drawCard() {

        if (this.isEmpty()) {
            // No more cards in the deck, nor in the discardPile
            return null;
        } else {
            if (this.isDrawPileEmpty()) {
                // Move the cards from the discardPile into the empty deck.
                drawPile.addAll(discardPile);
                discardPile.clear();
                // Shuffle the deck
                this.shuffleDeck();
                // Pop a card and return it
                return drawPile.removeFirst();
            } else {
                return drawPile.removeFirst();
            }
        }

    }
    
    /**
     * Adds a card to the discardPile.
     *
     * @param card the politics card to discard
     */
    @Override
    public void discardCard(PoliticsCard card) {

        discardPile.add(card);

    }

    /**
     * Checks if both the drawPile and the discardPile are empty.
     *
     * @return true, if the drawPile and the discardPile are empty
     */
    @Override
    public boolean isEmpty() {

        return drawPile.isEmpty() && discardPile.isEmpty();

    }
}
