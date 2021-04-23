/*
 * Created by Jerry Klos
 */
public class CardSlot {
	
	private int position;
	private int[] canAttack = new int[3];
	private Card card;
	private Player player;
	
	public CardSlot (int position) {
		this.position = position;
		card = null;
		buildCanAttack(position);
		player = null;
	}
	
	private void buildCanAttack(int position) {
		switch(position) {
			case 0: canAttack[0] = 0;
					canAttack[1] = 1;
					canAttack[2] = -1;
					break;
			case 1: canAttack[0] = 0;
					canAttack[1] = 1;
					canAttack[2] = 2;
					break;
			case 2: canAttack[0] = -1;
					canAttack[1] = 1;
					canAttack[2] = 2;
					break;
		}
	}

	public int getPosition() {
		return position;
	}

	private void setPosition(int position) {
		this.position = position;
	}
	
	public void setCard(Card card, Player player, int position) {
		this.card = card;
		this.player = player;
		setPosition(position);
	}
	
	public Card getCardInSlot() {
		return card;
	}
	
	public boolean isSlotEmpty(int position) {
		return card == null;
	}
	
	public String printSlot() {
		if(isSlotEmpty(position))
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