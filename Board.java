import java.util.concurrent.ThreadLocalRandom;

/*
 * Created by Jerry Klos
 */
public class Board {
	
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	
	private CardSlot[] playerOneSlots = new CardSlot[3];
	private CardSlot[] playerTwoSlots = new CardSlot[3];
	
	private PlayerSide[] playerSides = new PlayerSide[2];
	
	public Board() {
		
		for(int i = 0; i < playerSides.length; i++) {
			playerSides[i] = new PlayerSide();
		}
		
		// create player slots
		for(int i=0; i < playerOneSlots.length; i++) {
			playerOneSlots[i] = new CardSlot(i);
		}
		
		for(int i=0; i < playerTwoSlots.length; i++) {
			playerOneSlots[i] = new CardSlot(i);
		}
		
	}
	
	public void placeCard(Player player, int position, Card card) {
		int playerid = player.getPlayerid();
		
		switch(playerid) {
		case 0: 
			switch(position) {
			case 0: placeLeft(player,card); break;
			case 1: placeCenter(player,card); break;
			default: placeRight(player,card); break;
			}
			return;
		
		default: 
			switch(position) {
			case 0: placeLeft(player,card); break;
			case 1: placeCenter(player,card); break;
			default: placeRight(player,card); break;
			}
			return;
		 }
	}
	
	private void placeRight(Player player, Card card) {
		// TODO Auto-generated method stub
		playerSides[player.getPlayerid()].getRight().setCard(card, player, RIGHT);
	}

	private void placeCenter(Player player, Card card) {
		// TODO Auto-generated method stub
		playerSides[player.getPlayerid()].getCenter().setCard(card, player, CENTER);
	}

	private void placeLeft(Player player, Card card) {
		playerSides[player.getPlayerid()].getLeft().setCard(card, player, LEFT);
	}
	
	public String printPlayerSlots(int playerid) {
		StringBuilder s = new StringBuilder();
		if(playerid == 0) {
			for(int i = 0; i < playerOneSlots.length; i++) {
				s.append("Card Slot " + i);
				if(playerOneSlots[i].isSlotEmpty(i)) {
					s.append("Empty Slot");
				} else {
					s.append(playerOneSlots[i].printSlot());
				}
			}
		}
		return s.toString();
	}
	
	public void ppplaceCard(int cardslot, Player player, Card card) {
		
		switch(player.getPlayerid()) {
			case 0: if(!playerOneSlots[cardslot].isSlotEmpty(cardslot)) return;
					playerOneSlots[cardslot].setCard(card, player, cardslot);
					return;
			default: if(!playerTwoSlots[cardslot].isSlotEmpty(cardslot)) return; 
					 playerTwoSlots[cardslot].setCard(card, player, cardslot);
					 return;
		}
		
	}
	
	/**
	 * Flip a coin for determining binary outcomes
	 * @return
	 */
	public int coinFlip() {
		return ThreadLocalRandom.current().nextInt(2);
	}
	
	/**
	 * attack modifier slightly adjusts the value of the attack to allow
	 * two equal values to determine a winner
	 * i.e. if an attack of 9 is against a defense of 9, both values are given a random
	 * adjustment. The values are compared a winner can be determined.
	 * @param attackValue
	 * @return
	 */
	protected float attackModifier(int attackValue) {
		return ThreadLocalRandom.current().nextFloat() 
				- (ThreadLocalRandom.current().nextFloat())
				+ attackValue;
	}
	
	protected float defenseModifier(int defenseValue) {
		return ThreadLocalRandom.current().nextFloat() + defenseValue;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FullDeck fd = FullDeck.getInstance();
		Player p1 = new Player(0,"test player");
		
	}
}