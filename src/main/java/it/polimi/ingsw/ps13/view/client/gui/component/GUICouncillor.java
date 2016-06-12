package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.council.Councillor;

public class GUICouncillor extends JLabel {

	private static final Logger LOG = Logger.getLogger(GUICouncillor.class.getSimpleName());
	
	private static final long serialVersionUID = 0L;
	private static final BufferedImage councillorImage = loadCouncillorImage();
	
	private final String colorName;
	
	protected GUICouncillor(Councillor councillor) {
		
		super(new ImageIcon(GUIPanel.colorize(councillorImage, councillor.getColor(), 255)));
		setToolTipText(councillor.getColorName());
		
		this.colorName = councillor.getColorName();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getColorName() {
		
		return colorName;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private static BufferedImage loadCouncillorImage() {
		
		try {
			return ImageIO.read(GUICouncillor.class.getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/councillor.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading the councillor image file.", e);
		}
		
		return null;
		
	}
	
}
