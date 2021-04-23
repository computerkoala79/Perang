/*
 * Created by Jerry Klos
 */
public class Player {
	
	private int playerid;
	private String name;
	private Hand hand;
	private PlayerDeck playerdeck;
	
	public Player(int playerid, String name) {
		this.playerid = playerid;
		this.name = name;
		hand = null;
		playerdeck = new PlayerDeck(GameData.PLAYER_STARTER_DECK_SIZE,this);

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}