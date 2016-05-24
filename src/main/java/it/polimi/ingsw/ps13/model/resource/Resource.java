package it.polimi.ingsw.ps13.model.resource;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

/**
 * Resources that players have as they take part of a match.
 *
 */
public abstract class Resource implements Bonus, Serializable {
	
	public static final long serialVersionUID = 0L;
	protected int amount;
	
	/**
	 * Constructs a resource given its amount.
	 */
	public Resource(int amount){
		
		this.amount = amount;
		
	}
	
	/**
	 * 
	 * @param quantity the amount that should be added to this resource
	 */
	public void add(int quantity) {
		
		amount += quantity;
		
	}
	
	/**
	 * 
	 * @param quantity the amount that should be subtracted from this resource
	 */
	public void consume(int quantity) {
		
		amount -= quantity;
		
		if ( amount < 0 ) {
			throw new IllegalStateException("Amount of a Resource cannot be < 0");
		}
		
	}
	
	/**
	 * 
	 * @return the amount of this resource.
	 */
	public int getAmount(){
		
		return amount;
		
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		
		return amount == 0;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName()
				+ " x" + amount;
		
	}
	
}
