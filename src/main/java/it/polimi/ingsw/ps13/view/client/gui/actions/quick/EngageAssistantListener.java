package it.polimi.ingsw.ps13.view.client.gui.actions.quick;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.EngageAssistantRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;

/**
 * This listener is added to the GUI action button for performing EngageAssistantAction.
 *
 */
public class EngageAssistantListener extends GUIListener {

	/**
	 * Creates a new EngageAssistantListener.
	 * 
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public EngageAssistantListener(GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
	}

	/**
	 * Correct behavior of the confirm button for this action. 
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		connection.sendMessage(new EngageAssistantRequestMsg());

	}

}
