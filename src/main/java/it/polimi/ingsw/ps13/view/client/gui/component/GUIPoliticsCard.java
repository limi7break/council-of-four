package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.deck.PoliticsCard;

public class GUIPoliticsCard extends JLabel {

private static final Logger LOG = Logger.getLogger(GUIPoliticsCard.class.getSimpleName());
	
	private static final long serialVersionUID = 0L;
	private static final BufferedImage cardImage = loadPoliticsCardImage();
	private static final BufferedImage jollyImage = loadJollyImage();
	
	private final String colorName;
	
	protected GUIPoliticsCard(PoliticsCard card) {
		
		super();
		
		if (card.isMultiColored()) {
			setIcon(new ImageIcon(jollyImage));
		} else {
			setIcon(new ImageIcon(GUIPanel.colorize(cardImage, card.getColor(), 96)));
		}
		setToolTipText(card.getColorName());
		
		this.colorName = card.getColorName();
		
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
	private static BufferedImage loadPoliticsCardImage() {
		
		try {
			return ImageIO.read(GUIPoliticsCard.class.getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/politicscard.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading the politics card image file.", e);
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private static BufferedImage loadJollyImage() {

		try {
			return ImageIO.read(GUIPoliticsCard.class.getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/jolly.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading the jolly image file.", e);
		}
		
		return null;
		
	}
	
}
