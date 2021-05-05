/*
 * Created by Jerry Klos
 */
public class PlayerSide {
	
	private CardSlot left;
	private CardSlot center;
	private CardSlot right;
	
	public PlayerSide() {
		
		left = new CardSlot(0);
		center = new CardSlot(1);
		right = new CardSlot(2);

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
	
	public String printPlayerSide() {
		StringBuilder s = new StringBuilder();
		s.append("Slot:\t\tLeft\t\t\t"
				+ "Slot:\t\tCenter\t\t\t"
				+ "Slot:\t\tRight\n");		
		s.append("Card ID:\t" + left.getCardInSlot().getCardID() 
				+ "\t\t\tCard ID:\t" + center.getCardInSlot().getCardID()
				+ "\t\t\tCard ID:\t" + right.getCardInSlot().getCardID() + "\n");
		s.append("Card Name:\t" + left.getCardInSlot().getName()
				+ "\t\tCard Name:\t" + center.getCardInSlot().getName()
				+ "\t\t\tCard Name:\t" + right.getCardInSlot().getName() + "\n");
		s.append("Card Type:\t" + left.getCardInSlot().getType()
				+ "\t\t\tCard Type:\t" + center.getCardInSlot().getType()
				+ "\t\t\tCard Type:\t" + right.getCardInSlot().getType() + "\n");
		s.append("Attack:\t\t" + printLeftAttack()
				+ "\t\t\tAttack:\t\t" + printCenterAttack()
				+ "\t\t\tAttack:\t\t" + printRightAttack()  + "\n");
		s.append("Defense:\t" + printLeftDefense()
				+ "\t\t\tDefense:\t" + printCenterDefense()
				+ "\t\t\tDefense:\t" + printRightDefense() + "\n");
		return s.toString();
	}
	
	private String printLeftAttack() {
		return "" + left.getCardInSlot().getAttack(Board.LEFT) + " " 
				  + left.getCardInSlot().getAttack(Board.CENTER) + " " 
				  + left.getCardInSlot().getAttack(Board.RIGHT);
	}
	
	private String printCenterAttack() {
		return "" + center.getCardInSlot().getAttack(Board.LEFT) + " " 
				  + center.getCardInSlot().getAttack(Board.CENTER) + " " 
				  + center.getCardInSlot().getAttack(Board.RIGHT);
	}
	
	private String printRightAttack() {
		return "" + right.getCardInSlot().getAttack(Board.LEFT) + " " 
				  + right.getCardInSlot().getAttack(Board.CENTER) + " " 
				  + right.getCardInSlot().getAttack(Board.RIGHT);
	}
	
	private String printLeftDefense() {
		return "" + left.getCardInSlot().getDefense(Board.LEFT) + " " 
				  + left.getCardInSlot().getDefense(Board.CENTER) + " " 
				  + left.getCardInSlot().getDefense(Board.RIGHT);
	}
	
	private String printCenterDefense() {
		return "" + center.getCardInSlot().getDefense(Board.LEFT) + " " 
				  + center.getCardInSlot().getDefense(Board.CENTER) + " " 
				  + center.getCardInSlot().getDefense(Board.RIGHT);
	}
	
	private String printRightDefense() {
		return "" + right.getCardInSlot().getDefense(Board.LEFT) + " " 
				  + right.getCardInSlot().getDefense(Board.CENTER) + " " 
				  + right.getCardInSlot().getDefense(Board.RIGHT);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FullDeck fd = FullDeck.getInstance();
		PlayerSide ps = new PlayerSide();
		Player player = new Player(0,"test");
		ps.getLeft().setCard(fd.drawCard(), player, Board.LEFT);
		ps.getCenter().setCard(fd.drawCard(), player, Board.CENTER);
		ps.getRight().setCard(fd.drawCard(), player, Board.RIGHT);
		System.out.println(ps.printPlayerSide());
		
	}
}