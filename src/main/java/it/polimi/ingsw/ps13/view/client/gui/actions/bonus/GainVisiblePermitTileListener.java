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

public class GainVisiblePermitTileListener extends GUIListener {

	private String region;
	private int tile = -1;
	
	private final List<GUIPermitTile> tiles;
	
	public GainVisiblePermitTileListener(Collection<GUIRegion> regions, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		tiles = new ArrayList<>();
		
		for (GUIRegion r : regions) {
			tiles.addAll(r.getVisibleTiles());
		}
		
	}

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

	private void confirmAction(ActionEvent ae) {
		
		connection.sendMessage(new VisiblePermitTileRequestMsg(region, tile));
		
	}
	
}
