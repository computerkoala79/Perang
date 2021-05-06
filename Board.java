import java.util.concurrent.ThreadLocalRandom;

/*
 * Created by Jerry Klos
 */
public class Board {
	
	private CardSlot[] playerOneSlots = new CardSlot[GameData.NUM_SLOTS];
	private CardSlot[] playerTwoSlots = new CardSlot[GameData.NUM_SLOTS];
	
	private PlayerSide[] playerSides = new PlayerSide[GameData.NUM_PLAYERS];
	
	public Board(Player[] players) {
		
		for(int i = 0; i < GameData.NUM_PLAYERS; i++) {
			playerSides[i] = new PlayerSide(players[i]);
		}
		
		// create player slots
		for(int i=0; i < GameData.NUM_SLOTS; i++) {
			playerOneSlots[i] = new CardSlot(i);
		}
		
		for(int i=0; i < GameData.NUM_PLAYERS; i++) {
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
		playerSides[player.getPlayerid()].getRight().setCard(card, player, GameData.RIGHT);
	}

	private void placeCenter(Player player, Card card) {
		// TODO Auto-generated method stub
		playerSides[player.getPlayerid()].getCenter().setCard(card, player, GameData.CENTER);
	}

	private void placeLeft(Player player, Card card) {
		playerSides[player.getPlayerid()].getLeft().setCard(card, player, GameData.LEFT);
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