package it.polimi.ingsw.ps13.util.observer;

/**
 * This interface is implemented by observer objects.
 * 
 * An observer object is registered in an observable object's observer list, and is notified
 * by an observable object when a meaningful event occurs. The notify contains an object passed
 * as parameter, whose type is specified by the generic.
 *
 * @param <C> the type of the object passed to the observer via notifyObserver
 */
@FunctionalInterface
public interface Observer<C> {

	/**
	 * This method contains code executed when an observable object notifies the observer via notifyObserver.
	 * The observable object passes an object as parameter, whose type is specified by the class generic.
	 * 
	 * @param o the object passed to the observer via notifyObserver
	 */
	public void update(C o);
	
}
