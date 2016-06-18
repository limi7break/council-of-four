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
	public ConcreteBonus(List<Bonus> bonusList){
		
		contents = bonusList;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConcreteBonus other = (ConcreteBonus) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		return true;
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
			sb.append(bonus.toString()).append(" ");
		}
		
		return sb.toString();
		
	}
}
