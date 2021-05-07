import java.util.Scanner;

/*
 * Created by Jerry Klos
 */
public class UserInterface {
	
	public String getPlayerName() {
		Scanner input = new Scanner(System.in);
		System.out.println("-- Please enter your name --");
		String playername = input.nextLine();
		System.out.println("** " + playername + " , welcome to the battle! **");
		return playername;
	}
	
	public void placePlayerCards(Player player, Board board) {
		int[] choices = chooseCardsFromHand(player);
		Card left = player.getHand().getCard(choices[GameData.LEFT]);
		Card center = player.getHand().getCard(choices[GameData.CENTER]);
		Card right = player.getHand().getCard(choices[GameData.RIGHT]);
		board.placeCard(player, GameData.LEFT, left);
		board.placeCard(player, GameData.CENTER, center);
		board.placeCard(player, GameData.RIGHT, right);
		
	}
	
	private int[] chooseCardsFromHand(Player player) {
		Hand hand = player.getHand();
		System.out.println("******\n");
		System.out.println("* Choose 3 cards to place on the board. *\n"
							+ "-------\n"
							+ hand.printPlayerHand() + "\n"
							+ "-- Choose a card for the left slot --\n"
							+ "-- Enter a card id from your hand  ");
		Scanner input = new Scanner(System.in);
		int[] choices = new int[GameData.NUM_SLOTS];
		choices[GameData.LEFT] = input.nextInt();
		
		System.out.println("-- Choose a card for the center slot --\n" 
							+ "-- Enter a card id from your hand  ");
		
		choices[GameData.CENTER] = input.nextInt();
		
		System.out.println("-- Choose a card for the right slot --\n" 
							+ "-- Enter a card id from your hand  ");
		
		choices[GameData.RIGHT] = input.nextInt();
		
		return choices;
		
	}
	
	public void setAIcards(Player ai, Board board) {
		
		Hand hand = ai.getHand();
		
		int[] cardIDs = hand.getCardIDs();
		
		for(int i = 0; i < GameData.NUM_SLOTS; i++) {
			board.placeCard(ai, i, hand.getCard(cardIDs[i]));
		}
		
		flipCard(ai.getPlayerSide().getLeft());
		flipCard(ai.getPlayerSide().getCenter());
		flipCard(ai.getPlayerSide().getRight());
		
	}
	
	public void flipCard(CardSlot slot) {
		if(slot.isFaceUp()) {
			slot.setFaceUp(false);
		} else {
			slot.setFaceUp(true);
		}
	}
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}