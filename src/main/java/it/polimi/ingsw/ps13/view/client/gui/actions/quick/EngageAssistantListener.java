package it.polimi.ingsw.ps13.view.client.gui.actions.quick;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.EngageAssistantRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;

public class EngageAssistantListener extends GUIListener {

	public EngageAssistantListener(GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("[LISTENER] Sending message " + EngageAssistantListener.this.getClass().getSimpleName());
		connection.sendMessage(new EngageAssistantRequestMsg());
		System.out.println("[LISTENER] Sent message " + EngageAssistantListener.this.getClass().getSimpleName());

	}

}
