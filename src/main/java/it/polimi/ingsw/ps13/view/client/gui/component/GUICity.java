package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps13.model.bonus.ConcreteBonus;
import it.polimi.ingsw.ps13.model.region.City;

public class GUICity extends JPanel {

	private static final long serialVersionUID = 0L;
	private static final Logger LOG = Logger.getLogger(GUICity.class.getSimpleName());

	public GUICity(City city) {
		
		super(new BorderLayout());
		
		JPanel bonusPane = new JPanel(new GridLayout(0, 2));
		GUIBonusFactory.createBonus((ConcreteBonus)city.getBonus(), bonusPane);
		add(bonusPane, BorderLayout.WEST);
		
		BufferedImage cityImage = null;
		try {
			cityImage = ImageIO.read(getClass().getResourceAsStream("/it/polimi/ingsw/ps13/resource/image/city.png"));
		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while loading the city image file.", e);
		}
		BufferedImage coloredCityImage = colorize(cityImage, city.getColor(), 64);
		
		add(new JLabel(city.getName()), BorderLayout.NORTH);
		add(new JLabel(new ImageIcon(coloredCityImage)), BorderLayout.CENTER);
		add(new JLabel("Emporiums: " + city.getNumberOfEmporiums()), BorderLayout.SOUTH);
		setPreferredSize(new Dimension(150, 150));
		
	}
	
	/**
	 * This method is used to automatically colorize the default city image (which is grayscale)
	 * using the real color of the city.
	 * 
	 * @param image
	 * @param color
	 * @return
	 */
	protected static BufferedImage colorize(BufferedImage image, Color color, int alpha) {
		Color withAlpha = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage colorized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = colorized.createGraphics();
        g.drawImage(image, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(withAlpha);
        g.fillRect(0,0,w,h);
        g.dispose();
        return colorized;
    }

}
