package it.polimi.ingsw.ps13.view.client.gui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import net.miginfocom.swing.MigLayout;

public class GUIForm extends GUIPanel {
	
	private static final long serialVersionUID = 0L;

	private final JTextArea textArea;
	private final JTextField textField;
	
	public GUIForm() {
		
		super(new MigLayout("fill, flowy", "", ""));
		setTransparent(true);
		
		// Create text area
		textArea = new JTextArea("Council of Four version 1.0", 15, 35);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		textArea.setBackground(Color.white);
		textArea.setForeground(Color.black);
		Font font = new Font("Lucida Console", Font.PLAIN, 13);
		textArea.setFont(font);
		
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		// Create text field
		textField = new JTextField("", 35);
		
		// Create scroll pane
		JScrollPane scrollPane = new JScrollPane(textArea);
		@SuppressWarnings("unused")
		SmartScroller ss = new SmartScroller(scrollPane);
		
		add(scrollPane, "grow");
		add(new JLabel("Chat: "), "flowx");
		add(textField, "cell 0 1, grow");
		
	}
	
	/**
	 * 
	 * @param str
	 */
	public void append(String str) {
		
		textArea.append("\n");
		textArea.append(str);
		revalidate();
		repaint();
		
	}
	
	/**
	 * 
	 * @param str
	 */
	public void appendInfo(String str) {
		
		textArea.append("\n");
		textArea.append("[INFO] " + str);
		revalidate();
		repaint();
		
	}
	
	/**
	 * 
	 * @param player
	 * @param str
	 */
	public void appendChat(String player, String str) {
		
		textArea.append("\n");
		textArea.append("[" + player + "] " + str);
		revalidate();
		repaint();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public JTextField getTextField() {
		
		return textField;
		
	}
	
}
