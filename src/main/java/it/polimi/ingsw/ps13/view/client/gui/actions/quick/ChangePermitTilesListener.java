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

public class ChangePermitTilesListener extends GUIListener {

	private String region;
	
	private final Collection<GUIRegion> regions;
	
	public ChangePermitTilesListener(Collection<GUIRegion> regions, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.regions = regions;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		region = null;
		
		form.append("[INFO] Change Permit Tiles selected. Please choose tiles");
		
		for (GUIRegion reg : regions) {
			for (GUIPermitTile ti : reg.getVisibleTiles()) {
				ti.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						region = reg.getName();
						form.append("[INFO] Selected region: " + region);
					}
					
				});
			}
		}
		
		confirmButton.addActionListener(ae -> {
			
			connection.sendMessage(new ChangePermitTilesRequestMsg(region));
			
		});

	}

}
