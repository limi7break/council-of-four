package it.polimi.ingsw.ps13.model.resource.special;

import java.io.Serializable;
import it.polimi.ingsw.ps13.model.Player;

/**
 * A special resource represents a non-material item players have.
 * Players have the possibility to gain a special resource
 * through game bonuses.
 *
 */
public abstract class SpecialResource implements Serializable {
	
	public static final long serialVersionUID = 0L;
	
	public abstract void giveTo(Player player);

}
