package it.polimi.ingsw.ps13.model.deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class models and manages a Deck of permit tiles (deck and an arbitrary
 * number of visible tiles). Once a deck is created it is possible to draw tiles,
 * shuffle the deck, and replace the visible tiles with other tiles drawn from the Deck. 
 * 
 * The tiles are drawn from the top of the Deck.
 * There is no discard pile: whenever the visible tiles need to be replaced,
 * they are put directly at the bottom of the Deck and the others are drawn from the top.
 * 
 * The currently visible permit tiles are put in a list.
 *
 */
public class PermitTileDeck extends Deck<PermitTile> implements Serializable {

	private static final long serialVersionUID = 0L;
	private static final int VISIBLE_TILES = 2;
	private final List<PermitTile> visibleTiles;
	
	/**
     * Instantiates a new Deck populated with a Collection of permit tiles.
     * The tiles are not shuffled when the deck is created.
     * The list of visible permit tiles is empty when the deck is created, so
     * the method changeTiles() needs to be called.
     *
     * @param cards a Collection of permit tiles to put inside the deck
     */
	protected PermitTileDeck(Collection<PermitTile> cards) {
		
		super(cards);
		visibleTiles = new ArrayList<>();
		
	}
	
	/**
	 * This method is only used by the class when one of the visible
	 * tiles is taken and needs to be replaced with a new tile from the
	 * top of the deck.
	 * 
	 * @return a permit tile from the top of the deck, removing it from the deck
	 */
	@Override
	PermitTile drawCard() {
		
		return getDrawPile().removeFirst();
		
	}
	
	/**
	 * Puts a permit tile at the bottom of the deck. 
	 * 
	 * @param card the permit tile to discard
	 */
	@Override
	public void discardCard(PermitTile card) {
		
		getDrawPile().addLast(card);
		
	}
	
	/**
     * Checks if both the draw pile and the list of visible permit tiles are empty.
     *
     * @return true, if drawPile and visibleTiles are both empty
     */
	@Override
	public boolean isEmpty() {
		
		return getDrawPile().isEmpty() && visibleTiles.isEmpty();
		
	}
	
	/**
	 * 
	 * 
	 */
	public void changeTiles() {
		
		if (!isDrawPileEmpty()) {

			int numberOfVisibleTiles = visibleTiles.size();

			for (int i=0; i<numberOfVisibleTiles; i++) {
				discardCard(visibleTiles.remove(0));
			}
			
			placeTiles();
		
		}
		
	}
	
	/**
	 * Initializes the list of visible tiles by populating it with random tiles from
	 * the top of the deck.
	 *
	 * Method is private because it is called only in the constructor when the deck is
	 * created, and in the changeTiles() method.
	 *
	 */
	private void placeTiles() {
		
		for (int i=0; i<VISIBLE_TILES; i++) {
			visibleTiles.add(drawCard());
		}
		
	}
	
	/**
	 * Takes a specific tile from the list of visible tiles.
	 *
	 * @param position the position of the tile in the visibleTiles list. (from 0 to VISIBLE_TILES-1)
	 * @return the selected permit tile
	 */
	public PermitTile takeTile(int position) {
		
		if ( !(visibleTiles.isEmpty()) && (position <= visibleTiles.size()-1) ) {
			// If the draw pile is not empty, draw a new card from the
			// top of the deck and put it in the list of visible tiles
			if ( !(getDrawPile().isEmpty()) ) {
				visibleTiles.add(drawCard());
			}
			
			// Return the previously removed tile
			return visibleTiles.remove(position);
			
		} else {
			throw new ArrayIndexOutOfBoundsException("The selected tile does not exist");
		}
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		String str = super.toString();
		
		StringBuilder sb = new StringBuilder(str);
			
		sb.append("\nVisible Tiles:\n");
		for (PermitTile permitTile : visibleTiles) {
			sb.append(permitTile.toString()).append("\n");
		}
		
		return sb.toString();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PermitTile> getVisibleTiles() {
		
		return visibleTiles;
		
	}

	/**
	 * set 2 chosen visible tiles
	 * created for testing purposes
	 * @param permitTile1
	 * @param permitTile2
	 */
	protected void setVisibleTiles(PermitTile permitTile1,PermitTile permitTile2){

		visibleTiles.clear();
		visibleTiles.add(permitTile1);
		visibleTiles.add(permitTile2);

	}

	/**
	 * set 1 chosen visible tile
	 * created for testing purposes
	 * @param permitTile
	 */
	protected void setVisibleTile(PermitTile permitTile){

		visibleTiles.clear();
		visibleTiles.add(permitTile);

	}

	/**
	 * clear visible tiles
	 * created for testing purposes
	 * @param permitTile
	 */
	protected void clearVisibleTiles(){
		visibleTiles.clear();
	}


}
