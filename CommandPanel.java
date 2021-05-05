import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CommandPanel extends JPanel implements ActionListener, DocumentListener{
	
	// Keeps track of what type of input is currently polling for
	private enum Mode {OFF, TEXT, CARD, NUMBER, CONFIRMATION};
	private Mode mode;
	
	private final JTextField commandField;
	private final JLabel commandLabel;
	
	private final LinkedList<String> commandBuffer = new LinkedList<>();
	
	public CommandPanel(int width,int height) {
		super();
		
		setPreferredSize(new Dimension(width, height));
		
		this.setLayout(new BorderLayout());
		this.setBackground(GameData.CMD_PANEL_BG_COLOR);
		
		commandLabel = new JLabel("Terminal:");
		commandLabel.setForeground(GameData.CMD_PANEL_FG_COLOR);
		
		commandField = new JTextField(50);
		commandField.setBackground(GameData.CMD_PANEL_BG_COLOR);
		commandField.setForeground(GameData.CMD_PANEL_FG_COLOR);
		commandField.setFont(GameData.VERDANA);
		
		commandField.addActionListener(this);
		commandField.getDocument().addDocumentListener(this);
		commandField.setCaretColor(GameData.INPUT_COLOR);
		
		disableTerminal();

		
	}
	
	private String getInput(Mode inputMode) {
		enableTerminal();
		
		mode = inputMode;
		
		String command;
		synchronized (commandBuffer) {
			while(commandBuffer.isEmpty()) {
				try {
					commandBuffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		command = commandBuffer.pop();
		disableTerminal();
		return command;
	}
	
	public String getUserInput() {
		return getInput(Mode.TEXT);
	}
	
	private void disableTerminal() {
		mode = Mode.OFF;
		commandField.setText("");
		commandField.setEditable(false);
		commandField.setFocusable(false);
	}
	
	private void enableTerminal()
	{
		commandField.setEditable(true);
		commandField.setFocusable(true);
		commandField.grabFocus();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}