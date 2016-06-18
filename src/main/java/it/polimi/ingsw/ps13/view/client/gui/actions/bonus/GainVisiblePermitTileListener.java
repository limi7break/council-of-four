package it.polimi.ingsw.ps13.view.client.gui.actions.bonus;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps13.message.request.action.VisiblePermitTileRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPermitTile;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIRegion;

/**
 * This listener is added to the GUI action button for performing GainVisiblePermitTileAction.
 *
 */
public class GainVisiblePermitTileListener extends GUIListener {

	private String region;
	private int tile = -1;
	
	private final List<GUIPermitTile> tiles;
	
	/**
	 * Creates a new GainVisiblePermitTileListener.
	 * 
	 * @param regions every GUI region
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public GainVisiblePermitTileListener(Collection<GUIRegion> regions, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		tiles = new ArrayList<>();
		
		for (GUIRegion r : regions) {
			tiles.addAll(r.getVisibleTiles());
		}
		
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
		tile = -1;
		
		form.appendInfo("Please select a visible permit tile.");
		
		for (GUIPermitTile ti : tiles) {
			ti.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					GUIRegion r = (GUIRegion) SwingUtilities.getAncestorOfClass(GUIRegion.class, ti);
					region = r.getName();
					tile = ti.getNumber();
					form.appendInfo("Selected region: " + region);
					form.appendInfo("Selected tile n\u00b0: " + tile);
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
		
		connection.sendMessage(new VisiblePermitTileRequestMsg(region, tile));
		
	}
	
}
