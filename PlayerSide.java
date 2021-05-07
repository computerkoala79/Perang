/*
 * Created by Jerry Klos
 */
public class PlayerSide {
	
	private CardSlot left;
	private CardSlot center;
	private CardSlot right;
	
	
	public PlayerSide() {
		left = new CardSlot(GameData.LEFT);
		center = new CardSlot(GameData.CENTER);
		right = new CardSlot(GameData.RIGHT);
	}
	
	public CardSlot getLeft() {
		return left;
	}

	public CardSlot getCenter() {
		return center;
	}

	public CardSlot getRight() {
		return right;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FullDeck fd = FullDeck.getInstance();
		fd.shuffle();
		Player player = new Player(0,"Player One");
		Player p2 = new Player(1,"player Two");
		
		
		
	}
}