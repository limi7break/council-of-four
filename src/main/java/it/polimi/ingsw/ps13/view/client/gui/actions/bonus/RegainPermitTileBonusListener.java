package it.polimi.ingsw.ps13.view.client.gui.actions.bonus;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.RegainPermitTileBonusRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPermitTile;

/**
 * This listener is added to the GUI action button for performing RegainPermitTileBonusAction.
 *
 */
public class RegainPermitTileBonusListener extends GUIListener {

	private int tile = -1;
	
	private final List<GUIPermitTile> tiles;
	
	/**
	 * Creates a new RegainPermitTileBonusListener.
	 * 
	 * @param tiles every GUI permit tile in the player's hand
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public RegainPermitTileBonusListener(List<GUIPermitTile> tiles, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.tiles = tiles;
		
	}
	
	/**
	 * Adds appropriate MouseListeners to every clickable GUI object involved in the action,
	 * which collect user input and store it in the class' state, and modifies the behavior
	 * of the confirm button to send the correct request message to the server.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		tile = -1;
		
		form.appendInfo("Please select one of your permit tiles.");
		
		for (GUIPermitTile ti : tiles) {
			ti.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					tile = ti.getNumber();
					form.appendInfo("[INFO] Selected tile n\u00b0: " + tile);
				}
				
			});
		}
		
		confirmButton.addActionListener(this::confirmAction);

	}
	
	/**
	 * Correct behavior of the confirm button for this action. 
	 * 
	 */
	private void confirmAction(ActionEvent ae) {
		
		connection.sendMessage(new RegainPermitTileBonusRequestMsg(tile));
		
	}

}
