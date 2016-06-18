package it.polimi.ingsw.ps13.view.client.gui.actions.bonus;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.RegainRewardTokenRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICity;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;

/**
 * This listener is added to the GUI action button for performing RegainRewardTokenAction.
 *
 */
public class RegainRewardTokenListener extends GUIListener {

	private String city;
	
	private final Collection<GUICity> cities;
	
	/**
	 * Creates a new RegainRewardTokenListener.
	 * 
	 * @param cities every GUI city
	 * @param form the input form used to display useful info for the player
	 * @param connection the client connection used to communicate with the server
	 * @param confirmButton the button used to confirm the action
	 */
	public RegainRewardTokenListener(Collection<GUICity> cities, GUIForm form, ClientConnection connection, JButton confirmButton) {

		super(form, connection, confirmButton);
		
		this.cities = cities;
		
	}

	/**
	 * Adds appropriate MouseListeners to every clickable GUI object involved in the action,
	 * which collect user input and store it in the class' state, and modifies the behavior
	 * of the confirm button to send the correct request message to the server.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		city = null;
		
		form.appendInfo("Please select a city where there is an emporium of yours.");
		
		for (GUICity ci : cities) {
			ci.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					city = ci.getName();
					form.appendInfo("Selected city: " + city);
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
		
		connection.sendMessage(new RegainRewardTokenRequestMsg(city));
		
	}

}
