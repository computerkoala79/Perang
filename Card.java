/*
 * Created by Jerry Klos
 */
public class Card {
	
	private int cardID;
	private String name;
	private int[] attack;
	private int[] defense;
	private String type;
	
	/**
	 * Public constructor, requires an integer ID to create the Card object.
	 * The ID is used to pull information from the GameData class for building the elements of the Card.
	 * GameData.EMPTY_SLOT can be used to create an "empty" Card.
	 * @param cardID
	 */
	public Card(int cardID) {
		if(cardID == GameData.EMPTY_SLOT) {
			this.cardID = cardID;
			name = "** Empty **";
			attack = null;
			defense = null;
			type = "** Empty **";
		} else {
			this.cardID = cardID;
			name = GameData.CARD_NAMES[cardID][0];
			attack = GameData.ATTACK_STATS[cardID];
			defense = GameData.DEFENSE_STATS[cardID];
			type = GameData.CARD_NAMES[cardID][1];
		}
	}

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Attack Values are kept in an array therefore an integer representing the attack direction
	 * is used as an index to access the attack value.
	 * @param attackDirection
	 * @return
	 */
	public int getAttack(int attackDirection) {
		return attack[attackDirection];
	}
	
	/**
	 * Sets an individual attack value inside the attack array. 
	 * An integer for the attack value and the attack direction is used as an index.
	 * @param attackValue
	 * @param attackDirection
	 */
	public void setAttack(int attackValue, int attackDirection) {
		this.attack[attackDirection] = attackValue;
	}
	
	/**
	 * Defend Values are kept in an array therefore an integer representing the defend direction
	 * is used as an index to access the defend value.
	 * @param defenseDirection
	 * @return
	 */
	public int getDefense(int defenseDirection) {
		return defense[defenseDirection];
	}
	
	/**
	 * Sets an individual defend value inside the attack array. 
	 * An integer for the defend value and the defend direction is used as an index.
	 * @param defenseValue
	 * @param defenseDirection
	 */
	public void setDefense(int defenseValue, int defenseDirection) {
		this.defense[defenseDirection] = defenseValue;
	}
	
	public int[] getAttackValues() {
		return attack;
	}
	
	public int[] getDefendValues() {
		return defense;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Searches the attack array for the highest value and returns the index within the array.
	 * Used for AI
	 * @return
	 */
	public int getHighestAttackDirectionFromCard() {
		int highestAttackValue = -1;
		int attackDirection = -1;
		int[] attackValues = getAttackValues();
		for(int i = 0; i < attackValues.length; i++) {
			if(highestAttackValue < attackValues[i]) {
				highestAttackValue = attackValues[i];
				attackDirection = i;
			}
		}
		return attackDirection;
	}
	
	/**
	 * Searches the attack array for the highest value and returns it.
	 * Used for AI
	 * @return
	 */
	public int getHighestAttackValueFromCard() {
		int highestAttackValue = -1;
		
		int[] attackValues = getAttackValues();
		for(int i = 0; i < attackValues.length; i++) {
			if(highestAttackValue < attackValues[i]) {
				highestAttackValue = attackValues[i];
			}
		}
		return highestAttackValue;
	}
	
	/**
	 * Prints out the card in a more visually pleasing format.
	 * @return
	 */
	public String printCard() {
		StringBuilder s = new StringBuilder();
		s.append("-------------------\n");
		s.append("| \\ " + getAttack(0) + " -- " + getAttack(1) + " -- " + getAttack(2) + " / |\n");
		s.append("| / " + getDefense(0) + " -- " + getDefense(1) + " -- " + getDefense(2) + " \\ |\n");
		s.append("|-----------------|\n");
		s.append("    " + getName() + "\n");
		s.append("\n");
		s.append("    " + getType() + "\n");
		s.append("-------------------\n");
		return s.toString();
	}
	
	public String toString() {
		return "Card ID:\t" + getCardID() + "\n"
			+  "Card Name:\t" + getName() + "\n"
			+  "Card Type:\t" + getType() + "\n"
			+  "Attack:\t\t" + getAttack(0) + " " + getAttack(1) + " " + getAttack(2) + "\n"
			+  "Defense:\t" + getDefense(0) + " " + getDefense(1) + " " + getDefense(2) + "\n";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] attackValues = new int[] {2,3,4};
		int[] defenseValues = new int[] {4,2,3};
		int id = 0;
		
		Card card = new Card(6);
		System.out.println(card.printCard());
		System.out.println(card.toString());
		System.out.println(card.toString());
		

	}

}
