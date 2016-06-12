package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class GUIPanel extends JPanel {

	private static final long serialVersionUID = 0L;
	
	public GUIPanel() {
		super();
	}

	public GUIPanel(boolean arg0) {
		super(arg0);
	}

	public GUIPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	public GUIPanel(LayoutManager arg0) {
		super(arg0);
	}

	/**
	 * Sets a transparent background to this panel, letting you see the components underneath.
	 * Useful when stacking more than one panel on top of each other. 
	 * 
	 * @param isTransparent
	 */
	public void setTransparent(boolean isTransparent) {
		
		if (isTransparent) {
			setOpaque(false);
			setBackground(new Color(0,0,0,0));
		} else {
			setOpaque(true);
			setBackground(UIManager.getColor("Panel.background"));
		}
		
	}
	
	/**
	 * This method is used to automatically colorize an image with the passed color and
	 * the selected level of transparency.
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
