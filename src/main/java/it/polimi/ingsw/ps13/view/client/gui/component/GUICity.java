package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps13.model.player.Emporium;
import it.polimi.ingsw.ps13.model.region.City;

/**
 * This is the GUI representation of a city.
 *
 */
public class GUICity extends GUIPanel {

	private static final long serialVersionUID = 0L;
	private static final Logger LOG = Logger.getLogger(GUICity.class.getSimpleName());

	private Point centerPoint;
	private final String name;
	private final JLabel image;
	
	/**
	 * Creates the GUI representation of the city passed as parameter.
	 * 
	 * @param city the city from which the GUI representation is created
	 */
	protected GUICity(City city) {
		
		super(new BorderLayout());
		setTransparent(true);
		
		this.name = city.getName();
		
		GUIPanel bonusPane = new GUIPanel(new GridLayout(0, 2));
		bonusPane.setTransparent(true);
		GUIBonusFactory.createBonus(city.getBonus(), bonusPane);
		add(bonusPane, BorderLayout.WEST);
		
		BufferedImage cityImage = null;
		try {
			cityImage = ImageIO.read(getClass().getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/city.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading the city image file.", e);
		}
		BufferedImage coloredCityImage = colorize(cityImage, city.getColor(), 64);
		
		add(new JLabel(city.getName()), BorderLayout.NORTH);
	
		image = new JLabel(new ImageIcon(coloredCityImage));
		add(image, BorderLayout.CENTER);
		
		GUIPanel emporiumsPanel = new GUIPanel(new FlowLayout());
		emporiumsPanel.setTransparent(true);
		
		BufferedImage emporiumImage = null;
		try {
			emporiumImage = ImageIO.read(getClass().getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/emporium.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading the emporium image file.", e);
		}
		
		for (Emporium emporium : city.getEmporiums()) {
			BufferedImage coloredEmporiumImage = colorize(emporiumImage, emporium.getColor(), 255);
			emporiumsPanel.add(new JLabel(new ImageIcon(coloredEmporiumImage)));
		}
		add(emporiumsPanel, BorderLayout.SOUTH);
		
		setMaximumSize(new Dimension(150, 150));
		
	}
	
	/**
	 * Returns the name of the city.
	 * 
	 * @return the name of the city
	 */
	@Override
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * Returns the center point of the city image.
	 * 
	 * @return the center point of the city image
	 */
	protected Point getCenterPoint() {
		
		return centerPoint;
		
	}
	
	/**
	 * Sets the center point of the city relative to the component passed as parameter.
	 * 
	 * @param component the city center point will be relative to this component
	 */
	protected void setCenterPointRelativeTo(Component component) {
	
		centerPoint = SwingUtilities.convertPoint(image, new Point(image.getWidth()/2, image.getHeight()/2), component);
	
	}
	
	/**
	 * Sets the king to be displayed on the city.
	 * 
	 */
	protected void setKing() {
		
		add(new JLabel("K"), BorderLayout.EAST);
		
	}
	
}
