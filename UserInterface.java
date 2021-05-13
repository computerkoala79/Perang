import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	
	private static int aiStrategyTree = -1;
	
	private int getAILeftCardBestAttackDirection(Card left, int defenderGameState) {
		// attack direction is set to the center as the left card can only attack with
		// the center and right index value
		
		int highestAttackValue = -1;
		int attackDirection = GameData.CENTER;
		int[] attackValues = left.getAttackValues();
		for(int i = GameData.CENTER; i < attackValues.length; i++) {
			if(highestAttackValue < attackValues[i]) {
				highestAttackValue = attackValues[i];
				attackDirection = i;
			}
		}
		
		if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR || defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C
				&& attackDirection == GameData.CENTER) {
			attackDirection = GameData.RIGHT;
		}
		
		if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR || defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R
				&& attackDirection == GameData.RIGHT) {
			attackDirection = GameData.CENTER;
		}
		
		if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
			attackDirection = GameData.INVALID_ATTACK_DIRECTION;
		}
		
		return attackDirection;
	}
	
	private int getAICenterCardBestAttackDirection(Card center, int defenderGameState) {
		// attack direction is set to the center as the left card can only attack with
		// the center and right index value
				
		int highestAttackValue = -1;
		int attackDirection = GameData.LEFT;
		int[] attackValues = center.getAttackValues();
		for(int i = GameData.LEFT; i < attackValues.length; i++) {
			if(highestAttackValue < attackValues[i]) {
				highestAttackValue = attackValues[i];
				attackDirection = i;
			}
		}
				
		switch(attackDirection) {
			case GameData.LEFT:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR || 
					defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R ||
					defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					attackDirection = GameData.CENTER;
					// intentionally not breaking switch statement
				}
			case GameData.CENTER:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR || 
					defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R ||
					defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					attackDirection = GameData.RIGHT;
					// intentionally not breaking switch statement
				}
			case GameData.RIGHT:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC || 
					defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L ||
					defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					attackDirection = GameData.RIGHT;
				}
		}
				
		return attackDirection;
	}
	
	private CardSlot[] getAIAttackAndDefendSlots(int attackerGameState,int defenderGameState, PlayerSide ai, PlayerSide player) {
		CardSlot[] aiChoices = new CardSlot[2];
		
		Card left = ai.getLeft().getCardInSlot();
		Card center = ai.getCenter().getCardInSlot();
		Card right = ai.getRight().getCardInSlot();
		
		CardSlot attackSlot = null;
		CardSlot defendSlot = null;
		
		if(attackerGameState == PLAYERSIDE_GAME_STATE_3CARDS 
				&& defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS) {
			// currently just chooses a valid option, no strategy
			int attackSlotID = ThreadLocalRandom.current().nextInt(3);
			int defendSlotID = ThreadLocalRandom.current().nextInt(3);
			attackSlot = ai.getSlotByID(attackSlotID);
			defendSlot = ai.getSlotByID(defendSlotID);
		}
		
		// currently just chooses a valid option, no strategy
		
		switch(attackerGameState) {
		
			case PLAYERSIDE_GAME_STATE_3CARDS:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(3);
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(2);
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(2) + 1;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(3);
					while(attackSlotID == GameData.CENTER) {
						attackSlotID = ThreadLocalRandom.current().nextInt(3);
					}
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					int attackSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					int attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					int attackSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} break;
			
			case PLAYERSIDE_GAME_STATE_2CARDS_LC:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(2);
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(2);
					if(attackSlotID == GameData.LEFT) {
						int defendSlotID = GameData.CENTER;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					int attackSlotID = ThreadLocalRandom.current().nextInt(2);
					if(attackSlotID == GameData.CENTER) {
						int defendSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					int attackSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					int attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					int attackSlotID = GameData.CENTER;
					int defendSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(defendSlotID);
				} break;
				
			case PLAYERSIDE_GAME_STATE_2CARDS_LR:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					int attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					int attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					if(attackSlotID == GameData.LEFT) {
						int defendSlotID = GameData.CENTER;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					int attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					if(attackSlotID == GameData.RIGHT) {
						int defendSlotID = GameData.CENTER;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					int attackSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					int attackSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					int attackSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} break;
				
			case PLAYERSIDE_GAME_STATE_2CARDS_CR:
				
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					int attackSlotID = new Random().nextBoolean() ? GameData.CENTER : GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					int attackSlotID = new Random().nextBoolean() ? GameData.CENTER : GameData.RIGHT;
					if(attackSlotID == GameData.RIGHT) {
						int defendSlotID = GameData.CENTER;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					int attackSlotID = new Random().nextBoolean() ? GameData.CENTER : GameData.RIGHT;
					if(attackSlotID == GameData.CENTER) {
						int defendSlotID = new Random().nextBoolean() ? GameData.LEFT : GameData.RIGHT;
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(defendSlotID);
					} else {
						attackSlot = ai.getSlotByID(attackSlotID);
						defendSlot = player.getSlotByID(attackSlotID);
					}
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_R) {
					int attackSlotID = GameData.RIGHT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					int attackSlotID = GameData.CENTER;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(attackSlotID);
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					int attackSlotID = GameData.CENTER;
					int defendSlotID = GameData.LEFT;
					attackSlot = ai.getSlotByID(attackSlotID);
					defendSlot = player.getSlotByID(defendSlotID);
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
		
		aiChoices[ATTACK_INDEX] = attackSlot;
		aiChoices[DEFEND_INDEX] = defendSlot;
		return aiChoices;
	}
	
	private String printValidDefenderCardOptions(CardSlot attackSlot, int defenderGameState) {
		
		StringBuilder s = new StringBuilder();
		int attackPosition = attackSlot.getPosition();
		
		s.append("-- Choose a card to attack. --\n");
		s.append("-- Valid choices: ");
		
		switch(attackPosition) {
			case GameData.LEFT: 
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					s.append("left or center --\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR || defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					s.append("center --\n");
				} else {
					s.append("left --\n");
				} break;
			case GameData.CENTER:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS) {
					s.append("left,center, or right --\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					s.append("center or right --\\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC) {
					s.append("left or center --\\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LR) {
					s.append("left or right --\\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_L) {
					s.append("left --\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					s.append("center --\n");
				} else {
					s.append("right --\n");
				} break;
			default:
				if(defenderGameState == PLAYERSIDE_GAME_STATE_3CARDS || defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_CR) {
					s.append("center, or right --\n");
				} else if(defenderGameState == PLAYERSIDE_GAME_STATE_2CARDS_LC || defenderGameState == PLAYERSIDE_GAME_STATE_1CARD_C) {
					s.append("center --\n");
				} else {
					s.append("right --\n");
				} break;
		}
		
		return s.toString();
	}
	
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
			
			Scanner input = new Scanner(System.in);
			
			attackChoices = this.printValidOptionsForAttackerCardSelection(attackerGameState);
			System.out.println(attackChoices);
			String attackCardChoice = input.nextLine();
			
			attackSlot = this.getCardSlotFromChoice(attacker.getPlayerSide(), attackCardChoice);
			
			defendChoices = this.printValidDefenderCardOptions(attackSlot, defenderGameState);
			System.out.println(defendChoices);
			String defendCardChoice = input.nextLine();
			
			defendSlot = this.getCardSlotFromChoice(defender.getPlayerSide(), defendCardChoice);
			
		} else {
			// place A.I addition here
			CardSlot[] aiChoices = getAIAttackAndDefendSlots(attackerGameState,defenderGameState,attacker.getPlayerSide(),defender.getPlayerSide());
			attackSlot = aiChoices[this.ATTACK_INDEX];
			defendSlot = aiChoices[this.DEFEND_INDEX];
			// after A.I. addition has been added, remove the code below
			System.out.println("---- " + attacker.getName() + " ----");
			
//			Scanner input = new Scanner(System.in);
//			
//			attackChoices = this.printValidOptionsForAttackerCardSelection(attackerGameState);
//			System.out.println(attackChoices);
//			String attackCardChoice = input.nextLine();
//			
//			attackSlot = this.getCardSlotFromChoice(attacker.getPlayerSide(), attackCardChoice);
//			
//			defendChoices = this.printValidDefenderCardOptions(attackSlot, defenderGameState);
//			System.out.println(defendChoices);
//			String defendCardChoice = input.nextLine();
//			
//			defendSlot = this.getCardSlotFromChoice(defender.getPlayerSide(), defendCardChoice);
		}
		
		if(!attackSlot.isFaceUp()) {
			attackSlot.setFaceUp(true);
		}
		
		if(!defendSlot.isFaceUp()) {
			defendSlot.setFaceUp(true);
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
	
	private String printValidOptionsForAttackerCardSelection(int gamestate) {
		StringBuilder s = new StringBuilder();
		
		s.append("-- Choose a card to attack with. --\n");
		s.append("-- Valid choices: ");
		
		switch(gamestate) {
			case PLAYERSIDE_GAME_STATE_3CARDS: s.append("left, center, or right --\n"); break;
			case PLAYERSIDE_GAME_STATE_2CARDS_LC: s.append("left or center --\\n"); break;
			case PLAYERSIDE_GAME_STATE_2CARDS_LR: s.append("left or right --\\n"); break;
			case PLAYERSIDE_GAME_STATE_2CARDS_CR: s.append("center or right --\\n"); break;
			case PLAYERSIDE_GAME_STATE_1CARD_L: s.append("left --\\n"); break;
			case PLAYERSIDE_GAME_STATE_1CARD_C: s.append("center --\\n"); break;
			case PLAYERSIDE_GAME_STATE_1CARD_R: s.append("right --\\n"); break;
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
		int[] choices = chooseCardsFromHand(player);
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
							+ "-- Enter yes or no --");
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
	
	// currently unused
	private CardSlot getCardSlotChoice(String name, PlayerSide slots) {
		Scanner input = new Scanner(System.in);
		System.out.println("-- " + name + ", choose a card to attack with --");
		System.out.println("-- Enter left, center, right: ");
		String choice = input.nextLine();
		CardSlot attackSlot = this.getCardSlotFromChoice(slots, choice);
		return attackSlot;
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

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}