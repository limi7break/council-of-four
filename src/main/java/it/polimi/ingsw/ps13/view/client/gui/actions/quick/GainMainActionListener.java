package it.polimi.ingsw.ps13.view.client.gui.actions.quick;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.GainMainActionRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;

public class GainMainActionListener extends GUIListener {

	public GainMainActionListener(GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("[LISTENER] Sending message " + GainMainActionListener.this.getClass().getSimpleName());
		connection.sendMessage(new GainMainActionRequestMsg());
		System.out.println("[LISTENER] Sent message " + GainMainActionListener.this.getClass().getSimpleName());

	}

}
