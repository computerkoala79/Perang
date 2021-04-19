/*
 * Created by Jerry Klos
 */
public class CardSlot {
	
	private int position;
	private int[] canAttack = new int[3];
	private Card card;
	private int player;
	
	public CardSlot (int position) {
		this.position = position;
		buildCanAttack(position);
	}
	
	private void buildCanAttack(int position) {
		switch(position) {
			case 0: canAttack[0] = 0;
					canAttack[1] = 1;
					canAttack[2] = -1;
					break;
			case 1: canAttack[0] = 0;
					canAttack[1] = 1;
					canAttack[2] = 2;
					break;
			case 2: canAttack[0] = -1;
					canAttack[1] = 1;
					canAttack[2] = 2;
					break;
		}
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}