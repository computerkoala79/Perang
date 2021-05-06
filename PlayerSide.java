/*
 * Created by Jerry Klos
 */
public class PlayerSide {
	
	private CardSlot left;
	private CardSlot center;
	private CardSlot right;
	private Player player;
	
	public PlayerSide(Player player) {
		
		left = new CardSlot(0);
		center = new CardSlot(1);
		right = new CardSlot(2);
		this.player = player;
		
		

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
	
	/**
	 * Print the Player's slots
	 * @return Returns a formatted String
	 */
	public String printPlayerSide() {
		StringBuilder s = new StringBuilder();
		s.append("\t\t\t****************** Player: " + player.getName() + " ******************\n");
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
		
		FullDeck fd = FullDeck.getInstance();
		fd.shuffle();
		Player player = new Player(0,"Player One");
		Player p2 = new Player(1,"player Two");
		PlayerSide ps = new PlayerSide(player);
		PlayerSide ps1 = new PlayerSide(p2);
		ps.getLeft().setCard(fd.drawCard(), player, GameData.LEFT);
		ps.getCenter().setCard(fd.drawCard(), player, GameData.CENTER);
		ps.getCenter().flipCard();
//		ps.getRight().setCard(fd.drawCard(), player, GameData.RIGHT);
		System.out.println(ps.printPlayerSide());
		ps1.getLeft().setCard(fd.drawCard(), player, GameData.LEFT);
		ps1.getCenter().setCard(fd.drawCard(), player, GameData.CENTER);
		ps1.getRight().setCard(fd.drawCard(), player, GameData.RIGHT);
		System.out.println(ps1.printPlayerSide());
		
	}
}