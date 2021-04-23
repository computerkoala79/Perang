import java.util.ArrayList;

public class PlayerDeck {
	
	private ArrayList<Card> cards;
	private FullDeck fulldeck;
	private int deckSize;
	private Player player;
	
	public PlayerDeck(int deckSize, Player player) {
		validateDeckBuildSize(deckSize);
		this.deckSize = deckSize;
		cards = new ArrayList<>();
		buildPlayerDeck(deckSize);
		this.player = player;
	}
	
	private void buildPlayerDeck(int deckSize) {
		fulldeck = FullDeck.getInstance();
		for(int i = 0; i < deckSize; i++) {
			cards.add(fulldeck.drawCard());
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
			return player.getName() + ", your deck is empty.";
		
		for(int i = 0;i<deckSize;i++) {
			s.append(cards.get(i).toString() + "\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
