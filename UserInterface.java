import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Created by Jerry Klos
 */
public class UserInterface {
	
	// Constants for attack and defend indices
	private static final int ATTACK_INDEX = 0;
	private static final int DEFEND_INDEX = 1;
	public static final int PLAYERSIDE_GAME_STATE_3CARDS = 300; 
	public static final int PLAYERSIDE_GAME_STATE_2CARDS_LC = 200;
	public static final int PLAYERSIDE_GAME_STATE_2CARDS_LR = 201;
	public static final int PLAYERSIDE_GAME_STATE_2CARDS_CR = 202;
	public static final int PLAYERSIDE_GAME_STATE_1CARD_L = 100;
	public static final int PLAYERSIDE_GAME_STATE_1CARD_C = 101;
	public static final int PLAYERSIDE_GAME_STATE_1CARD_R = 102;
	public static final int PLAYERSIDE_GAME_STATE_NOCARDS = -101;
	private static final String INPUT_LEFT = "left";
	private static final String INPUT_CENTER = "center";
	private static final String INPUT_RIGHT = "right";
	private ArrayList<String> validStringInput = new ArrayList<>();
	private ArrayList<Integer> validHandInput = new ArrayList<>();
	
	/**
	 * Verifies user input is a valid choice and returns a matching boolean
	 * compares against validStringInput method
	 * @param choice
	 * @return
	 */
	private boolean verifyStringChoice(String choice) {
		for(String s : validStringInput) {
			if(s.equals(choice.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get AI Attack And Defend Slots
	 * Method takes player game states and the player sides to determine
	 * the AI's selection of Attack and Defend Card slots
	 * currently the AI does not use an informed function to make attack decisions
	 * @param attackerGameState
	 * @param defenderGameState
	 * @param ai
	 * @param player
	 * @return Returns a size 2 array of Card Slots in the form [attackCardSlot,defendCardSlot]
	 */
 	private CardSlot[] getAIAttackAndDefendSlots(int attackerGameState,int defenderGameState, PlayerSide ai, PlayerSide player) {
 		// creates a card slot array to return
 		CardSlot[] aiChoices = new CardSlot[2];
		
		// create null CardSlots to represent attack and defend selections
		CardSlot attackSlot = null;
		CardSlot defendSlot = null;
		
		// AI selects cards based on game states
		if(attackerGameState == PLAYERSIDE_GAME_STATE_3CARDS 
				&& defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS) {
			
			int attackSlotID = ThreadLocalRandom.current().nextInt(3);
			int defendSlotID = ThreadLocalRandom.current().nextInt(3);
			attackSlot = ai.getSlotByID(attackSlotID);
			
			// Guard against invalid attack
			if(attackSlotID == GameData.LEFT && defendSlotID == GameData.RIGHT) {
				defendSlotID--;
			} else if(attackSlotID == GameData.RIGHT && defendSlotID == GameData.LEFT) {
				defendSlotID++;
			}
			
			defendSlot = player.getSlotByID(defendSlotID);
		
		} else {
			
		int attackSlotID = 0;
		switch(attackerGameState) {
		
			case PLAYERSIDE_GAME_STATE_3CARDS:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					attackSlotID = ThreadLocalRandom.current().nextInt(2);
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					attackSlotID = ThreadLocalRandom.current().nextInt(2) + 1;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					attackSlotID = ThreadLocalRandom.current().nextInt(3);
					while(attackSlotID == GameData.CENTER) {
						attackSlotID = ThreadLocalRandom.current().nextInt(3);
					}
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					attackSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					attackSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} break;
			
			case PLAYERSIDE_GAME_STATE_2CARDS_LC:
				attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.CENTER;
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					if(attackSlotID == GameData.LEFT) {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(GameData.CENTER);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					if(attackSlotID == GameData.CENTER) {
						int defendSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					attackSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(GameData.RIGHT);
				} break;
				
			case PLAYERSIDE_GAME_STATE_2CARDS_LR:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					if(attackSlotID == GameData.LEFT) {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(GameData.CENTER);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					if(attackSlotID == GameData.RIGHT) {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(GameData.CENTER);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					attackSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(GameData.CENTER);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					attackSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} break;
				
			case PLAYERSIDE_GAME_STATE_2CARDS_CR:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					attackSlotID = new Random().nextBoolean() ? GameData.CENTER : GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					attackSlotID = new Random().nextBoolean() ? GameData.CENTER : GameData.RIGHT;
					if(attackSlotID == GameData.RIGHT) {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(GameData.CENTER);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					attackSlotID = new Random().nextBoolean() ? GameData.CENTER : GameData.RIGHT;
					if(attackSlotID == GameData.CENTER) {
						int defendSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					attackSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					attackSlot = ai.getSlotByID(GameData.CENTER);
					defendSlot = player.getSlotByID(GameData.LEFT);
				} break;
				
			case PLAYERSIDE_GAME_STATE_1CARD_L:
				attackSlot = ai.getSlotByID(GameData.LEFT);
				if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					defendSlot = player.getSlotByID(GameData.CENTER);
				} else {
					defendSlot = player.getSlotByID(GameData.LEFT);
				} break;
				
			case PLAYERSIDE_GAME_STATE_1CARD_R:
				attackSlot = ai.getSlotByID(GameData.RIGHT);
				if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					defendSlot = player.getSlotByID(GameData.CENTER);
				} else {
					defendSlot = player.getSlotByID(GameData.RIGHT);
				} break;
				
			case PLAYERSIDE_GAME_STATE_1CARD_C:
				attackSlot = ai.getSlotByID(GameData.CENTER);
				if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					defendSlot = player.getSlotByID(GameData.LEFT);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					defendSlot = player.getSlotByID(GameData.RIGHT);
				} else {
					defendSlot = player.getSlotByID(GameData.CENTER);
				} break;
			}
		}
		
		aiChoices[ATTACK_INDEX] = attackSlot;
		aiChoices[DEFEND_INDEX] = defendSlot;
		return aiChoices;
	}
	
	/**
	 * Print Valid Defender Card Options
	 * Method takes an attackSlot and a defender game state to determine 
	 * valid options for the player to attack.
	 * @param attackSlot
	 * @param defenderGameState
	 * @return Formated String
	 */
	private String printValidDefenderCardOptions(CardSlot attackSlot, int defenderGameState) {
		if(!validStringInput.isEmpty())
			validStringInput.clear();
		
		StringBuilder s = new StringBuilder();
		int attackPosition = attackSlot.getPosition();
		
		s.append("-- Choose a card to attack. --\n");
		s.append("-- Valid choices: ");
		
		switch(attackPosition) {
			case GameData.LEFT: 
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					s.append("left or center --\n");
					validStringInput.add(INPUT_LEFT);
					validStringInput.add(INPUT_CENTER);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR || defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					s.append("center --\n");
					validStringInput.add(INPUT_CENTER);
				} else {
					s.append("left --\n");
					validStringInput.add(INPUT_LEFT);
				} break;
			case GameData.CENTER:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS) {
					s.append("left, center, or right --\n");
					validStringInput.add(INPUT_LEFT);
					validStringInput.add(INPUT_CENTER);
					validStringInput.add(INPUT_RIGHT);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					s.append("center or right --\n");
					validStringInput.add(INPUT_CENTER);
					validStringInput.add(INPUT_RIGHT);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					s.append("left or center --\n");
					validStringInput.add(INPUT_LEFT);
					validStringInput.add(INPUT_CENTER);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					s.append("left or right --\n");
					validStringInput.add(INPUT_LEFT);
					validStringInput.add(INPUT_RIGHT);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					s.append("left --\n");
					validStringInput.add(INPUT_LEFT);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					s.append("center --\n");
					validStringInput.add(INPUT_CENTER);
				} else {
					s.append("right --\n");
					validStringInput.add(INPUT_RIGHT);
				} break;
			default:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					s.append("center or right --\n");
					validStringInput.add(INPUT_CENTER);
					validStringInput.add(INPUT_RIGHT);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC || defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					s.append("center --\n");
					validStringInput.add(INPUT_CENTER);
				} else {
					s.append("right --\n");
					validStringInput.add(INPUT_RIGHT);
				} break;
		}
		
		return s.toString();
	}
	
	/**
	 * Battle Phase
	 * Method takes an attacking player and a defending player along with the game board to 
	 * prompt user with card selections. Based on selections, a card battle ensues and a 
	 * winner is determined. No ties are allowed in single card battles. The losing card is removed
	 * from the board. If either attacking or defending card is face down on the board, it is 
	 * turned face up.
	 * @param attacker
	 * @param defender
	 * @param board
	 */
	public void battlePhase(Player attacker, Player defender, Board board) {
		int attackerGameState = getPlayerSideGameState(attacker.getPlayerSide());
		int defenderGameState = getPlayerSideGameState(defender.getPlayerSide());
		CardSlot attackSlot;
		CardSlot defendSlot;
		String attackChoices;
		String defendChoices;
		boolean attackerWins = false;
		
		if(attacker.getPlayerid() == GameData.PLAYER_ID) {
			
			System.out.println("---- " + attacker.getName() + " ----");
			
			// test ai v ai
			
//			CardSlot[] aiChoices = getAIAttackAndDefendSlots(attackerGameState,defenderGameState,attacker.getPlayerSide(),defender.getPlayerSide());
//			attackSlot = aiChoices[ATTACK_INDEX];
//			defendSlot = aiChoices[DEFEND_INDEX];
			
			// end test ai v ai
			
			Scanner input = new Scanner(System.in);
			
			attackChoices = this.printValidOptionsForAttackerCardSelection(attackerGameState);
			System.out.println(attackChoices);
			String attackCardChoice = input.nextLine();
			
			while(!verifyStringChoice(attackCardChoice)) {
				System.out.println("!!!!! Invalid Input !!!!!");
				System.out.println(attackChoices);
				attackCardChoice = input.nextLine();
			}
			
			attackSlot = this.getCardSlotFromChoice(attacker.getPlayerSide(), attackCardChoice);
			
			defendChoices = this.printValidDefenderCardOptions(attackSlot, defenderGameState);
			System.out.println(defendChoices);
			String defendCardChoice = input.nextLine();
			
			while(!verifyStringChoice(defendCardChoice)) {
				System.out.println("!!!!! Invalid Input !!!!!");
				System.out.println(defendChoices);
				defendCardChoice = input.nextLine();
			}
			
			defendSlot = this.getCardSlotFromChoice(defender.getPlayerSide(), defendCardChoice);
			
		} else {
			
			System.out.println("---- " + attacker.getName() + " ----");
			
			CardSlot[] aiChoices = getAIAttackAndDefendSlots(attackerGameState,defenderGameState,attacker.getPlayerSide(),defender.getPlayerSide());
			attackSlot = aiChoices[ATTACK_INDEX];
			defendSlot = aiChoices[DEFEND_INDEX];
			
		}
		
		if(!attackSlot.isFaceUp()) {
			flipCard(attackSlot);
		}
		
		if(!defendSlot.isFaceUp()) {
			flipCard(defendSlot);
		}
		
		System.out.println("-- " + attackSlot.getCardInSlot().getName() + " attacks " + defendSlot.getCardInSlot().getName());
		
		int battleValues[] = getAttackValueFromChoices(attackSlot,defendSlot);
		
		System.out.println("Attack Value: " + battleValues[ATTACK_INDEX]);
		System.out.println("Defend Value: " + battleValues[DEFEND_INDEX]);
		
		if(battleValues[ATTACK_INDEX] == battleValues[DEFEND_INDEX]) {
			float modifiedAttack = board.attackModifier(battleValues[ATTACK_INDEX]);
			float modifiedDefend = board.defenseModifier(battleValues[DEFEND_INDEX]);
			
			System.out.println("Modified Attack Value: " + modifiedAttack);
			System.out.println("Modified Defend Value: " + modifiedDefend);
			
			if(modifiedAttack > modifiedDefend) {
				attackerWins = true;
			}
		} 
		
		if(battleValues[ATTACK_INDEX] > battleValues[DEFEND_INDEX]) {
			attackerWins = true;
		}
		
		if(attackerWins) {
			defendSlot.removeCard();
		} else {
			attackSlot.removeCard();
		}
		
	}
	
	
	/**
	 * Get Attack Values from Choices
	 * Based on an attackSlot and defendSlot, returns an array containing the attack and defend values
	 * @param attackSlot
	 * @param defendSlot
	 * @return
	 */
	private int[] getAttackValueFromChoices(CardSlot attackSlot, CardSlot defendSlot) {
		// TODO Auto-generated method stub
		int[] battleValues = new int[2];
		
		int attackPosition = attackSlot.getPosition();
		int defendPosition = defendSlot.getPosition();
		
		System.out.println("Attack Position: " + attackPosition);
		System.out.println("Defend Position: " + defendPosition);
		
		if(attackPosition == defendPosition) {
			battleValues[ATTACK_INDEX] = attackSlot.getCardInSlot().getAttack(GameData.CENTER);
			battleValues[DEFEND_INDEX] = defendSlot.getCardInSlot().getDefense(GameData.CENTER);
			return battleValues;
		}
		
		switch(attackPosition) {
			case GameData.LEFT:
				battleValues[ATTACK_INDEX] = attackSlot.getCardInSlot().getAttack(GameData.RIGHT);
				battleValues[DEFEND_INDEX] = defendSlot.getCardInSlot().getDefense(GameData.LEFT);
				return battleValues;
			case GameData.RIGHT:
				battleValues[ATTACK_INDEX] = attackSlot.getCardInSlot().getAttack(GameData.LEFT);
				battleValues[DEFEND_INDEX] = defendSlot.getCardInSlot().getDefense(GameData.RIGHT);
				return battleValues;
		}
		
		if(attackPosition == GameData.CENTER) {
			switch(defendPosition) {
				case GameData.LEFT:
					battleValues[ATTACK_INDEX] = attackSlot.getCardInSlot().getAttack(GameData.LEFT);
					battleValues[DEFEND_INDEX] = defendSlot.getCardInSlot().getDefense(GameData.RIGHT);
					return battleValues;
				case GameData.RIGHT:
					battleValues[ATTACK_INDEX] = attackSlot.getCardInSlot().getAttack(GameData.RIGHT);
					battleValues[DEFEND_INDEX] = defendSlot.getCardInSlot().getDefense(GameData.LEFT);
					return battleValues;
			}
		}
		
		return battleValues;
	}

	/**
	 * Get Player Side Game State
	 * Given a PlayerSide, returns an integer representation of the player's Game State
	 * @param playerside
	 * @return
	 */
	public int getPlayerSideGameState(PlayerSide playerside) {
		CardSlot left = playerside.getLeft();
		CardSlot center = playerside.getCenter();
		CardSlot right = playerside.getRight();
		
		if(left.isEmpty() && center.isEmpty() && !right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_1CARD_R;
		}
		
		if(left.isEmpty() && !center.isEmpty() && right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_1CARD_C;
		}
		
		if(!left.isEmpty() && center.isEmpty() && right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_1CARD_L;
		}
		
		if(left.isEmpty() && !center.isEmpty() && !right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_2CARDS_CR;
		}
		
		if(!left.isEmpty() && center.isEmpty() && !right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_2CARDS_LR;
		}
		
		if(!left.isEmpty() && !center.isEmpty() && right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_2CARDS_LC;
		}
		
		if(!left.isEmpty() && !center.isEmpty() && !right.isEmpty()) {
			return PLAYERSIDE_GAME_STATE_3CARDS;
		}
		
		return PLAYERSIDE_GAME_STATE_NOCARDS;
	}
	
	/**
	 * Print Valid Options for Attacker Card Selection
	 * Given a game state, returns a formatted string to print out the valid choices a player can make
	 * @param gamestate
	 * @return
	 */
	private String printValidOptionsForAttackerCardSelection(int gamestate) {
		StringBuilder s = new StringBuilder();
		
		if(!validStringInput.isEmpty()) {
			validStringInput.clear();
		}
		
		s.append("-- Choose a card to attack with. --\n");
		s.append("-- Valid choices: ");
		
		switch(gamestate) {
			case PLAYERSIDE_GAME_STATE_3CARDS: 
				s.append("left, center, or right --\n");
				validStringInput.add(INPUT_LEFT);
				validStringInput.add(INPUT_CENTER);
				validStringInput.add(INPUT_RIGHT);
				break;
			case PLAYERSIDE_GAME_STATE_2CARDS_LC: 
				s.append("left or center --\\n"); 
				validStringInput.add(INPUT_LEFT);
				validStringInput.add(INPUT_CENTER);
				break;
			case PLAYERSIDE_GAME_STATE_2CARDS_LR: 
				s.append("left or right --\\n"); 
				validStringInput.add(INPUT_LEFT);
				validStringInput.add(INPUT_RIGHT);
				break;
			case PLAYERSIDE_GAME_STATE_2CARDS_CR: 
				s.append("center or right --\\n"); 
				validStringInput.add(INPUT_CENTER);
				validStringInput.add(INPUT_RIGHT);
				break;
			case PLAYERSIDE_GAME_STATE_1CARD_L: 
				s.append("left --\\n"); 
				validStringInput.add(INPUT_LEFT);
				break;
			case PLAYERSIDE_GAME_STATE_1CARD_C: 
				s.append("center --\\n"); 
				validStringInput.add(INPUT_CENTER);
				break;
			case PLAYERSIDE_GAME_STATE_1CARD_R: 
				s.append("right --\\n"); 
				validStringInput.add(INPUT_RIGHT);
				break;
		}
		
		
		return s.toString();
	}
	
	/**
	 * Get Player Name
	 * Prompts user to enter their name.
	 * @return Returns a String with the user name.
	 */
	public String getPlayerName() {
		Scanner input = new Scanner(System.in);
		System.out.println("-- Please enter your name --");
		String playername = input.nextLine();
		
		while(playername.trim().isEmpty()) {
			System.out.println("!!!! Your name cannot be blank !!!!");
			System.out.println("-- Please enter your name --");
			playername = input.nextLine();
		}
		
		System.out.println("** " + playername + " , welcome to the battle! **");
		return playername;
	}
	
	/**
	 * Place Player Cards
	 * Method uses the players choices to set cards on the board.
	 * @param player
	 * @param board
	 */
	public void placePlayerCards(Player player, Board board) {
		int[] choices = chooseCardsFromHand(player.getHand());
		Card left = player.getHand().getCard(choices[GameData.LEFT]);
		Card center = player.getHand().getCard(choices[GameData.CENTER]);
		Card right = player.getHand().getCard(choices[GameData.RIGHT]);
		board.placeCard(player, GameData.LEFT, left);
		board.placeCard(player, GameData.CENTER, center);
		board.placeCard(player, GameData.RIGHT, right);
		
	}
	
	/**
	 * Read Instructions To List
	 * Method to read in a text file.
	 * @param fileName, The path to the instructions file
	 * @return Returns a List of Strings
	 */
	private static List<String> readInstructionsToList(String fileName){
		List<String> readLines = Collections.emptyList();
		try {
			readLines = Files.readAllLines(Paths.get(fileName),StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return readLines;
	}
	
	/**
	 * Print Instructions
	 * Method prompts user with a choice to print out the instructions.
	 */
	public void printInstructions() {
		Scanner input = new Scanner(System.in);
		System.out.println("-- Would you like to read the instructions? --\n"
							+ "-- Enter yes to view instructions.\n-- Enter anything else or just press enter to start the game --");
		String choice = input.nextLine();
		
		if(choice.toLowerCase().equals("yes")) {
			List instructions = readInstructionsToList("C:\\Users\\Koala\\git\\Perang\\instructions.txt");
			Iterator<String> instructionIterator = instructions.iterator();
			while(instructionIterator.hasNext()) {
				System.out.println(instructionIterator.next());
			}
			System.out.println("\n\n");
		}
	}
	
	/**
	 * Choose Cards From Hand
	 * Method prompts user to choose cards from their hand to place on the board.
	 * @param player
	 * @return Returns an integer array with card IDs
	 */
	private int[] chooseCardsFromHand(Hand hand) {
		
		int[] cardIDs = hand.getCardIDs();
		
		System.out.println("******\n");
		System.out.println("* Choose 3 cards to place on the board. *\n"
							+ "-------\n"
							+ hand.printPlayerHand() + "\n"
							+ "-- Choose a card for the left slot --\n"
							+ "-- Enter a card id from your hand  ");
		Scanner input = new Scanner(System.in);
		int[] choices = new int[GameData.NUM_SLOTS];
		choices[GameData.LEFT] = input.nextInt();
		
		while(!verifyCardIDSelection(choices[GameData.LEFT], cardIDs)) {
			System.out.println("!!!! Card ID not found in hand. !!!!");
			System.out.println("-- Choose a card for the left slot --\n" 
					+ "-- Enter a card id from your hand  ");
			choices[GameData.LEFT] = input.nextInt();
		}
		
		System.out.println("-- Choose a card for the center slot --\n" 
							+ "-- Enter a card id from your hand  ");
		
		choices[GameData.CENTER] = input.nextInt();
		
		while(!verifyCardIDSelection(choices[GameData.CENTER], cardIDs)) {
			System.out.println("!!!! Card ID not found in hand. !!!!");
			System.out.println("-- Choose a card for the center slot --\n" 
					+ "-- Enter a card id from your hand  ");
			choices[GameData.CENTER] = input.nextInt();
		}
		
		System.out.println("-- Choose a card for the right slot --\n" 
							+ "-- Enter a card id from your hand  ");
		
		choices[GameData.RIGHT] = input.nextInt();
		
		while(!verifyCardIDSelection(choices[GameData.RIGHT], cardIDs)) {
			System.out.println("!!!! Card ID not found in hand. !!!!");
			System.out.println("-- Choose a card for the right slot --\n" 
					+ "-- Enter a card id from your hand  ");
			choices[GameData.RIGHT] = input.nextInt();
		}
		
		return choices;
		
	}
	
	/**
	 * Verify Card ID Selection
	 * guards against invalid card selection
	 * @param cardID
	 * @param cardIDs
	 * @return
	 */
	private boolean verifyCardIDSelection(int cardID, int[] cardIDs) {
		for(int i = 0; i < cardIDs.length; i++) {
			if(cardID == cardIDs[i]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Set AI Cards
	 * Method chooses 3 cards from the AI's hand and sets them face down on the board.
	 * @param ai
	 * @param board
	 */
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
	
	/**
	 * Flip Card
	 * Method flips over a Card
	 * @param slot
	 */
	public void flipCard(CardSlot slot) {
		if(slot.isFaceUp()) {
			slot.setFaceUp(false);
		} else {
			slot.setFaceUp(true);
		}
	}
	
	/**
	 * Get Card Slot From Choice
	 * Method takes in the player's slots from the board and a String with
	 * left,center, or right to return the corresponding slot.
	 * @param slots
	 * @param choice
	 * @return Returns a Card Slot
	 */
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
	
	/**
	 * Set Attack Direction
	 * Method takes in the player's slots from the board and a String with
	 * left,center, or right to return an integer to represent an attack direction.
	 * @param slots
	 * @param choice
	 * @return Returns an integer to represent attack direction.
	 */
	private int setAttackDirection(String choice) {
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
	
	/**
	 * Set Attack Values
	 * Method determines the attack and defend values to be placed into an integer array.
	 * The values are determined based upon the attacker card location, the defender attack location
	 * and the corresponding attack/defense values from the cards.
	 * @param attackSlot
	 * @param defendSlot
	 * @param choice
	 * @param attackDirection
	 * @param attackValues
	 * @return
	 */
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
	
	/**
	 * Card Battle
	 * Method takes an attacker, a defender, and the board to determine a winner of the card battle.
	 * If the attacker or defender's card or both are face down, the card or cards are set face up.
	 * The loser's card is removed from the board.
	 * @param attacker
	 * @param defender
	 * @param board
	 */
	public void cardBattle(Player attacker,Player defender, Board board) {
		
		// create local variables
		PlayerSide attackerSide = attacker.getPlayerSide();
		PlayerSide defenderSide = defender.getPlayerSide();
		
		int attackDirection = -1;
		
		int[] attackValues = {-1,0};
		
		boolean attackerWins = false;
		
		Scanner input = new Scanner(System.in);
		
		String choice;
//		String[] validStrings = {"left","center","right"};
		
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
					attackDirection = this.setAttackDirection(choice);
				}
			} while (attackSlot == null && !validAttackDirection(attackDirection, attackSlot, defenderSide));
		
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
		attackValues = setAttackValues(attackSlot, defendSlot, choice, attackDirection, attackValues);
		
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
	
	/**
	 * Valid Attack Direction
	 * Guards against invalid attack directions
	 * @param attackDirection
	 * @param attackSlot
	 * @param defenderSide
	 * @return
	 */
	private boolean validAttackDirection(int attackDirection, CardSlot attackSlot, PlayerSide defenderSide) {
		// TODO Auto-generated method stub
		System.out.println("attack direction: " + attackDirection);
		
		if(attackSlot.isEmpty()) {
			return false;
		}
		
		if(attackSlot.getPosition() == GameData.CENTER) {
			switch(attackDirection) {
			case GameData.LEFT:
				if(defenderSide.getLeft().isEmpty()) {
					return false;
				}
				return true;
			case GameData.CENTER:
				if(defenderSide.getCenter().isEmpty()) {
					return false;
				}
				return true;
			default: 
				if(defenderSide.getRight().isEmpty()) {
					return false;
				}
				return true;
			}
		}
		
		// if attack slot is the left slot
		if(attackSlot.getPosition() == GameData.LEFT) {
			switch(attackDirection) {
			case GameData.LEFT: 
				if(defenderSide.getLeft().isEmpty()) {
					return false;
				}
				return true;
			case GameData.CENTER: 
				if(defenderSide.getCenter().isEmpty()) {
					return false;
				}
				return true;
			default: return false;
			}
		}
		
		// if attack slot is the
		if(attackSlot.getPosition() == GameData.RIGHT) {
			switch(attackDirection) {
			case GameData.RIGHT: 
				if(defenderSide.getRight().isEmpty()) {
					return false;
				}
				return true;
			case GameData.CENTER: 
				if(defenderSide.getCenter().isEmpty()) {
					return false;
				}
				return true;
			default: return false;
			}
		}
		
		return false;
	}

}