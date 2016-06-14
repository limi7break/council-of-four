package it.polimi.ingsw.ps13.view.client.gui.actions.quick;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.QuickElectCouncillorRequestMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICouncillor;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIRegion;

public class QuickElectCouncillorListener extends GUIListener {

	private String region;
	private String councillor;
	
	private final Collection<GUIRegion> regions;
	private final Collection<GUICouncillor> councillors;
	
	public QuickElectCouncillorListener(Collection<GUIRegion> regions, Collection<GUICouncillor> councillors, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.regions = regions;
		this.councillors = councillors;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		region = null;
		councillor = null;
		
		form.append("[INFO] Quick Elect Councillor selected. Please choose balcony and councillor");
		
		for (GUIRegion reg : regions) {
			reg.getCouncillorBalcony().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					region = reg.getName();
					form.append("[INFO] Selected region: " + region);
				}
				
			});
		}
		
		for (GUICouncillor co : councillors) {
			co.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					councillor = co.getColorName();
					form.append("[INFO] Selected councillor: " + councillor);
				}
				
			});
		}

		confirmButton.addActionListener(ae -> {
			
			connection.sendMessage(new QuickElectCouncillorRequestMsg(region, councillor));
			
		});

	}

}
