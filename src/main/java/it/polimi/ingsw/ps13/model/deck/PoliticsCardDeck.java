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
     * The cards are not shuffled when the deck is created.
     *
     * @param cards a Collection of politics cards to put inside the deck
     */
    protected PoliticsCardDeck(Collection<PoliticsCard> cards) {

        super(cards);
        discardPile = new ArrayList<>();
        
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
            	getDrawPile().addAll(discardPile);
                discardPile.clear();
                // Shuffle the deck
                this.shuffleDeck();
                // Pop a card and return it
                return getDrawPile().removeFirst();
            } else {
                return getDrawPile().removeFirst();
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
     * Adds cards to the discardPile.
     * 
     * @param cards
     */
    public void discardCard(Collection<PoliticsCard> cards) {
    	
    	discardPile.addAll(cards);
    	
    }

    /**
     * Checks if both the drawPile and the discardPile are empty.
     *
     * @return true, if the drawPile and the discardPile are empty
     */
    @Override
    public boolean isEmpty() {

        return getDrawPile().isEmpty() && discardPile.isEmpty();

    }
    
    /**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		String str = super.toString();
		
		StringBuilder sb = new StringBuilder(str);
			
		sb.append("\nDiscard Pile:\n");
		for (PoliticsCard politicsCard : discardPile) {
			sb.append(politicsCard.toString()).append("\n");
		}
		
		return sb.toString();
		
	}

    /**
     * created for testing purposes
     * @return the discard pile as a list of politics cards
     */
    protected List <PoliticsCard> getDiscardPile() {
        return discardPile;
    }

    /**
     * add 1 chosen card to discard pile
     * created for testing purposes
     * @param card
     */
    protected void addCardToDiscardPile(PoliticsCard card){

        discardPile.add(card);

    }

    /**
     * clear visible tiles
     * created for testing purposes
     */
    protected void clearDiscardPile(){
        discardPile.clear();
    }

   
}
