package it.polimi.ingsw.ps13.model.bonus;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import it.polimi.ingsw.ps13.model.player.Player;

public class CompositeBonus implements Bonus {

	private static final long serialVersionUID = 0L;
	private final List<Bonus> content;
	
	/**
	 * Constructor for a single bonus.
	 * 
	 * @param bonus
	 */
	protected CompositeBonus(Bonus bonus) {
		
		content = new ArrayList<>();
		content.add(bonus);
		
	}
	
	/**
	 * Constructor for two bonuses.
	 * 
	 * @param bonusOne
	 * @param bonusTwo
	 */
	protected CompositeBonus(Bonus bonusOne, Bonus bonusTwo) {
		
		content = new ArrayList<>();
		content.add(bonusOne);
		content.add(bonusTwo);
		
	}
	
	/**
	 * Constructor for multiple bonuses.
	 * 
	 * @param bonuses
	 */
	protected CompositeBonus(Collection<Bonus> bonusList){
		
		content = new ArrayList<>();
		content.addAll(bonusList);
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		Iterator<Bonus> it = content.iterator();
		while(it.hasNext()){
			
			it.next().giveTo(player);
			
		}

	}
}
