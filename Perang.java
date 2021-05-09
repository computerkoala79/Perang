/*
 * Created by Jerry Klos
 */
public class Perang {
	
	// build the game board
	
	protected FullDeck fulldeck;
	protected Board board;
	protected Player player;
	protected Player ai;
	protected UserInterface ui;
	
	public Perang() {
		ui = new UserInterface();
	}
	
	public Board getBoard() { return board; }
	
	protected void setup() {
		// code for the initial setup
		
		// create a deck from the full list of cards
		fulldeck = FullDeck.getInstance();
		
		// create two players
		String playername = ui.getPlayerName();
		String ainame = "A.I.";
		
		player = new Player(GameData.PLAYER_ID,playername);
		ai = new Player(GameData.AI_ID,ainame);
		
		// create the board
		board = new Board();
		
		// set player cards on the board
		ui.placePlayerCards(player, board);
		
		ui.setAIcards(ai, board);
		
		System.out.println(board.printBoard(player,ai));
		
	}
	
	public boolean isGameOver() {
		// code to determine if the game is over
		if(player.getPlayerSide().allSlotsAreEmpty()) {
			System.out.println("------ " + ai.getName() + " wins!!! ------");
			return true;
		} else if(ai.getPlayerSide().allSlotsAreEmpty()) {
			System.out.println("------ " + player.getName() + " wins!!! ------");
			return true;
		} else if(isTieGame()) {
			System.out.println("------ Tie Game -------");
			return true;
		}
		return false;
	}
	
	protected boolean isTieGame() {
		return (player.getPlayerSide().oneCardRemainingOnRight() && ai.getPlayerSide().oneCardRemainingOnLeft()) ||
				(player.getPlayerSide().oneCardRemainingOnLeft() && ai.getPlayerSide().oneCardRemainingOnRight());
	}
	
	protected void battle() {
		// who goes first
		int turn = board.coinFlip();
		
		if(turn == GameData.PLAYER_ID) {
			System.out.println("\n*** " + player.getName() + ", will go first.***");
		} else {
			System.out.println("\n*** " + ai.getName() + ", will go first.***");
		}
		
		do {
			if(turn == GameData.PLAYER_ID) {
				ui.cardBattle(player,ai,board);
				turn = GameData.AI_ID;
			} else {
				ui.cardBattle(ai,player,board);
				turn = GameData.PLAYER_ID;
			}
			System.out.println(board.printBoard(player,ai));
		} while(!isGameOver());
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
		perang.battle();
		
	}
}