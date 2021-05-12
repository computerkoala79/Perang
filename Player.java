/*
 * Created by Jerry Klos
 */
public class Player {
	
	private int playerid;
	private String name;
	private Hand hand;
	private PlayerDeck playerdeck;
	private PlayerSide cardslots;
	
	public Player(int playerid, String name) {
		this.playerid = playerid;
		this.name = name;
		playerdeck = new PlayerDeck(GameData.PLAYER_STARTER_DECK_SIZE);
		hand = new Hand(playerdeck);
		cardslots = new PlayerSide();

	}
	
	public PlayerSide getPlayerSide() {
		return cardslots;
	}
	
	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public PlayerDeck getPlayerdeck() {
		return playerdeck;
	}

	public void setPlayerdeck(PlayerDeck playerdeck) {
		this.playerdeck = playerdeck;
	}
	
	/**
	 * Print the Player's slots
	 * @return Returns a formatted String
	 */
	public String printPlayerSide() {
		StringBuilder s = new StringBuilder();
		CardSlot left = cardslots.getLeft();
		CardSlot center = cardslots.getCenter();
		CardSlot right = cardslots.getRight();
		
		s.append("\t\t\t****************** Player: " + getName() + " ******************\n");
		s.append("Slot:\t\tLeft\t\t\t"
				+ "Slot:\t\tCenter\t\t\t"
				+ "Slot:\t\tRight\n");		
		s.append(printCardName(left) + printCardName(center) + printCardName(right) + "\n");
		s.append(printCardType(left) + printCardType(center) + printCardType(right) + "\n");
		s.append(       "Attack:\t\t" + printAttack(left)
				+ "\t\t\tAttack:\t\t" + printAttack(center)
				+ "\t\t\tAttack:\t\t" + printAttack(right)  + "\n");
		s.append(       "Defense:\t" + printDefense(left)
				+ "\t\t\tDefense:\t" + printDefense(center)
				+ "\t\t\tDefense:\t" + printDefense(right) + "\n");
		s.append("\t\t\t*******************************************************\n");
		return s.toString();
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		CardSlot left = cardslots.getLeft();
		CardSlot center = cardslots.getCenter();
		CardSlot right = cardslots.getRight();
		
		
		s.append("\t\t\t****************** Player: " + getName() + " ******************\n");
		s.append("---------------\n");
		s.append("|  \\  |  /   |\n");
		s.append("|   " + printAttack(left) + "  " + printAttack(center) + "  " + printAttack(right) +      "\n");
		
		
		return s.toString();
	}
	
	private String printAttack(CardSlot slot) {
		if(slot.isEmpty()) {
			return "";
		}
		
		if(!slot.isFaceUp()) {
			return "";
		}
		
		
		return "" + slot.getCardInSlot().getAttack(GameData.LEFT) + " " 
				  + slot.getCardInSlot().getAttack(GameData.CENTER) + " " 
				  + slot.getCardInSlot().getAttack(GameData.RIGHT);
	}
	
	private String printDefense(CardSlot slot) {
		if(slot.isEmpty()) {
			return "";
		}
		
		if(!slot.isFaceUp()) {
			return "";
		}
		
		return "" + slot.getCardInSlot().getDefense(GameData.LEFT) + " " 
				  + slot.getCardInSlot().getDefense(GameData.CENTER) + " " 
				  + slot.getCardInSlot().getDefense(GameData.RIGHT);
	}
	
	private String printCardName(CardSlot slot) {
		if(slot.isEmpty()) {
			return "Card Name:\tEmpty Slot\t\t";
		}
		
		if(!slot.isFaceUp()) {
			return "Card Name:\tFace Down\t\t";
		}
		
		if(slot.getCardInSlot().getName().length() >= 8) {
			return "Card Name:\t" + slot.getCardInSlot().getName() + "\t\t";
		} else {
			return "Card Name:\t" + slot.getCardInSlot().getName() + "\t\t\t";
		}
	}
	
	private String printCardType(CardSlot slot) {
		if(slot.isEmpty()) {
			return "Card Type:\t\t\t\t";
		}
		
		if(!slot.isFaceUp()) {
			return "Card Type:\t\t\t\t";
		}
		
		int size = slot.getCardInSlot().getType().length();
		
		if(size < 5) {
			return "Card Type:\t" + slot.getCardInSlot().getType() + "\t\t\t\t";
		} else if(size == 5 || size == 6 || size == 7) {
			return "Card Type:\t" + slot.getCardInSlot().getType() + "\t\t\t";
		} else {
			return "Card Type:\t" + slot.getCardInSlot().getType() + "\t\t";
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}