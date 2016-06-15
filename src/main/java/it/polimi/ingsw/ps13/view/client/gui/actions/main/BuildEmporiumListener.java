package it.polimi.ingsw.ps13.view.client.gui.actions.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.BuildEmporiumRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICity;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPermitTile;

public class BuildEmporiumListener extends GUIListener {

	private int tile = -1;
	private String city;
	
	private final Collection<GUIPermitTile> tiles;
	private final Collection<GUICity> cities;
	
	public BuildEmporiumListener(Collection<GUIPermitTile> tiles, Collection<GUICity> cities, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.tiles = tiles;
		this.cities = cities;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		tile = -1;
		city = null;
		
		form.appendInfo("Please select one of your permit tiles and a city.");
		
		for (GUICity ci : cities) {
			ci.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					city = ci.getName();
					form.appendInfo("Selected city: " + city);
				}
				
			});
		}
		
		for (GUIPermitTile ti : tiles) {
			ti.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					GUIPermitTile t = (GUIPermitTile)arg0.getSource();
					tile = t.getNumber();
					form.appendInfo("Selected tile n\u00b0: " + tile);
				}
				
			});
		}
		
		confirmButton.addActionListener(ae -> {
			connection.sendMessage(new BuildEmporiumRequestMsg(tile, city));
		});

	}

}
