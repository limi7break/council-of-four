package it.polimi.ingsw.ps13.view.client.gui.actions.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.ElectCouncillorRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICouncillor;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICouncillorBalcony;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIRegion;

/**
 * This listener is added to the GUI action button for performing ElectCouncillorAction.
 *
 */
public class ElectCouncillorListener extends GUIListener {

	private String region;
	private String councillor;
	
	private final Collection<GUIRegion> regions;
	private final Collection<GUICouncillor> councillors;
	private final GUICouncillorBalcony kingBalcony;
	
	/**
	 * Creates a new ElectCouncillorListener.
	 * 
	 * @param regions every GUI region
	 * @param councillors every GUI councillor that is not in a GUI councillor balcony
	 * @param kingBalcony the king GUI councillor balcony
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public ElectCouncillorListener(Collection<GUIRegion> regions, Collection<GUICouncillor> councillors, GUICouncillorBalcony kingBalcony, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.regions = regions;
		this.councillors = councillors;
		this.kingBalcony = kingBalcony;
		
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
		councillor = null;
		
		form.appendInfo("Please select a balcony and a councillor.");
		
		for (GUIRegion reg : regions) {
			reg.getCouncillorBalcony().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					region = reg.getName();
					form.appendInfo("Selected region: " + region);
				}
				
			});
		}
		
		kingBalcony.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				region = "king";
				form.appendInfo("Selected region: " + region);
			}
			
		});
		
		for (GUICouncillor co : councillors) {
			co.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					councillor = co.getColorName();
					form.appendInfo("Selected councillor: " + councillor);
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
		
		connection.sendMessage(new ElectCouncillorRequestMsg(region, councillor));
		
	}

}
