package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;

public class GUICouncillorBalcony extends JPanel {

	private static final long serialVersionUID = 0L;
	private static final Logger LOG = Logger.getLogger(GUICouncillorBalcony.class.getSimpleName());

	public GUICouncillorBalcony(CouncillorBalcony balcony) {
		
		super(new FlowLayout());
		
		BufferedImage councillor = null;
		try {
			councillor = ImageIO.read(getClass().getResource("/it/polimi/ingsw/ps13/resource/image/councillor.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading councillor image files.", e);
		}
		
		for (Councillor c : balcony.getCouncillors()) {
			add(new JLabel(new ImageIcon(GUICity.colorize(councillor, c.getColor(), 255))));
		}
		
	}

}
