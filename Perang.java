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
		fulldeck = FullDeck.getInstance();
		String pOne = ui.getPlayerName();
		String pTwo = "A.I.";
		players[0] = new Player(0,pOne);
		players[1] = new Player(1,pTwo);
		board = new Board(players);
	}
	
	private static String printStartScreen() {
		return "****************************\n\n"
			 + "********** Perang **********\n\n"
			 + "****************************\n\n";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(printStartScreen());
		Perang perang = new Perang();
		perang.setup();
		
	}
}