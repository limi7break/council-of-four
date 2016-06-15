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

public class RegainPermitTileBonusListener extends GUIListener {

	private int tile = -1;
	
	private final List<GUIPermitTile> tiles;
	
	public RegainPermitTileBonusListener(List<GUIPermitTile> tiles, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.tiles = tiles;
		
	}
	
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
		
		confirmButton.addActionListener(ae -> {
			connection.sendMessage(new RegainPermitTileBonusRequestMsg(tile));
		});

	}

}
