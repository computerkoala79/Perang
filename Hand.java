import java.util.ArrayList;

/*
 * Created by Jerry Klos
 */
public class Hand {
	
	private ArrayList<Card> cards;
	private PlayerDeck deck;
	
	public Hand(PlayerDeck deck) {
		this.deck = deck;
		deck.shuffle();
		cards = new ArrayList<>();
		buildHand();
	}
	
	private void buildHand() {
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
	
	public int[] getCardIDs() {
		int[] ids = new int[cards.size()];
		int index = 0;
		for(Card c : cards) {
			ids[index++] = c.getCardID();
		}
		return ids;
	}
	
	public Card getCard(int cardID) {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).getCardID() == cardID) {
				return cards.remove(i);
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerDeck d = new PlayerDeck(7);
		Hand h = new Hand(d);
		System.out.println(h.printPlayerHand());
	}
}