package it.polimi.ingsw.ps13.model.bonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import it.polimi.ingsw.ps13.model.Player;
import it.polimi.ingsw.ps13.model.resource.Resource;

/**
 * 
 *
 */
public class ResourceBonus implements Bonus {

	private static final long serialVersionUID = 0L;
	private final Collection<Resource> content;

	/**
	 * 
	 * @param bonus
	 */
	public ResourceBonus(Collection<Resource> bonus){
		
		content = new ArrayList<>();
		content.addAll(bonus);
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		Iterator<Resource> it = content.iterator();
		while(it.hasNext()){
			
			it.next().giveTo(player);
			
		}
		
	}
	
	

}
