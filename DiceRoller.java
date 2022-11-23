package artemis.game;

import java.util.Random;
import java.util.Scanner;

public class DiceRoller {
	

	/**
	 * Constructor without arguments
	 */
	public DiceRoller() {
	}
	
	/**
	 * Method used to two roll the dice in game
	 * and determine how far a player moves 
	 * on the board
	 * 
	 * @param diceScanner
	 * @return
	 */
	public int rollDice(Scanner diceScanner) {
		
		int move = 0;
		
//		System.out.println("\n     Press any key and hit enter to roll the dice\n");
//		System.out.println("-------------------------------------------------------"); // 55
		Random rd = new Random();
		//String keyPress = sc.nextLine();
		if(diceScanner.hasNext()) {
			int die1 = rd.nextInt(6)+1;
			int die2 = rd.nextInt(6)+1;
			move = die1+die2;
		
			System.out.println("-------------------------------------------------------"); // 55
			System.out.println("\nDie 1 has a value of " +die1+ ".\nDie 2 has a value of " +die2+".\n");
			System.out.println("Please move " +move+ " spaces.\n");
			System.out.println("-------------------------------------------------------"); // 55
		}
		
		return move;
	}
	
}
