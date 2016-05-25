package it.polimi.ingsw.ps13.util.observer;

@FunctionalInterface
public interface Observer<C> {
	
	public default void update(C o) {
		
		System.out.println("I am the" + this.getClass().getSimpleName() +
				" I have been notified with the "+o.getClass().getSimpleName());
	
	}

	public void update();
	
}
