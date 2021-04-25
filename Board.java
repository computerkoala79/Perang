import java.util.concurrent.ThreadLocalRandom;

/*
 * Created by Jerry Klos
 */
public class Board {
	
	private CardSlot[] playerOneSlots;
	private CardSlot[] playerTwoSlots;
	
	public Board() {
		
		
		
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
		
	}
}