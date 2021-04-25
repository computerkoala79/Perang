/*
 * Created by Jerry Klos
 */
public class Perang {
	
	// build the game board
	
	protected FullDeck fulldeck;
	protected Board board;
	protected Player[] players;
	
	public Perang() {}
	
	public Board getBoard() { return board; }
	
	public boolean isGameOver() {
		// code to determine if the game is over
		
		return false;
	}
	
	protected void setup() {
		// code for the initial setup
		fulldeck = FullDeck.getInstance();
		board = new Board();
		String pOne = "Player One";
		String pTwo = "Player Two";
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}