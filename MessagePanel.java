
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.util.ArrayList;



public class MessagePanel extends JPanel{
	
	private final JEditorPane message;
	private final String header;
	private ArrayList<String> mainContent;
	private String currentLine;
	private final String footer;
	private final int MAX_LINES = 40;
	private final int MSG_PANEL_WIDTH = 300;
	private final int MSG_PANEL_HEIGHT = 300;
	private final Dimension MSG_PANEL_DIMENSION = new Dimension(MSG_PANEL_WIDTH,MSG_PANEL_HEIGHT);
	private final Dimension MSG_PANEL_EDITOR_DIMENSION = new Dimension(MSG_PANEL_WIDTH-10,MSG_PANEL_HEIGHT);
	
	Color inputTextColor;
	Color errorTextColor;
	
	public MessagePanel() {
		
		setPreferredSize(MSG_PANEL_DIMENSION);
		setLayout(new BorderLayout());
		
		message = new JEditorPane();
		message.setEditable(false);
		message.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		message.setPreferredSize(MSG_PANEL_EDITOR_DIMENSION);
		message.setContentType("text/html");
		HTMLEditorKit kit = new HTMLEditorKit();
		
		StyleSheet stylesheet = kit.getStyleSheet();
		Color defaultColor = GameData.MSG_PANEL_TEXT;
		Color background = GameData.CARD_TEXT;
		inputTextColor = GameData.INPUT_COLOR;
		errorTextColor = GameData.ERROR_COLOR;
		int defaultFontSize = 12;
		String defaultFontType = "Verdana";
		
		// Sets defaults on body
        stylesheet.addRule("body {font-family: " + defaultFontType + "; font-size: " + defaultFontSize + "; color: " + colorToRGBString(defaultColor) +
                "; background: " + colorToRGBString(background) + ";}");
        // Removes padding from paragraphs
        stylesheet.addRule("p {padding: 0; margin: 0;");

        // Setup header
        header = "<html><body><p>";
        // Setup footer (padding div at the bottom stops message panel from pushing up against command)
        footer = "</p><div style=\"padding-top: 15px\"></div></body></html>";
        // Setup content
        mainContent = new ArrayList<>();
        currentLine = "";
        
        JScrollPane scrollV = new JScrollPane(message);
        scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollV.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        add(scrollV, BorderLayout.CENTER);
        
		
	}
	
	 /**
     * Converts a java Color object to a string in the format "rgb(XXX,XXX,XXX)" which can be used in CSS
     *
     * @param color to convert
     * @return rgb string
     */
    private static String colorToRGBString(Color color)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("rgb(");
        sb.append(color.getRed());
        sb.append(",");
        sb.append(color.getGreen());
        sb.append(",");
        sb.append(color.getBlue());
        sb.append(")");
        return sb.toString();
    }
	
}
