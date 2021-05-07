/*
 * Created by Jerry Klos
 */
public class Perang {
	
	// build the game board
	
	protected FullDeck fulldeck;
	protected Board board;
	protected Player[] players = new Player[GameData.NUM_PLAYERS];
	protected UserInterface ui;
	
	public Perang() {
		ui = new UserInterface();
	}
	
	public Board getBoard() { return board; }
	
	public boolean isGameOver() {
		// code to determine if the game is over
		
		return false;
	}
	
	protected void setup() {
		// code for the initial setup
		
		// create a deck from the full list of cards
		fulldeck = FullDeck.getInstance();
		
		// create two players
		String playername = ui.getPlayerName();
		String ainame = "A.I.";
		
		players[GameData.PLAYER_ID] = new Player(GameData.PLAYER_ID,playername);
		players[GameData.AI_ID] = new Player(GameData.AI_ID,ainame);
		
		// create the board
		board = new Board(players);
		
		// set player cards on the board
		ui.placePlayerCards(players[GameData.PLAYER_ID], board);
		
		ui.setAIcards(players[GameData.AI_ID], board);
		
		System.out.println(board.printBoard());
		
	}
	
	private static String printStartScreen() {
		return "****************************\n\n"
			 + "********** Perang **********\n\n"
			 + "******* Card Battle ********\n\n"
			 + "****************************\n\n";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(printStartScreen());
		Perang perang = new Perang();
		perang.setup();
		
	}
}