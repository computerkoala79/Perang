import java.util.ArrayList;

/*
 * Created by Jerry Klos
 */
public class Hand {
	
	private ArrayList<Card> cards;
	private FullDeck deck;
	
	public Hand() {
		cards = new ArrayList<>();
		buildHand(deck);
	}
	
	private void buildHand(FullDeck deck) {
		for(int i = 0; i < GameData.HAND_SIZE; i++)
			cards.add(deck.drawCard());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}