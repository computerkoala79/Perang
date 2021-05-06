import java.util.Scanner;

/*
 * Created by Jerry Klos
 */
public class UserInterface {
	
	public String getPlayerName() {
		Scanner input = new Scanner(System.in);
		System.out.println("-- Please enter your name --");
		String playername = input.nextLine();
		System.out.println("** " + playername + " , welcome to the battle! **");
		return playername;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}