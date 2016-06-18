package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import it.polimi.ingsw.ps13.model.deck.PoliticsCard;

/**
 * This is the GUI representation of a politics card.
 *
 */
public class GUIPoliticsCard extends JLabel {

	private static final Logger LOG = Logger.getLogger(GUIPoliticsCard.class.getSimpleName());
	
	private static final long serialVersionUID = 0L;
	private static final BufferedImage cardImage = loadPoliticsCardImage();
	private static final BufferedImage jollyImage = loadJollyImage();
	private static final BufferedImage tick = loadTickImage();
	
	private final String colorName;
	private boolean selected;
	
	/**
	 * Creates the GUI representation of the politics card passed as parameter.
	 * 
	 * @param card the politics card from which the GUI representation is created
	 */
	protected GUIPoliticsCard(PoliticsCard card) {
		
		super();
		selected = false;
		
		if (card.isMultiColored()) {
			setIcon(new ImageIcon(jollyImage));
		} else {
			setIcon(new ImageIcon(GUIPanel.colorize(cardImage, card.getColor(), 96)));
		}
		setToolTipText(card.getColorName());
		
		this.colorName = card.getColorName();
		
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
	 * 
	 * @return true if the card is selected
	 */
	public boolean isSelected() {
		
		return selected;
		
	}
	
	/**
	 * Sets the selected status of this card to the passed boolean value 
	 * 
	 * @param isSelected the new selected status of the card
	 */
	public void setSelected(boolean isSelected) {
		
		selected = isSelected;
		revalidate();
		repaint();
		
	}
	
	/**
	 * Draws the tick image over the card, if the card is selected.
	 * 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		if (selected) {
			g.drawImage(tick, (getWidth()-tick.getWidth())/2, (getHeight()-tick.getHeight())/2, this);
		}
		
	}
	
	/**
	 * Loads the politics card image.
	 * 
	 * @return the politics card image
	 */
	private static BufferedImage loadPoliticsCardImage() {
		
		try {
			return ImageIO.read(GUIPoliticsCard.class.getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/politicscard.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading politics card image file.", e);
		}
		
		return null;
		
	}
	
	/**
	 * Loads the jolly politics card image.
	 * 
	 * @return the jolly politics card image
	 */
	private static BufferedImage loadJollyImage() {

		try {
			return ImageIO.read(GUIPoliticsCard.class.getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/jolly.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading jolly image file.", e);
		}
		
		return null;
		
	}
	
	/**
	 * Loads the tick image.
	 * 
	 * @return the tick image
	 */
	private static BufferedImage loadTickImage() {
		
		try {
			return ImageIO.read(GUIPoliticsCard.class.getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/tick.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading tick image file.", e);
		}
		
		return null;
		
	}
	
}
