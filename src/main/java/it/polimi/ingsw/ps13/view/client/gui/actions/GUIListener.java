package it.polimi.ingsw.ps13.view.client.gui.actions;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;

/**
 * This class represents an ActionListener which is going to be added to an action button.
 * 
 * Its purpose is that of adding appropriate MouseListeners to every clickable GUI object
 * involved in the action, which collect user input and store it in the class' state, and modify
 * the behavior of the confirm button to send the correct request message to the server,
 * with the correct parameters selected by the user.  
 *
 */
public abstract class GUIListener implements ActionListener {

	protected final GUIForm form;
	protected final ClientConnection connection;
	protected final JButton confirmButton;
	
	/**
	 * Creates a GUIListener with the three basic needed objects.
	 * 
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public GUIListener(GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		this.form = form;
		this.connection = connection;
		this.confirmButton = confirmButton;
		
	}
	
}
