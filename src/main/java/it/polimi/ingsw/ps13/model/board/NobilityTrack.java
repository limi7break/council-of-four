package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import it.polimi.ingsw.ps13.model.bonus.Bonus;

/**
 * 
 *
 */
public class NobilityTrack implements Serializable {

    private static final long serialVersionUID = 0L;
    private final Map<Integer, Bonus> bonusMap;
    
    /**
     * 
     * @param bonuses
     */
    public NobilityTrack (Map<Integer, Bonus> bonuses) {
    	
        bonusMap = new TreeMap<>();
        bonusMap.putAll(bonuses);
        
    }
    
    /**
     * 
     * @return
     */
    public Map<Integer, Bonus> getBonusMap() {
    	
        return Collections.unmodifiableMap(bonusMap);
        
    }

    /**
     * 
     * @param position
     * @return
     */
    public Bonus getBonus(int position) {
    	
    	return bonusMap.get(position);
    	
    }
    
    /*
     * Useful for debug.
     * 
     */
    @Override
    public String toString() {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("[NobilityTrack]\n\n");
    	
    	for (Map.Entry<Integer, Bonus> entry : bonusMap.entrySet()) {
    		sb.append("Position " + entry.getKey() + "\n");
    		sb.append(entry.getValue().toString()).append("\n");
    	}
    	
    	return sb.toString();
    	
    }
}
