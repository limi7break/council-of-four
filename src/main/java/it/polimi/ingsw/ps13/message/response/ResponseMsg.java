package it.polimi.ingsw.ps13.message.response;

import java.io.Serializable;

public abstract class ResponseMsg implements Serializable {
	
	public static final long serialVersionUID = 0L;
	
	private final String message;
	
	public ResponseMsg(String message) {
		
		this.message = message;
		
	}
	
	public String getMessage() {
		
		return message;
		
	}
	
}
