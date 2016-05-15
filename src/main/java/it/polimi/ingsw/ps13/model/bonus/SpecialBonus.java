package it.polimi.ingsw.ps13.model.bonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.model.resource.special.SpecialResource;

public class SpecialBonus implements Bonus {

	private static final long serialVersionUID = 0L;
	private final Collection<SpecialResource> content;
	
	/**
	 * 
	 * @param bonus
	 */
	public SpecialBonus(Collection<SpecialResource> bonus){
		
		content = new ArrayList<>();
		content.addAll(bonus);
		
	}
	
	/**
	 * 
	 * @param bonus
	 */
	public SpecialBonus(SpecialResource bonus){
		
		content = new ArrayList<>();
		content.add(bonus);
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		Iterator<SpecialResource> it = content.iterator();
		while(it.hasNext()){
			
			it.next().giveTo(player);
			
		}
		
	}
	
	

}
