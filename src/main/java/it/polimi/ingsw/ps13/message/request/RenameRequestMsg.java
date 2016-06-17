package it.polimi.ingsw.ps13.message.request;

/**
 * 
 * @author Michele Ferri
 *
 */
public class RenameRequestMsg extends RequestMsg {
	
	private static final long serialVersionUID = 0L;
	
	private final String newName;

	public RenameRequestMsg(String newName) {
		
		this.newName = newName;
		
	}
	
	public String getNewName() {
		
		return newName;
		
	}

}
