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
	 * Constructs a resource with the specified initial amount.
	 * 
	 * @param amount the initial amount of the resource
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
	 * Returns the amount of this resource.
	 * 
	 * @return the amount of this resource.
	 */
	public int getAmount(){
		
		return amount;
		
	}
	
	/**
	 * Returns true if the amount of the resource is zero.
	 * 
	 * @return true if the amount of the resource is zero
	 */
	@Override
	public boolean isEmpty() {
		
		return amount == 0;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (amount != other.amount)
			return false;
		return true;
	}
	
}
