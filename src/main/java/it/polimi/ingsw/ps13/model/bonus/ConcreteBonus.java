package it.polimi.ingsw.ps13.model.bonus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class represents a concrete bonus.
 * It is instantiated only by the BonusFactory class, whenever a new bonus
 * needs to be created.
 *
 */
public class ConcreteBonus implements Bonus {

	private static final long serialVersionUID = 0L;
	private final List<Bonus> contents;
	
	/**
	 * Constructs a new bonus.
	 * 
	 * @param bonusList
	 */
	protected ConcreteBonus(List<Bonus> bonusList){
		
		contents = bonusList;
		
	}
	
	/**
	 * No-arg constructor for an empty bonus.
	 * 
	 */
	protected ConcreteBonus() {
		
		contents = new ArrayList<>();
		
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		
		return contents.isEmpty();
		
	}
	
	/**
	 * Iterates over a list of bonuses, and calls their specific
	 * implementation of the method giveTo.
	 * 
	 * @param player the player who receives the bonus
	 */
	@Override
	public void giveTo(Player player) {
		
		Iterator<Bonus> it = contents.iterator();
		while(it.hasNext()){
			
			it.next().giveTo(player);
			
		}

	}
	
	/**
	 * 
	 * @return the list of bonuses 
	 */
	public List<Bonus> getContents() {
		
		return Collections.unmodifiableList(contents);
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		if (this.isEmpty()) {
			sb.append("Wow! It's fucking nothing!\n");
		}
		for (Bonus bonus : contents) {
			sb.append(bonus.toString()).append("\n");
		}
		
		return sb.toString();
		
	}
}
