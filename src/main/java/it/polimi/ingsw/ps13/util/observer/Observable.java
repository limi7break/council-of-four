package it.polimi.ingsw.ps13.util.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a generic observable object.
 * 
 * An observable object has a list of observers, and can notify them calling notifyObserver, passing them
 * an object whose type is specified by the generic.
 * 
 * @param <C> the type of the object passed to the observer via notifyObserver
 */
public abstract class Observable<C> {

	private List<Observer<C>> observers;
	
	/**
	 * Constructor for the abstract class.
	 * Creates an empty list of observers.
	 * 
	 */
	public Observable(){
		
		this.observers=new ArrayList<>();
	
	}
	
	/**
	 * Adds the passed observer to the observer list of this observable object.
	 * 
	 * @param o the observer to add to the list of observers
	 */
	public void registerObserver(Observer<C> o){
		
		this.observers.add(o);
	
	}
	
	/**
	 * Removes the passed observer from the observer list of this observable object.
	 * 
	 * @param o the observer to remove from the list of observers
	 */
	public void unregisterObserver(Observer<C> o){
		
		this.observers.remove(o);
	
	}
	
	/**
	 * Notifies every observer registered in this object's observer list, passing the specified object as parameter.
	 * The observers will then react to the notify executing the update method, which is defined in the Observer interface.
	 * 
	 * @param c the object to pass to the observers' update method
	 */
	public void notifyObserver(C c){
		
		for(Observer<C> o: this.observers){
			o.update(c);
		}
	
	}
	
}
