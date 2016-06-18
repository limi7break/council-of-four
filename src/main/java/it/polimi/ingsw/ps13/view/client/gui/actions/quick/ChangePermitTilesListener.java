package it.polimi.ingsw.ps13.view.client.gui.actions.quick;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.ChangePermitTilesRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPermitTile;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIRegion;

/**
 * This listener is added to the GUI action button for performing ChangePermitTilesAction.
 *
 */
public class ChangePermitTilesListener extends GUIListener {

	private String region;
	
	private final Collection<GUIRegion> regions;
	
	/**
	 * Creates a new ChangePermitTilesListener.
	 * 
	 * @param regions every GUI region
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public ChangePermitTilesListener(Collection<GUIRegion> regions, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.regions = regions;
		
	}

	/**
	 * Adds appropriate MouseListeners to every clickable GUI object involved in the action,
	 * which collect user input and store it in the class' state, and modifies the behavior
	 * of the confirm button to send the correct request message to the server.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		region = null;
		
		form.appendInfo("Please select a visible tiles area.");
		
		for (GUIRegion reg : regions) {
			for (GUIPermitTile ti : reg.getVisibleTiles()) {
				ti.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						region = reg.getName();
						form.appendInfo("Selected region: " + region);
					}
					
				});
			}
		}
		
		confirmButton.addActionListener(this::confirmAction);

	}
	
	/**
	 * Correct behavior of the confirm button for this action. 
	 * 
	 */
	private void confirmAction(ActionEvent ae) {
		
		connection.sendMessage(new ChangePermitTilesRequestMsg(region));
		
	}

}
