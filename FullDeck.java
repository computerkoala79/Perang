import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * Created by Jerry Klos
 */
public class FullDeck {
	
	private ArrayList<Card> cards;
	
	public FullDeck() {
		cards = new ArrayList<Card>();
		buildDeck();
	}
	
	private void buildDeck() {
		for(int i = 0; i<GameData.FULL_DECK_SIZE;i++) {
			cards.add(new Card(i));
		}
	}
	
	public Card drawCard() {
		return cards.remove(0);
	}
	
	public String printFullDeck() {
		StringBuilder s = new StringBuilder();
		for(int i = 0;i<GameData.FULL_DECK_SIZE;i++) {
			s.append("Card Number: " + this.cards.get(i).getCardID() + "\n");
			s.append(this.cards.get(i).printCard());
		}
		return s.toString();
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0;i<GameData.FULL_DECK_SIZE;i++) {
			s.append(this.cards.get(i).toString() + "\n");
		}
		return s.toString();
	}
	
	private void shuffle() {
		Random rand = new Random();
		int r = 0;
		for(int i = 1; i<GameData.FULL_DECK_SIZE ; i++) {
			r = rand.nextInt(i+1);
			if(r!=i) {
				Collections.swap(cards, i, r);
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FullDeck fd = new FullDeck();
		fd.shuffle();
		System.out.println(fd.toString());
	}

}
