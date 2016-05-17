package it.polimi.ingsw.ps13.model.bonus;

import java.util.List;
import java.util.Iterator;
import java.util.Collections;
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
}