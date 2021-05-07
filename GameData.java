import java.awt.Color;
import java.awt.Font;

/*
 * Created by Jerry Klos
 */
public class GameData {
	public static final boolean FACEUP = true;
	public static final int AI_ID = 1;
	public static final int PLAYER_ID = 0;
	public static final int EMPTY_SLOT = -10;
	public static final int FACE_DOWN = -20;
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	public static final int PLAYER_STARTER_DECK_SIZE = 7;
	public static final int FULL_DECK_SIZE = 16;
	public static final int HAND_SIZE = 4;
	public static final int BOARD_WIDTH = 1280;
	public static final int BOARD_HEIGHT = 720;
	public static final int CARD_WIDTH = 180;
	public static final int CARD_HEIGHT = 252;
	public static final int NUM_PLAYERS = 2;
	public static final int NUM_SLOTS = 3;
	public static final Color CARD_TEXT = new Color(13,2,8); // black
	public static final Color MSG_PANEL_TEXT = new Color(0,100,15); // greenish
	public static final Color INPUT_COLOR = new Color(0,212,22); // light greenish
	public static final Color ERROR_COLOR = new Color(225,5,5); // red
	public static final Color CMD_PANEL_BG_COLOR = new Color(10,2,5);
	public static final Color CMD_PANEL_FG_COLOR = new Color(12,5,111);
	public static final Font VERDANA = new Font("Verdana",Font.BOLD,12);
	public static final String[][] CARD_NAMES = {
			{"Jerry","Creator"},
			{"Liakada","Unknown"},
			{"Sebastian","Meenun"},
			{"Draven","Unknown"},
			{"Terislee","Unknown"},
			{"The Unknowing","Unknown"},
			{"Temil","Tembian"},
			{"Echo","Talonian"},
			{"Leila","Talonian"},
			{"Jarrett","Descendant"},
			{"FantaC","Siren"},
			{"Pheonix","Eternal"},
			{"Everscott","Unknown"},
			{"Lalen","Ancient"},
			{"Our Mother","Eternal"},
			{"Our Fathers","Eternal"},
			};
	public static final int[][] ATTACK_STATS = 
		{
			{5,9,9}, // Jerry
			{6,5,4}, // Liakada
			{3,4,5}, // Sebastian
			{2,9,4}, // Draven
			{3,2,9}, // Terislee
			{1,9,9}, // The Unknowing
			{7,7,4}, // Temil
			{2,2,8}, // Echo
			{6,4,5}, // Leila
			{1,1,6}, // Jarrett
			{8,2,4}, // FantaC
			{8,8,8}, // Pheonix
			{6,3,4}, // Everscott
			{1,7,7}, // Lalen
			{9,3,9}, // Our Mother
			{9,3,9}, // Our Fathers
			};
	public static final int[][] DEFENSE_STATS = 
		{
			{9,6,9}, // Jerry
			{6,5,4}, // Liakada
			{3,4,5}, // Sebastian
			{8,5,4}, // Draven
			{6,3,7}, // Terislee
			{9,2,4}, // The Unknowing
			{2,2,8}, // Temil
			{5,6,4}, // Echo
			{2,7,6}, // Leila
			{8,2,6}, // Jarrett
			{4,7,5}, // FantaC
			{9,9,1}, // Pheonix
			{3,1,9}, // Everscott
			{7,7,2}, // Lalen
			{9,7,9}, // Our Mother
			{9,9,7}, // Our Fathers
			};
	
	
	
}
