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

public class RegainRewardTokenListener extends GUIListener {

	private String city;
	
	private final Collection<GUICity> cities;
	
	public RegainRewardTokenListener(Collection<GUICity> cities, GUIForm form, ClientConnection connection, JButton confirmButton) {

		super(form, connection, confirmButton);
		
		this.cities = cities;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		city = null;
		
		form.append("[INFO] Regain Reward Token selected. Please choose city");
		
		for (GUICity ci : cities) {
			ci.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					city = ci.getName();
					form.append("[INFO] Selected city: " + city);
				}
				
			});
		}
		
		confirmButton.addActionListener(ae -> {
			connection.sendMessage(new RegainRewardTokenRequestMsg(city));
		});

	}

}
