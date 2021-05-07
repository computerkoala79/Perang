import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayerDeck {
	
	private ArrayList<Card> cards;
	private FullDeck fulldeck;
	private int deckSize;
	
	
	public PlayerDeck(int deckSize) {
		validateDeckBuildSize(deckSize);
		this.deckSize = deckSize;
		cards = new ArrayList<>();
		buildPlayerDeck(deckSize);
		
	}
	
	private void buildPlayerDeck(int deckSize) {
		fulldeck = FullDeck.getInstance();
		for(int i = 0; i < deckSize; i++) {
			cards.add(fulldeck.drawCard());
		}
	}
	
	public void shuffle() {
		Random rand = new Random();
		int r = 0;
		for(int i = 1; i < deckSize ; i++) {
			r = rand.nextInt(i+1);
			if(r!=i) {
				Collections.swap(cards, i, r);
			}
		}
	}
	
	private void validateDeckBuildSize(int deckSize) {
		if(deckSize < 1)
			throw new IllegalArgumentException("Cannot build an empty deck.");
	}
	
	private void validateDeckSize(int deckSize) {
		if(deckSize < 1) 
			throw new IllegalArgumentException("Cannot reduce deck below zero.");
	}
	
	public int getDeckSize() {
		return deckSize;
	}
	
	public ArrayList<Card> getPlayerDeck(){
		return cards;
	}
	
	public Card drawCard() {
		validateDeckSize(deckSize);
		deckSize--;
		return cards.remove(0);
	}
	
	public void addCard() {
		deckSize++;
		cards.add(fulldeck.drawCard());
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		if(deckSize == 0)
			return "Your deck is empty.";
		
		for(int i = 0;i<deckSize;i++) {
			s.append(cards.get(i).toString() + "\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
