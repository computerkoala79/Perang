import java.util.ArrayList;

/*
 * Created by Jerry Klos
 */
public class Hand {
	
	private ArrayList<Card> cards;
	private PlayerDeck deck;
	
	public Hand(FullDeck fulldeck) {
		deck = deck.getInstance();
		cards = new ArrayList<>();
		buildHand(deck);
	}
	
	private void buildHand(FullDeck deck) {
		for(int i = 0; i < GameData.HAND_SIZE; i++)
			cards.add(deck.drawCard());
	}
	
	public String printPlayerHand() {
		StringBuilder s = new StringBuilder();
		s.append("*** Your Hand ***\n");
		for(Card c : cards) {
			s.append(c.toString() + "------\n");
		}
		s.append("**********");
		return s.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hand h = new Hand();
		System.out.println(h.printPlayerHand());
	}
}