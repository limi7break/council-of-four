package it.polimi.ingsw.ps13.model.deck;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.io.Serializable;

/**
 * This class models and manages a Deck of permit tiles (deck and an arbitrary
 * number of visible tiles). Once a deck is created it is possible to draw tiles,
 * shuffle the deck, and replace the visible tiles with other tiles drawn from the Deck. 
 * 
 * The tiles are drawn from the top of the Deck.
 * There is no discard pile: whenever the visible tiles need to be replaced,
 * they are put face down directly at the bottom of the Deck and the others
 * are drawn from the top.
 * 
 * The integers representing the position of the visible tiles start from 0.
 *
 */
public class PermitTileDeck<E extends PermitTile> extends Deck<E> implements Serializable {

	private static final long serialVersionUID = 0L;
	private static final int VISIBLE_TILES = 2;
	private Map<Integer, E> visibleTiles = new HashMap<>();
	
	/**
     * Instantiates a new Deck populated with a Collection of permit tiles.
     * The tiles are shuffled when the deck is created.
     * Some permit tiles are then removed from the Deck and placed
     * face up in the Map containing the visible permit tiles.
     *
     * @param cards a Collection of permit tiles to put inside the deck
     */
	public PermitTileDeck(Collection<E> cards) {
		
		super(cards);
		shuffleDeck();
		placeTiles();
		
	}
	
	/**
	 * This method is only used by the class when one of the visible
	 * tiles is taken and needs to be replaced with a new tile from the
	 * top of the deck.
	 * 
	 * @return a permit tile from the top of the deck, removing it from the deck
	 */
	@Override
	E drawCard() {
		
		return drawPile.removeFirst();
		
	}
	
	/**
	 * Puts a permit tile at the bottom of the deck, facing down. 
	 * 
	 * @param card the permit tile to discard
	 */
	@Override
	public void discardCard(E card) {
		
		if (card.isFacingUp()) {
			card.flip();
		}
		
		drawPile.addLast(card);
		
	}
	
	/**
     * Checks if both the draw pile and the ArrayList of visible permit tiles are empty.
     *
     * @return true, if the drawPile and the visiblePermitTiles are empty
     */
	@Override
	public boolean isEmpty() {
		
		return drawPile.isEmpty() && visibleTiles.isEmpty();
		
	}
	
	/**
	 * 
	 */
	public void changeTiles() {
		
		if (!isDrawPileEmpty()) {
		
			for (Integer i : visibleTiles.keySet()) {
				discardCard(visibleTiles.remove(i));
			}
			
			placeTiles();
		
		}
		
	}
	
	/**
	 * Initializes the map of visible tiles by populating it with random tiles from
	 * the top of the deck, and associating each one with an incremental number from 0
	 * to VISIBLE_TILES - 1.
	 *
	 * Method is private because it is called only in the constructor when the deck is
	 * created, and in the changeTiles() method.
	 *
	 */
	private void placeTiles() {
		
		for (int i=0; i<VISIBLE_TILES; i++) {
			visibleTiles.put(i, drawCard());
		}
		
	}
	
	/**
	 * Takes a specific tile from the Map of visible tiles.
	 *
	 * @param position the position of the tile in the visibleTiles Map
	 * @return the selected permit tile
	 */
	public E takeTile(int position) {
		
		if (visibleTiles.containsKey(position)) {
			// Remove tile from the Map 
			E tile = visibleTiles.remove(position);
			
			// If the draw pile is not empty, Draw a new card from the
			// top of the Deck and put it face up in the Map of visible tiles
			if ( !(drawPile.isEmpty()) ) {
				visibleTiles.put(position, drawCard());
			}
			
			// Return the previously removed tile
			return tile;
			
		} else {
			throw new NoSuchElementException();
		}
		
	}
}
