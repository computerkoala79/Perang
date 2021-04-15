
public class Card {
	
	private int cardID;
	private String name;
	private int[] attack;
	private int[] defense;
	private String type;
	
	public Card(int cardID, String name, int[] attack,
				int[] defense, String type) {
		this.cardID = cardID;
		this.name = name;
		this.attack = attack;
		this.defense = defense;
		this.type = type;
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

	public int getAttack(int attackDirection) {
		return attack[attackDirection];
	}

	public void setAttack(int attackValue, int attackDirection) {
		this.attack[attackDirection] = attackValue;
	}

	public int getDefense(int defenseDirection) {
		return defense[defenseDirection];
	}

	public void setDefense(int defenseValue, int defenseDirection) {
		this.defense[defenseDirection] = defenseValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String printCard() {
		StringBuilder s = new StringBuilder();
		s.append("-------------------\n");
		s.append("| \\ " + getAttack(0) + " -- " + getAttack(1) + " -- " + getAttack(2) + " / |\n");
		s.append("| / " + getDefense(0) + " -- " + getDefense(1) + " -- " + getDefense(2) + " \\ |\n");
		s.append("|                 |\n");
		s.append("|      " + getName() + "      |\n");
		s.append("|                 |\n");
		s.append("|     " + getType() + "     |\n");
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
		int id = 01;
		String cardName = "Jerry";
		String cardType = "Creator";
		
		Card card = new Card(id,cardName,attackValues,defenseValues,cardType);
		System.out.println(card.printCard());
		System.out.println(card.toString());
		System.out.println(card.toString());
		

	}

}
