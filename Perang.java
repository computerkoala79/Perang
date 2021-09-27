
/*
 * Created by Jerry Klos
 * 
 * This is the main class which is initiated at game start.
 */
public class Perang {
	
	/* create necessary objects, a Full Deck to draw cards from, a board to play on,
	 * one human player and one A.I. player, and a user interface
	*/
	protected FullDeck fulldeck;
	protected Board board;
	protected Player player;
	protected Player ai;
	protected UserInterface ui;
	
	/**
	 * Perang
	 * Game Constructor with instruction selection options
	 */
	public Perang() {
		ui = new UserInterface();
		ui.printInstructions();
	}
	
	/**
	 * Setup Method 
	 * calls the getInstance method on the Full Deck to ensure future calls to it use the same deck,
	 * gets player names, builds the board and has players set cards in play, then prints the game ready board
	 */
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
	
	/**
	 * Is Game Over? 
	 * Method verifies the game over status of the board
	 * @return Returns true if the game is over via a win or tie.
	 */
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
		
		// this could also be done with the UserInterface Game State function, 
		// however the efficiency of the player side functions, while minimal, is still better
	}
	
	/**
	 * Is Tie Game
	 * Method checks if a tie has occurred.
	 * @return Returns true if a tie has occurred.
	 */
	protected boolean isTieGame() {
		return (player.getPlayerSide().oneCardRemainingOnRight() && ai.getPlayerSide().oneCardRemainingOnLeft()) ||
				(player.getPlayerSide().oneCardRemainingOnLeft() && ai.getPlayerSide().oneCardRemainingOnRight());
	}
	
	/**
	 * Battle
	 * Method randomly selects a player to start the game and handles turn taking for the game.
	 */
	protected void battle() {
		// who goes first
		int turn = board.coinFlip();
		
		// print who goes first
		if(turn == GameData.PLAYER_ID) {
			System.out.println("\n*** " + player.getName() + ", will go first.***");
		} else {
			System.out.println("\n*** " + ai.getName() + ", will go first.***");
		}
		
		// while the game is not over, players take turns
		do {
			if(turn == GameData.PLAYER_ID) {
//				ui.cardBattle(player,ai,board);
				ui.battlePhase(player, ai, board);
				turn = GameData.AI_ID;
			} else {
//				ui.cardBattle(ai,player,board);
				ui.battlePhase(ai, player, board);
				turn = GameData.PLAYER_ID;
			}
			System.out.println(board.printBoard(player,ai));
		} while(!isGameOver());
	}
	
	/**
	 * Print Start Screen
	 * Method prints out the game banner.
	 * @return Returns a formated string.
	 */
	private static String printStartScreen() {
		return "****************************\n\n"
			 + "********** Perang **********\n\n"
			 + "***** Three Card Battle ****\n\n"
			 + "****************************\n\n";
	}
	
	public static void main(String[] args) {
		System.out.println(printStartScreen());
		Perang perang = new Perang();
		perang.setup();
		perang.battle();
				
	}
}