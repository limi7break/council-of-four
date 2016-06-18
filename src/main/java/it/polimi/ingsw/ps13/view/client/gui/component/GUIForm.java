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

/**
 * This panel contains a text area wrapped in a scroll pane with a smart scroller,
 * and a text field for user input.
 *
 */
public class GUIForm extends GUIPanel {
	
	private static final long serialVersionUID = 0L;

	private final JTextArea textArea;
	private final JTextField textField;
	
	/**
	 * Creates a new GUIForm.
	 * 
	 */
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
	 * Appends the passed string to the text area.
	 * 
	 * @param str the message to append to the text area
	 */
	public void append(String str) {
		
		textArea.append("\n");
		textArea.append(str);
		revalidate();
		repaint();
		
	}
	
	/**
	 * Appends the passed string to the text area as an information message.
	 * 
	 * @param str the information message to append to the text area
	 */
	public void appendInfo(String str) {
		
		textArea.append("\n");
		textArea.append("[INFO] " + str);
		revalidate();
		repaint();
		
	}
	
	/**
	 * Appends the passed string to the text area as a chat message.
	 * 
	 * @param sender the sender of the chat message
	 * @param str the content of the chat message
	 */
	public void appendChat(String sender, String str) {
		
		textArea.append("\n");
		textArea.append("[" + sender + "] " + str);
		revalidate();
		repaint();
		
	}
	
	/**
	 * Returns the input form's text field.
	 * 
	 * @return the input form's text field
	 */
	public JTextField getTextField() {
		
		return textField;
		
	}
	
}
