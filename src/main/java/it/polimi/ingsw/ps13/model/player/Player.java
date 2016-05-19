package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.region.City;

/**
 * 
 *
 */
public class Player implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private final String name;
	private final Color color;
	private final PlayerSupply supply;
	private int nobilityPosition;
	private int mainActions;							// number of available main actions
	private boolean quickAction;
	
	private final Board match;
	
	/**
	 * 
	 * @param name the name of the player
	 * @param color the color of the player
	 */
	public Player(String name, Color color, int position, Board board) { 
		
		this.name = name;
		this.color = color;
		supply = new PlayerSupply(position);
		
		initHand(supply, board);
		
		nobilityPosition = 0;
		mainActions = 0;
		quickAction = false;
		match = board;
		
	}
	
	/**
	 * Sets the hand of a player according to the rules of the real game (6 politics cards for each player).
	 * Cards are drawn from the Board's politics card deck.
	 * 
	 * @param playerSupply
	 * @param board
	 */
	public void initHand(PlayerSupply playerSupply, Board board){
		
		Collection<PoliticsCard> hand = new ArrayList<>();
		
		for(int i=0; i<6; i++){
			
			hand.add(board.getPoliticsCardDeck().drawCard());
			
		}
		
		playerSupply.setPoliticsCards(hand);
		
	}
	
	/**
	 * 
	 * @param board
	 */
	public void drawPoliticsCard(int number){
		
		for(int i=0; i<number; i++){
		
			this.getSupply().getPoliticsCards().add(match.getPoliticsCardDeck().drawCard());
			
		}
		
	}
	
	/**
	 * 
	 * @param position
	 * @param deck
	 */
	public void takePermitTile(int position, PermitTileDeck deck){
		
		this.getSupply().getPermitTiles().add(deck.takeTile(position));
		
	}
	
	//getter and setter
	
	/**
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * 
	 * @return the color of the player
	 */
	public Color getColor() {
		
		return color;
		
	}
	
	/**
	 * 
	 * @param city the city to check whether the player has built on
	 * @return true if the player has built an emporium on that city
	 */
	public boolean hasBuiltOn(City city) {
		
		return supply.getCitySet().contains(city);
		
	}
	
	/**
	 * 
	 * @return the number of cities on which the player has built an emporium
	 */
	public int getNumberOfCities() {
		
		return supply.getCitySet().size();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMainActions() {
		
		return mainActions;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public PlayerSupply getSupply() {
		
		return supply;
		
	}
	
	/**
	 * 
	 * @param mainActions
	 */
	public void setMainActions(int mainActions) {
		
		this.mainActions = mainActions;
		
	}
	
	/**
	 * 
	 * @param quickAction
	 */
	public void setQuickAction(boolean quickAction) {
		
		this.quickAction = quickAction;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isQuickAction() {
		
		return quickAction;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNobilityPosition() {
		
		return nobilityPosition;
		
	}
	
	/**
	 * 
	 * @param nobilityPosition
	 */
	public void setNobilityPosition(int nobilityPosition) {
		
		this.nobilityPosition = nobilityPosition;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Board getMatch() {
		
		return match;
		
	}
	
}
