package it.polimi.ingsw.ps13.view.client.gui.actions.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.AcquirePermitTileRequestMsg;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPermitTile;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPoliticsCard;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIRegion;

public class AcquirePermitTileListener extends GUIListener {

	private String region;
	private int tile = -1;
	private List<String> cards;
	
	private final Collection<GUIRegion> regions;
	private final Collection<GUIPoliticsCard> politicsCards;
	
	public AcquirePermitTileListener(Collection<GUIRegion> regions, Collection<GUIPoliticsCard> politicsCards, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.regions = regions;
		this.politicsCards = politicsCards;
		
		cards = new ArrayList<>();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		region = null;
		tile = -1;
		cards.clear();
		
		form.append("[INFO] Acquire Permit Tile selected. Please choose balcony, permit tile, politics cards");
		
		for (GUIRegion reg : regions) {
			reg.getCouncillorBalcony().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					region = reg.getName();
					form.append("[INFO] Selected region: " + region);
				}
				
			});
			
			for (GUIPermitTile ti : reg.getVisibleTiles()) {
				ti.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						GUIPermitTile t = (GUIPermitTile)arg0.getSource();
						tile = t.getNumber();
						form.append("[INFO] Selected tile n\u00b0: " + tile);
					}
			
				});
			}
		}
		
		for (GUIPoliticsCard c : politicsCards) {
			c.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					GUIPoliticsCard pol = (GUIPoliticsCard)arg0.getSource();
					if (cards.size() < CouncillorBalcony.COUNCILLORS_PER_BALCONY) {
						cards.add(pol.getColorName());
						form.append("[INFO] Selected cards: " + cards.toString());
					}
				}
				
			});
		}
		
		confirmButton.addActionListener(ae -> {
			
			connection.sendMessage(new AcquirePermitTileRequestMsg(region, tile, cards));
			
		});

	}

}
