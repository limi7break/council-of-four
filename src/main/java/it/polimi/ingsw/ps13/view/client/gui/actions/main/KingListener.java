package it.polimi.ingsw.ps13.view.client.gui.actions.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;

import it.polimi.ingsw.ps13.message.request.action.KingActionRequestMsg;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.gui.actions.GUIListener;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICity;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPoliticsCard;

public class KingListener extends GUIListener {

	private String city;
	private List<String> cards;
	
	private final Collection<GUICity> cities;
	private final Collection<GUIPoliticsCard> politicsCards;
	
	public KingListener(Collection<GUICity> cities, Collection<GUIPoliticsCard> politicsCards, GUIForm form, ClientConnection connection, JButton confirmButton) {
		
		super(form, connection, confirmButton);
		
		this.cities = cities;
		this.politicsCards = politicsCards;
		
		cards = new ArrayList<>();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		city = null;
		cards.clear();
		
		form.appendInfo("Please select a city and [1, " + CouncillorBalcony.COUNCILLORS_PER_BALCONY + "] politics cards");
		
		for (GUICity ci : cities) {
			ci.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					city = ci.getName();
					form.appendInfo("Selected city: " + city);
				}
				
			});
		}
		
		for (GUIPoliticsCard c : politicsCards) {
			c.setSelected(false);
			
			c.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					GUIPoliticsCard pol = (GUIPoliticsCard)arg0.getSource();
					if (!pol.isSelected() && cards.size() < CouncillorBalcony.COUNCILLORS_PER_BALCONY) {
						pol.setSelected(true);
						cards.add(pol.getColorName());
					} else if (pol.isSelected()) {
						pol.setSelected(false);
						cards.remove(pol.getColorName());
					}
				}
				
			});
		}
		
		confirmButton.addActionListener(this::confirmAction);

	}
	
	private void confirmAction(ActionEvent ae) {
		
		connection.sendMessage(new KingActionRequestMsg(city, cards));
		
	}

}
