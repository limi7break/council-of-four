package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

public class BonusTiles implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private final Bonus coastBonus;
	private final Bonus hillBonus;
	private final Bonus mountainBonus;
	private final Bonus kingBonus;
	
	public BonusTiles(Bonus coast, Bonus hill, Bonus mountain, Bonus king){
		
		coastBonus = coast;
		hillBonus = hill;
		mountainBonus = mountain;
		kingBonus = king;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getCoastBonus() {
		
		return coastBonus;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getHillBonus() {
		
		return hillBonus;
		
	}

	/**
	 * 
	 * @return
	 */
	public Bonus getMountainBonus() {
		
		return mountainBonus;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getKingBonus() {
		
		return kingBonus;
		
	}
	
}
