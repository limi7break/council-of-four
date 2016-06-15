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

public class ElectCouncillorListener extends GUIListener {

	private String region;
	private String councillor;
	
	private final Collection<GUIRegion> regions;
	private final Collection<GUICouncillor> councillors;
	private final GUICouncillorBalcony kingBalcony;
	
	public ElectCouncillorListener(Collection<GUIRegion> regions, Collection<GUICouncillor> councillors, GUICouncillorBalcony kingBalcony, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.regions = regions;
		this.councillors = councillors;
		this.kingBalcony = kingBalcony;
		
	}

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
		
		confirmButton.addActionListener(ae -> {
			connection.sendMessage(new ElectCouncillorRequestMsg(region, councillor));
		});

	}

}