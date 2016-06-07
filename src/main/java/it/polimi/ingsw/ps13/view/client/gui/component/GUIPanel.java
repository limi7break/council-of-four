package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Color;
import java.awt.LayoutManager;

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
	
}
