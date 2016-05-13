package it.polimi.ingsw.ps13.model.bonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import it.polimi.ingsw.ps13.model.Player;
import it.polimi.ingsw.ps13.model.resource.Resource;
import it.polimi.ingsw.ps13.model.resource.special.SpecialResource;

public class CompositeBonus implements Bonus {

	private static final long serialVersionUID = 0L;

	private final Collection<Resource> resources;
	private final Collection<SpecialResource> specialResources;
	
	public CompositeBonus(Collection<Resource> resourceBonus, Collection<SpecialResource> specialResourceBonus){
		
		resources = new ArrayList<>();
		specialResources = new ArrayList<>();
		
		resources.addAll(resourceBonus);
		specialResources.addAll(specialResourceBonus);
		
	}
	
	@Override
	public void giveTo(Player player) {
		
		Iterator<Resource> itr = resources.iterator();
		Iterator<SpecialResource> itsr = specialResources.iterator();
		
		while(itr.hasNext()){
			itr.next().giveTo(player);
		}
		
		while(itsr.hasNext()){
			itsr.next().giveTo(player);
		}

	}
}
