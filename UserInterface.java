import java.util.Scanner;

/*
 * Created by Jerry Klos
 */
public class UserInterface {
	
	private static final int ATTACK_INDEX = 0;
	private static final int DEFEND_INDEX = 1;
	
	public String getPlayerName() {
		Scanner input = new Scanner(System.in);
		System.out.println("-- Please enter your name --");
		String playername = input.nextLine();
		System.out.println("** " + playername + " , welcome to the battle! **");
		return playername;
	}
	
	public void placePlayerCards(Player player, Board board) {
		int[] choices = chooseCardsFromHand(player);
		Card left = player.getHand().getCard(choices[GameData.LEFT]);
		Card center = player.getHand().getCard(choices[GameData.CENTER]);
		Card right = player.getHand().getCard(choices[GameData.RIGHT]);
		board.placeCard(player, GameData.LEFT, left);
		board.placeCard(player, GameData.CENTER, center);
		board.placeCard(player, GameData.RIGHT, right);
		
	}
	
	private int[] chooseCardsFromHand(Player player) {
		Hand hand = player.getHand();
		System.out.println("******\n");
		System.out.println("* Choose 3 cards to place on the board. *\n"
							+ "-------\n"
							+ hand.printPlayerHand() + "\n"
							+ "-- Choose a card for the left slot --\n"
							+ "-- Enter a card id from your hand  ");
		Scanner input = new Scanner(System.in);
		int[] choices = new int[GameData.NUM_SLOTS];
		choices[GameData.LEFT] = input.nextInt();
		
		System.out.println("-- Choose a card for the center slot --\n" 
							+ "-- Enter a card id from your hand  ");
		
		choices[GameData.CENTER] = input.nextInt();
		
		System.out.println("-- Choose a card for the right slot --\n" 
							+ "-- Enter a card id from your hand  ");
		
		choices[GameData.RIGHT] = input.nextInt();
		
		return choices;
		
	}
	
	public void setAIcards(Player ai, Board board) {
		
		Hand hand = ai.getHand();
		
		int[] cardIDs = hand.getCardIDs();
		
		for(int i = 0; i < GameData.NUM_SLOTS; i++) {
			board.placeCard(ai, i, hand.getCard(cardIDs[i]));
		}
		
		flipCard(ai.getPlayerSide().getLeft());
		flipCard(ai.getPlayerSide().getCenter());
		flipCard(ai.getPlayerSide().getRight());
		
	}
	
	public void flipCard(CardSlot slot) {
		if(slot.isFaceUp()) {
			slot.setFaceUp(false);
		} else {
			slot.setFaceUp(true);
		}
	}
	
	private CardSlot getCardSlotFromChoice(PlayerSide slots, String choice) {
		if(choice.toLowerCase().equals("left") 
				&& !slots.getLeft().isEmpty()) {
			return slots.getLeft();
		} else if(choice.toLowerCase().equals("center")
				&& !slots.getCenter().isEmpty()) {
			return slots.getCenter();
		} else if(choice.toLowerCase().equals("right")
				&& !slots.getRight().isEmpty()) {
			return slots.getRight();
		} else {
			return null;
		}
	}
	
	private int setAttackDirection(PlayerSide slots, String choice) {
		if(choice.toLowerCase().equals("left")) {
			return GameData.LEFT;
		} else if(choice.toLowerCase().equals("center")) {
			return GameData.CENTER;
		} else if(choice.toLowerCase().equals("right")) {
			return GameData.RIGHT;
		} else {
			return -1;
		}
	}
	
	private int[] setAttackValues(CardSlot attackSlot, CardSlot defendSlot, String choice, int attackDirection, int[] attackValues) {
		Card attackCard = attackSlot.getCardInSlot();
		Card defendCard = defendSlot.getCardInSlot();
		
		switch(attackDirection) {
		// case for attacking from a card in the left slot of the attacker
		// the attacker can only attack the defender's left and center cards
		case GameData.LEFT:
			if(choice.toLowerCase().equals("left")) {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.CENTER);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.CENTER);
				return attackValues;
			} else if(choice.toLowerCase().equals("center")) {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.RIGHT);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.LEFT);
				return attackValues;
			} else {
				return attackValues;
			}
		
		// case for attacking from a card in the center slot of the attacker
		// the attacker can go to any defending slot
		case GameData.CENTER:
			if(choice.toLowerCase().equals("left")) {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.LEFT);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.RIGHT);
				return attackValues;
			} else if(choice.toLowerCase().equals("center")) {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.CENTER);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.CENTER);
				return attackValues;
			} else {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.RIGHT);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.LEFT);
				return attackValues;
			}
		
		// case for attacking from a card in the right slot of the attacker
		// the attacker can only attack the defender's center and right cards
		default:
			if(choice.toLowerCase().equals("left")) {
				return attackValues;
			} else if(choice.toLowerCase().equals("center")) {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.LEFT);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.RIGHT);
				return attackValues;
			} else {
				attackValues[ATTACK_INDEX] = attackCard.getAttack(GameData.CENTER);
				attackValues[DEFEND_INDEX] = defendCard.getDefense(GameData.CENTER);
				return attackValues;
			}
		}
	}
	
	public void cardBattle(Player attacker,Player defender, Board board) {
		
		// create local variables
		PlayerSide attackerSide = attacker.getPlayerSide();
		PlayerSide defenderSide = defender.getPlayerSide();
		
		int attackDirection = -1;
		
		int[] attackValues = {-1,0};
		
		boolean attackerWins = false;
		
		Scanner input = new Scanner(System.in);
		
		String choice;
		String[] validStrings = {"left","center","right"};
		
		CardSlot attackSlot = null;
		CardSlot defendSlot = null;
		
		// get and verify user input
		do {
			System.out.println("-- " + attacker.getName() + ", choose a card to attack with --");
			System.out.println("-- Enter left, center, right: ");
			choice = input.nextLine();
			attackSlot = this.getCardSlotFromChoice(attackerSide, choice);
			if(attackSlot == null) {
				System.out.println("!!!! Invalid Choice !!!!\n");
			} else {
				attackDirection = this.setAttackDirection(attackerSide, choice);
			}
		} while (attackSlot == null);
		
		do {
			System.out.println("-- " + attacker.getName() + ", choose a card to attack --");
			System.out.println("-- Enter left, center, right: ");
			choice = input.nextLine();
			defendSlot = this.getCardSlotFromChoice(defenderSide, choice);
			if(defendSlot == null) {
				System.out.println("!!!! Invalid Choice !!!!\n");
			} 
		} while (defendSlot == null);
		
		// get attack and defend values
		attackValues = this.setAttackValues(attackSlot, defendSlot, choice, attackDirection, attackValues);
		
		// ******* debugging
		System.out.println("Attacker Value: " + attackValues[ATTACK_INDEX] + "\n" 
							+ "Defender Value: " + attackValues[DEFEND_INDEX]);
		
		// if the attack value remains at -1, restart the cardBattle method
		if(attackValues[ATTACK_INDEX] == -1) {
			System.out.println("!!!!! Your attack direction is invalid !!!!!");
			cardBattle(attacker, defender, board);
		}
		
		// flip cards if necessary
		if(!attackSlot.isFaceUp()) {
			flipCard(attackSlot);
		}
		
		if(!defendSlot.isFaceUp()) {
			flipCard(defendSlot);
		}
		
		if(attackValues[ATTACK_INDEX] == attackValues[DEFEND_INDEX]) {
			float attack = board.attackModifier(attackValues[ATTACK_INDEX]);
			float defend = board.defenseModifier(attackValues[DEFEND_INDEX]);
			
			// ******* debugging
			System.out.println("Attack Value: " + attack + "\n"
								+ "Defend Value: " + defend);
			
			if(attack - defend > 0.0) {
				attackerWins = true;
			} 
			
		} else if(attackValues[ATTACK_INDEX] - attackValues[DEFEND_INDEX] > 0.0) {
			attackerWins = true;
		}
		
		if(attackerWins) {
			System.out.println("**** " + defender.getName()
					+ " loses the card. ****");
			defendSlot.removeCard();
		} else {
			System.out.println("**** " + attacker.getName()
					+ " loses the card. ****");
			attackSlot.removeCard();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}