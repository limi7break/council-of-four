package it.polimi.ingsw.ps13.model.bonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import it.polimi.ingsw.ps13.model.Player;
import it.polimi.ingsw.ps13.model.resource.special.SpecialResource;

public class SpecialBonus implements Bonus {

	private static final long serialVersionUID = 0L;
	private final Collection<SpecialResource> content;
	
	public SpecialBonus(Collection<SpecialResource> bonus){
		
		content = new ArrayList<>();
		content.addAll(bonus);
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		Iterator<SpecialResource> it = content.iterator();
		while(it.hasNext()){
			
			it.next().giveTo(player);
			
		}
		
	}
	
	

}
