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
	
	public CardSlot getSlotByID(int slotid) {
		switch(slotid) {
		case GameData.LEFT: return getLeft();
		case GameData.CENTER: return getCenter();
		default: return getRight();
		}
	}
	
	public boolean allSlotsAreEmpty() {
		return getLeft().isEmpty() &&
			   getCenter().isEmpty() &&
			   getRight().isEmpty();
	}
	
	public boolean oneCardRemainingOnLeft() {
		return !getLeft().isEmpty() &&
			   getCenter().isEmpty() &&
			   getRight().isEmpty();
	}
	
	public boolean oneCardRemainingOnRight() {
		return getLeft().isEmpty() &&
			   getCenter().isEmpty() &&
			   !getRight().isEmpty();
	}
	
	public Card getCardFromSlotID(int slotid) {
		switch(slotid) {
			case GameData.LEFT: return this.getLeft().getCardInSlot();
			case GameData.CENTER: return getCenter().getCardInSlot();
			default: return this.getRight().getCardInSlot();
		}
	}
	
	public int[] getAttackValues(int slotid) {
		return getCardFromSlotID(slotid).getAttackValues();
	}
	
	public int[] getDefendValues(int slotid) {
		return getCardFromSlotID(slotid).getDefendValues();
	}
	
	public int[] getValidAttackDirections(int slotid) {
		return this.getSlotByID(slotid).getValidAttackDirections();
	}
	
	public int getNumberOfEmptySlots() {
		int emptySlots = 0;
		
		for(int i = 0; i < GameData.NUM_SLOTS; i++) {
			if(this.getSlotByID(i).isEmpty()) {
				emptySlots++;
			}
		}
		return emptySlots;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FullDeck fd = FullDeck.getInstance();
		fd.shuffle();
		Player player = new Player(0,"Player One");
		Player p2 = new Player(1,"player Two");
		
		
		
	}
}