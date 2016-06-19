package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.bonus.BonusFactory;

/**
 * This class represents the nobility track as a map that associates
 * an integer (the position) to a bonus.
 *
 */
public class NobilityTrack implements Serializable {

    private static final long serialVersionUID = 0L;
    private final Map<Integer, Bonus> bonusMap;
    
    /**
     * Creates a new Nobility Track containing the passed map of bonuses
     * 
     * @param bonuses the map of bonuses
     */
    public NobilityTrack (Map<Integer, Bonus> bonuses) {
    	
        bonusMap = new TreeMap<>();
        bonusMap.putAll(bonuses);
        
    }
    
    /**
     * Returns an unmodifiable map view of the nobility track bonuses.
     * 
     * @returnan unmodifiable map view of the nobility track bonuses
     */
    public Map<Integer, Bonus> getBonusMap() {
    	
        return Collections.unmodifiableMap(bonusMap);
        
    }

    /**
     * Returns the nobility track bonus at the specified position.
     * If the position is not valid (out of bounds), returns an empty bonus.
     * 
     * @param position the position of the nobility track
     * @return the bonus at the specified position, null if the position is not valid 
     */
    public Bonus getBonus(int position) {
    	
    	if (bonusMap.containsKey(position))
    		return bonusMap.get(position);
    	else 
    		return BonusFactory.createEmptyBonus();
    	
    }
    
    /**
     * Used for Command Line Interface (CLI).
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
