package artemis.game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * The purpose of this class is to hold the methods relating to the movement along the board and the subsequent 
 * actions that can potentially follow:
 * o Landing on the GO or Nothing Square
 * o Landing on a square that no one owns
 * o Landing on a square that you already own
 * o Landing on a square another player owns
 * These methods also deal with the purchase of squares and the charging of rent
 * 
 */

public class SquareOwnership extends Game {
	static Scanner scSquareOwnership = new Scanner(System.in);

	/*
	 * Method that allows the player to move along the board
	 */
	public void moveForward(int diceRoll, ArrayList<GameSquares> squares, ArrayList<Player> players,
			ArrayList<Systems> systems, Player player) {
		player.setPosition(diceRoll);

		squareLandedOn(squares, players, systems, player);
	}

	/**
	 * Method that determines whether a square is owned, unowned, or is a 'special
	 * square' (Blast Off! or Mission Break)
	 * 
	 * @param squares
	 * @param players
	 * @param systems
	 * @param player
	 */
	public static void squareLandedOn(ArrayList<GameSquares> squares, ArrayList<Player> players,
			ArrayList<Systems> systems, Player player) {
		int position = player.getPosition();

		String currentOwner = squares.get(position).getElementOwner();
		String playerName = player.getPlayerName();
		String squareName = squares.get(position).getSquareName();

		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("\nYou have landed on " + squareName + ".");
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		if (isSpecialSquare(squares, player) == false) {
			// If to decide pay rent
			// this means its owned, and is owned by the current player
			if (currentOwner.equals(playerName)) {
				try {
					System.out.println("\nYou already own this square.\n");
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.err.format("IOException: %s %n", e);
				}
				// does nothing, since player already owns this square
				return;
			} else if (!currentOwner.equals("")) { // This is owned but not by player
				isOwnedButNotCurrentPlayer(squares, players, systems, player);
			} else { // This is when the square is unowned
				squareIsUnowned(squares, players, player);
			}
		}
	}

	/**
	 * Method used if conditions in squareLandedOn are met for a square being owned
	 * Determines if the owned square is owned by the current player or another
	 * player If owned by current player, nothing happens and play moves to the next
	 * player If owned by a different player, then the current player may be charged
	 * rent, depending on the owner of that squares decision Owner can choose 'Yes'
	 * or 'No' in their decision to charge the current player
	 * 
	 * @param squares
	 * @param players
	 * @param systems
	 * @param player
	 */
	public static void isOwnedButNotCurrentPlayer(ArrayList<GameSquares> squares, ArrayList<Player> players,
			ArrayList<Systems> systems, Player player) {
		int position = player.getPosition();
		int rent = squares.get(position).getRent();

		String currentOwner = squares.get(position).getElementOwner();
		String playerAnswer = "";

		System.out.println("This square is already owned by " + currentOwner);
		System.out.println("You may therefore have to pay " + rent + " resources to " + currentOwner);
		System.out.println("\n" + lineSep + "\n"); // 55

		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println(currentOwner + ", would you like to charge the player?\n");
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		do {
			System.out.println(lineSep + "\n"); // 55
			System.out.printf("%39s %n", "> Please enter Yes or No <");
			System.out.println("\n" + lineSep); // 55
			playerAnswer = scSquareOwnership.next();

		} while (!playerAnswer.equalsIgnoreCase("Yes") && !playerAnswer.equalsIgnoreCase("No"));

		if (playerAnswer.equalsIgnoreCase("Yes") && player.getResources() > rent) {
			try {
				System.out.println(lineSep); // 55
				System.out.println("\nYou will now be charged " + rent + " resources\n");
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}
			player.setResources(-rent);

			for (Player owner : players) {
				if (owner.getPlayerName().equals(currentOwner)) {
					owner.setResources(rent);

				}
			}

		} else if (playerAnswer.equalsIgnoreCase("Yes") && player.getResources() < rent) {
			// funds too low method
			// game ends in failure
			fundsTooLow(players, squares, systems, player);

		} else if (playerAnswer.equalsIgnoreCase("No")) {
			// player is not charged rent
			System.out.println(lineSep); // 55
			System.out.println("\nNo one will be charged this turn.\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}
		} else {
			System.out.println("\nPlease enter Yes or No.\n");
		}

	}

	/**
	 * Method used if conditions in squareLandedOn are met for a square being
	 * unowned Offers player option to buy square based on their resources If they
	 * have enough, they're offered the chance to buy the square If they say yes,
	 * the price of the square is removed from their resources, and they become the
	 * owner If they say no, or if they don't have enough resources, then the square
	 * is offered to other players another player can buy, or if no one wants the
	 * square, it is left unowned until someone lands on it again
	 * 
	 * @param squares
	 * @param players
	 * @param player
	 */
	public static void squareIsUnowned(ArrayList<GameSquares> squares, ArrayList<Player> players, Player player) {
		// all booleans set to false to avoid issue of uninitialised variables
		boolean enoughresources = false;
		boolean playerCanBuySystem = false;
		boolean otherPlayerOwnSystem = false;

		int position = player.getPosition();
		int price = squares.get(position).getPriceOfSquare();
		int alternativePlayerChoice;

		// variables for the system the square belongs to
		// and the system owned by player
		ArrayList<Integer> pSystems = player.getPlayerSystem();
		int sSystem = squares.get(position).getSquareSystem();

		String squareName = squares.get(position).getSquareName();
		String playerAnswer = "";

		// determine if other players own squares on the system
		for (Player p : players) {
			// cycle through players in ArrayList
			if (p != player) {
				// check is other player
				if (p.getPlayerSystem().contains(sSystem)) {
					// if other player owns system -> this player cannot buy square
					otherPlayerOwnSystem = true;
					System.out.println("Other player owns system");
				}
			}
		}

		// determine if player can buy the system
		if (pSystems.contains(sSystem)) {
			// if player already owns system
			playerCanBuySystem = true;
			System.out.println("You own this system");
		} else if (!pSystems.contains(sSystem) && !otherPlayerOwnSystem) {
			// if no player owns the system
			playerCanBuySystem = true;
			System.out.println("No one owns this system");
		} else {
			// if other player owns this system
			playerCanBuySystem = false;
		}

		System.out.print("The price of " + squareName + " is " + price + ".");

		// check resources to see if they can buy
		if (player.getResources() >= price && playerCanBuySystem) {
			System.out.println("\nYou have enough resources! Would you like to buy this \nsquare?\n");
			System.out.println(lineSep); // 55

			enoughresources = true;

			do {
				System.out.printf("%39s %n", "\n> Please enter Yes or No <");
				System.out.println("\n" + lineSep); // 55
				playerAnswer = scSquareOwnership.next();
				System.out.println(lineSep); // 55

			} while (!playerAnswer.equalsIgnoreCase("Yes") && !playerAnswer.equalsIgnoreCase("No"));

		} else if (player.getResources() < price) {
			// player does not have enough resources
			System.out.println("\nSorry, you don't have enough resources to buy this square...\n");
			enoughresources = false;
		} else if (playerCanBuySystem == false) {
			// system is already owned by another player
			System.out.println("\nSorry, you may not buy this square...\n");
			enoughresources = false;
		}

		if (enoughresources == true && playerAnswer.equalsIgnoreCase("Yes")) {

			squares.get(position).setElementOwner(player.getPlayerName());
			player.setResources(-price);
			player.setPlayerSystem(sSystem);

			try {
				System.out.println("\n" + player.getPlayerName() + " is now the owner of " + squareName + ".\n");
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}
		} else if (playerAnswer.equalsIgnoreCase("No") || enoughresources == false) {

			System.out.println("\nPlease enter the number of the Player who wishes to \npurchase this square. "
					+ "\nIf no one wishes to purchase this square, please enter 0.\n");
			System.out.println(lineSep); // 55
			try {
				alternativePlayerChoice = scSquareOwnership.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\n" + lineSep + "\n"); // 55
				System.out.println("You have entered an invalid character.");
				System.out.println("Your turn will end for now.\n");
				System.out.println(lineSep); // 55
				return;
			}

			if (alternativePlayerChoice == 0) {
				// if no player wants to buy
				System.out.println(lineSep); // 55
				System.out.println("\nThis square will remain unowned for now.\n");
			} else {
				// if a player wants to buy

				// variable to use for the player's index in the ArrayList
				int pNum = alternativePlayerChoice - 1;

				// if the player has enough resources
				if (players.get(pNum).getResources() > price) {
					squares.get(position).setElementOwner(players.get(pNum).getPlayerName());
					players.get(pNum).setResources(-price);
					System.out.println(lineSep); // 55
					System.out.println(
							"\n" + squareName + " now belongs to Player: " + players.get(pNum).getPlayerName() + "\n");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						System.err.format("IOException: %s %n", e);
					}
				} else {
					System.out.println(lineSep + "\n");
					System.out.println("Unfortunately you do not have enough resources to buy this square.");
					System.out.println("This square will remain unowned for now.");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						System.err.format("IOException: %s %n", e);
					}

				}
			}
		}
	}

	/**
	 * Method to determine whether or not a square is special (Blast Off! or Mission
	 * Break squares)
	 * 
	 * @param squares
	 * @param player
	 * @return
	 */
	public static boolean isSpecialSquare(ArrayList<GameSquares> squares, Player player) {

		int position = player.getPosition();
		String squareName = squares.get(position).getSquareName();

		switch (squareName) {
		case "Mission Break":
			try {
				System.out.println("\nThis is an empty space.\n");
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}

			break;
		case "Blast Off!":
			try {
				System.out.println("Please collect 200.");
				// this 200 is collected through the Player class
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * Method used if a player is charged more rent than they can afford.
	 * This will cause the game to end in failure
	 * 
	 * @param players
	 * @param squares
	 * @param systems
	 * @param player
	 */
	public static void fundsTooLow(ArrayList<Player> players, ArrayList<GameSquares> squares,
			ArrayList<Systems> systems, Player player) {
		System.out.println("\nYou do not have enough funds to pay.\n");
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("\nERROR!");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("ERROR!");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("ERROR!");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("INSUFFICIENT FUNDS!!!!!!!");
			TimeUnit.SECONDS.sleep(2);
			System.out.println(
					"\nYou have run out of resources and therefore \ncan no longer afford to finance the misson.");
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\nThe mission has failed. You have all lost...");
			TimeUnit.SECONDS.sleep(2);
			System.out.println("");
			System.out.println("/////////////////////////");
			System.out.println("/                       /");
			System.out.println("/\tGAME OVER\t/");
			System.out.println("/                       /");
			System.out.println("/////////////////////////");
			System.out.println("");
			TimeUnit.SECONDS.sleep(2);

		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		// display results at the end of the game
		System.out.println("\n" + lineSep + "\n");
		System.out.println("\nRESULTS:\n");

		for (Player p : players) {
			displayStats(squares, systems, p);
		}
		restartGameBad();
	}

	public static void restartGameBad() {
		String playerResponse;
		// Scanner sc = new Scanner(System.in);

		try {
			TimeUnit.SECONDS.sleep(3);
			System.out.println(lineSep); // 55
			System.out.println("\nWould you like to try again?");
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		do {
			System.out.println("\n > Please enter Yes or No. < \n");
			System.out.println(lineSep); // 55
			playerResponse = scSquareOwnership.next();
		} while (!playerResponse.equalsIgnoreCase("yes") && !playerResponse.equalsIgnoreCase("No"));

		if (playerResponse.equalsIgnoreCase("Yes")) {
			ArtemisLite();

		} else if (playerResponse.equalsIgnoreCase("No")) {
			System.out.println("\nSee you soon!");
			System.exit(0);
		}

	}

}
