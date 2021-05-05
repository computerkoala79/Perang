/*
 * Created by Jerry Klos
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardPanel extends JFrame {
	
	
	private BufferedImage background;
	
	// build the game board
	
	public BoardPanel() {
		super();
		
		setSize(1280,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		try {
			background = ImageIO.read(this.getClass().getResource("perangBoard.png"));
		} catch (IOException ex) {
			System.out.println("Could not find the image file " + ex.toString());
		}
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawEmptySlots(g);
	}
	
	private void drawEmptySlots(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, 1280, 720, this);
		g2.setColor(Color.WHITE);
		g2.drawRect(285,36,GameData.CARD_WIDTH,GameData.CARD_HEIGHT);
		g2.drawRect(550,36,GameData.CARD_WIDTH,GameData.CARD_HEIGHT);
		g2.drawRect(815,36,GameData.CARD_WIDTH,GameData.CARD_HEIGHT);
		g2.drawRect(285,432,GameData.CARD_WIDTH,GameData.CARD_HEIGHT);
		g2.drawRect(550,432,GameData.CARD_WIDTH,GameData.CARD_HEIGHT);
		g2.drawRect(815,432,GameData.CARD_WIDTH,GameData.CARD_HEIGHT);
		
	}
	
	private void drawCardText(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoardPanel bp = new BoardPanel();
		bp.setTitle("rectangle testing");
		bp.setVisible(true);
	}
}