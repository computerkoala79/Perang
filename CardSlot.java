/*
 * Created by Jerry Klos
 */
public class CardSlot {
	
	private int position;
	private int[] canAttack = new int[3];
	private Card card;
	private Player player;
	private boolean faceUp;
	
	/**
	 * Public constructor for Card Slot object.
	 * Requires an integer position reference to indicate whether it is a left, center or right slot.
	 * @param position
	 */
	public CardSlot (int position) {
		this.position = position;
		card = null;
		buildCanAttack();
		player = null;
		faceUp = true;
	}
	
	/**
	 * Builds the can attack array with using the slot's position as reference.
	 * All Slots can attack make a center attack, but only the center slot can attack to both the right and left.
	 * Zeros indicate the slot cannot attack in that direction.
	 * Array index represents direction [left,center,right]
	 * @param position
	 */
	private void buildCanAttack() {
		switch(position) {
			case 0: canAttack[0] = 1;
					canAttack[1] = 1;
					canAttack[2] = 0;
					break;
			case 1: canAttack[0] = 1;
					canAttack[1] = 1;
					canAttack[2] = 1;
					break;
			case 2: canAttack[0] = 0;
					canAttack[1] = 1;
					canAttack[2] = 1;
					break;
		}
	}
	
	/**
	 * Returns true if the card slot is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return card == null;
	}
	
	/**
	 * Returns true if the card in the slot is face up
	 * @return
	 */
	public boolean isFaceUp() {
		return faceUp;
	}
	
	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}
	
	public int[] getValidAttackDirections() {
		return canAttack;
	}

	public int getPosition() {
		return position;
	}

	private void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * Given a Card and a Player, sets the card into the slot and assigns the player.
	 * @param card
	 * @param player
	 * @param position
	 */
	public void setCard(Card card, Player player) {
		this.card = card;
		this.player = player;
		setPosition(position);
	}
	
	public Card getCardInSlot() {
		return card;
	}
	
//	public boolean isSlotEmpty(int position) {
//		return card == null;
//	}
	
	/**
	 * Sets card to null. Used to 'destroy' cards during battle.
	 */
	public void removeCard() {
		card = null;
	}
	
	/**
	 * Returns a formatted string with the card slot's information.
	 * @return
	 */
	public String printSlot() {
		if(isEmpty())
			return player.getName() + ", this card slot is empty.";
		
		StringBuilder s = new StringBuilder();
		
		s.append("Card Slot:\t");
		
		switch(position) {
			case 0: s.append("left\n"); break;
			case 1: s.append("center\n"); break;
			default: s.append("right\n"); break;
		}
		
		s.append(card.printCard());
		
		return s.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}