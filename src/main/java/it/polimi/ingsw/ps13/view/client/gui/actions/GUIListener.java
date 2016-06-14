package it.polimi.ingsw.ps13.view.client.gui.actions;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;

public abstract class GUIListener implements ActionListener {

	protected final GUIForm form;
	protected final ClientConnection connection;
	protected final JButton confirmButton;
	
	public GUIListener(GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		this.form = form;
		this.connection = connection;
		this.confirmButton = confirmButton;
		
	}
	
}
