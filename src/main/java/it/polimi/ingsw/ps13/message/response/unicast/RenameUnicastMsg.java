package it.polimi.ingsw.ps13.message.response.unicast;

public class RenameUnicastMsg extends UnicastMsg {

	private static final long serialVersionUID = 0L;
	
	private final String newName;

	public RenameUnicastMsg(String message, String oldName, String newName) {
		
		super(message, oldName);
		this.newName = newName;
		
	}
	
	public String getNewName() {
		
		return newName;
		
	}

}
