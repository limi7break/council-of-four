package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.council.Councillor;

/**
 * This is the GUI representation of a councillor.
 *
 */
public class GUICouncillor extends JLabel {

	private static final Logger LOG = Logger.getLogger(GUICouncillor.class.getSimpleName());
	
	private static final long serialVersionUID = 0L;
	private static final BufferedImage councillorImage = loadCouncillorImage();
	
	private final String colorName;
	
	/**
	 * Creates the GUI representation of the councillor passed as parameter.
	 * 
	 * @param councillor the councillor from which the GUI representation is created
	 */
	protected GUICouncillor(Councillor councillor) {
		
		super(new ImageIcon(GUIPanel.colorize(councillorImage, councillor.getColor(), 255)));
		setToolTipText(councillor.getColorName());
		
		this.colorName = councillor.getColorName();
		
	}
	
	/**
	 * Returns the name of this councillor's color.
	 * 
	 * @return the name of this councillor's color
	 */
	public String getColorName() {
		
		return colorName;
		
	}
	
	/**
	 * Loads the councillor image.
	 * 
	 * @return the councillor image
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
