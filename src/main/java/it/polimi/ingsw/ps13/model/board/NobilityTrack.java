package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

/**
 * 
 * @author alessiomongelluzzo
 *
 */
public class NobilityTrack implements Serializable {

    private Map<Integer, Bonus> bonusList;

    private static final long serialVersionUID = 0L;
    
    /**
     * 
     * @param bonuses
     */
    public NobilityTrack (Map<Integer, Bonus> bonuses) {
    	
        bonusList = new HashMap<>();
        bonusList.putAll(bonuses);
        
    }
    
    /**
     * 
     * @return
     */
    public Map<Integer, Bonus> getBonusList() {
    	
        return bonusList;
        
    }


}
