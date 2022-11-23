package artemis.game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * 
 */

public class SystemDevelopment {
	static Scanner scSystemDevelopment = new Scanner(System.in);

	/**
	 * This method checks if systems can be developed
	 * 
	 * @param squares
	 * @param player
	 */
	public void developmentCheck(ArrayList<Systems> systems, ArrayList<Player> players, ArrayList<GameSquares> squares,
			Player player) {

		//strings for player name and system owners
		String playerInput, pName, sys1Owner, sys2Owner, sys3Owner, sys4Owner;
		
		//assigning values to each variable
		pName = player.getPlayerName();
		sys1Owner = systems.get(0).getSystemOwner();
		sys2Owner = systems.get(1).getSystemOwner();
		sys3Owner = systems.get(2).getSystemOwner();
		sys4Owner = systems.get(3).getSystemOwner();

		// if player doesn't own any complete systems
		if (!pName.equals(sys1Owner) && !pName.equals(sys2Owner) && !pName.equals(sys3Owner)
				&& !pName.equals(sys4Owner)) {
			System.out.println("-------------------------------------------------------"); // 55
			System.out.println("\nYou currently have no elements to develop.\n");
			System.out.println("-------------------------------------------------------"); // 55
		}

		//for system 1
		System.out.println("");
		if ((squares.get(1).getElementOwner().equals(player.getPlayerName()))
				&& (squares.get(2).getElementOwner().equals(player.getPlayerName()))) {

			System.out.println("-------------------------------------------------------\n"); // 55
			System.out.println("You own system 1, would you like to develop the elements \nwithin it? ");
			System.out.println("\n-------------------------------------------------------"); // 55

			do {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.printf("%39s %n", "> Please enter Yes or No <");
				System.out.println("\n-------------------------------------------------------"); // 55
				playerInput = scSystemDevelopment.next();
			} while (!playerInput.equalsIgnoreCase("Yes") && !playerInput.equalsIgnoreCase("No"));

			if (playerInput.equalsIgnoreCase("yes")) {
				developSystem(systems, players, squares.get(1), squares.get(2), null, player);
			} else if (playerInput.equalsIgnoreCase("no")) {
				System.out.println("-------------------------------------------------------"); // 55
			} else {
				System.out.println("Please enter Yes or No");
			}
		}

		
		// for system 2
		System.out.println("");
		if ((squares.get(3).getElementOwner().equals(player.getPlayerName()))
				&& (squares.get(4).getElementOwner().equals(player.getPlayerName()))
				&& (squares.get(5).getElementOwner().equals(player.getPlayerName()))) {

			System.out.println("-------------------------------------------------------\n"); // 55
			System.out.println("You own system 2, would you like to develop the elements within it?");
			System.out.println("\n-------------------------------------------------------"); // 55

			do {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.printf("%39s %n", "> Please enter Yes or No <");
				System.out.println("\n-------------------------------------------------------"); // 55
				playerInput = scSystemDevelopment.next();
			} while (!playerInput.equalsIgnoreCase("Yes") && !playerInput.equalsIgnoreCase("No"));

			if (playerInput.equalsIgnoreCase("yes")) {
				developSystem(systems, players, squares.get(3), squares.get(4), squares.get(5), player);
			} else if (playerInput.equalsIgnoreCase("no")) {
				System.out.println("-------------------------------------------------------"); // 55

			} 
		}

		//for system 3
		System.out.println("");
		if ((squares.get(7).getElementOwner().equals(player.getPlayerName()))
				&& (squares.get(8).getElementOwner().equals(player.getPlayerName()))
				&& (squares.get(9).getElementOwner().equals(player.getPlayerName()))) {

			System.out.println("-------------------------------------------------------"); // 55
			System.out.println("\nYou own system 3, would you like to develop the elements within it?\n");
			System.out.println("-------------------------------------------------------"); // 55

			do {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.printf("%39s %n", "> Please enter Yes or No <");
				System.out.println("\n-------------------------------------------------------"); // 55
				playerInput = scSystemDevelopment.next();
			} while (!playerInput.equalsIgnoreCase("Yes") && !playerInput.equalsIgnoreCase("No"));

			if (playerInput.equalsIgnoreCase("yes")) {
				developSystem(systems, players, squares.get(7), squares.get(8), squares.get(9), player);
			} else if (playerInput.equalsIgnoreCase("no")) {
				System.out.println("-------------------------------------------------------"); // 55

			} 
		}

		//for system 4
		System.out.println("");
		if ((squares.get(10).getElementOwner().equals(player.getPlayerName()))
				&& (squares.get(11).getElementOwner().equals(player.getPlayerName()))) {

			System.out.println("-------------------------------------------------------"); // 55
			System.out.println("\nYou own system 4, would you like to develop the elements within it?\n");
			System.out.println("-------------------------------------------------------"); // 55

			do {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.printf("%39s %n", "> Please enter Yes or No <");
				System.out.println("\n-------------------------------------------------------"); // 55
				playerInput = scSystemDevelopment.next();
			} while (!playerInput.equalsIgnoreCase("Yes") && !playerInput.equalsIgnoreCase("No"));

			if (playerInput.equalsIgnoreCase("yes")) {
				developSystem(systems, players, squares.get(10), squares.get(11), null, player);
			} else if (playerInput.equalsIgnoreCase("no")) {
				System.out.println("-------------------------------------------------------"); // 55

			} else {
				System.out.println("Please enter Yes or No");
			}

		}
	}

	/**
	 * After check interface to increase development level of elements
	 *
	 * @param element  first square owned
	 * @param element2 second square owned
	 * @param element3 (optional) third square owned
	 * @param player   - player who owns them
	 */
	public static void developSystem(ArrayList<Systems> systems, ArrayList<Player> players, GameSquares element1,
			GameSquares element2, GameSquares element3, Player player) {
		// Needs to take the checked and give the option to the user to increase the
		// development level

		int userSelection;
		String developConfirmation;
		int continueFlow, continueElementDev;

		continueFlow = 0;
		continueElementDev = 0;
		do {
			do {

				System.out.println("-------------------------------------------------------"); // 55
				System.out.println("\nThe current status of this system is\n");
				System.out.println(
						"1." + element1.getSquareName() + " development level: " + element1.getDevelopmentLevel());
				System.out.println(
						"2." + element2.getSquareName() + " development level: " + element2.getDevelopmentLevel());

				if (element3 != null) {
					System.out.println(
							"3." + element3.getSquareName() + " development level: " + element3.getDevelopmentLevel());
				}

				System.out.println(
						"\n > Please select the corresponding number \n   for the element you wish to develop < \n");
				System.out.println("-------------------------------------------------------"); // 55

				try {
					userSelection = scSystemDevelopment.nextInt();
					System.out.println("-------------------------------------------------------"); // 55
				} catch (InputMismatchException e) {
					System.out.println("-------------------------------------------------------"); // 55
					System.out.println("\nYou have entered an invalid character.");
					System.out.println("You will now be returned to the turn menu\n");
					System.out.println("-------------------------------------------------------"); // 55
					return;
				}

				switch (userSelection) {
				case 1:
					elementLevelUp(element1, players, player);
					break;
				case 2:
					elementLevelUp(element2, players, player);
					break;
				case 3:
					if (element3 == null) {
						System.out.println("\nYou have entered an invalid number.");
						System.out.println("You will now be returned to the turn menu\n");
						System.out.println("-------------------------------------------------------"); // 55
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							System.err.format("IOException: %s %n", e);
						}
						return;
					}
					elementLevelUp(element3, players, player);
					break;
				default:
					System.out.println("\n > Please input a number from the above list < ");
				}
			} while ((userSelection < 1) || (userSelection > 3));

			do {
				System.out.println("\nWould you like to develop another element in this system?\n");

				do {
					System.out.printf("%39s %n", "> Please enter Yes or No <");
					System.out.println("\n-------------------------------------------------------"); // 55
					developConfirmation = scSystemDevelopment.next();
				} while (!developConfirmation.equalsIgnoreCase("Yes") && !developConfirmation.equalsIgnoreCase("No"));

				if (developConfirmation.equalsIgnoreCase("yes")) {
					continueFlow = 1;
					continueElementDev = 1;
					// this needs to loop back to the top of the element selection list
				} else if (developConfirmation.equalsIgnoreCase("no")) {
					System.out.println("-------------------------------------------------------"); // 55
					continueFlow = 1;
					return;
				} else {
					System.out.println("Please input Yes or No");
					continueFlow = 0;
				}
			} while (continueFlow != 1);
		} while (continueElementDev == 1);
	}

	/**
	 * Method used to develop an element
	 * @param element
	 * @param players
	 * @param player
	 */
	public static void elementLevelUp(GameSquares element, ArrayList<Player> players, Player player) {

		String developConfirmation;
		int afterDevelopmentLevel;
		
		//if the element is fully developed
		if (element.getDevelopmentLevel() >= 4) {
			System.out.println("\n" + element.getSquareName() + " is fully developed\n");
			System.out.println("-------------------------------------------------------"); // 55
		}

		//if the element has yet to be fully developed
		if (element.getDevelopmentLevel() < 3) {
			System.out.println("\nYou've selected to develop " + element.getSquareName());
			System.out.println("\nDeveloping " + element.getSquareName() + " to the next level will cost "
					+ element.getDevelopmentCost());
			System.out.println("Your resources currently stand at " + player.getResources());
			System.out.println("\nDo you wish to develop this element?\n");

			afterDevelopmentLevel = element.getDevelopmentLevel() + 1;

			do {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.printf("%39s %n", "> Please enter Yes or No <");
				System.out.println("-------------------------------------------------------\n"); // 55
				developConfirmation = scSystemDevelopment.next();

			} while (!developConfirmation.equalsIgnoreCase("Yes") && !developConfirmation.equalsIgnoreCase("No"));

			//if the player would like to develop and has enough resources to do so
			if (developConfirmation.equalsIgnoreCase("yes") && player.getResources() >= element.getDevelopmentCost()) {
				player.setResources(-element.getDevelopmentCost());
				element.setDevelopmentLevel(afterDevelopmentLevel);
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.println(
						element.getSquareName() + " is now at development level " + element.getDevelopmentLevel());
				System.out.println("\nYour resources now stand at " + player.getResources());
				System.out.println("\n-------------------------------------------------------"); // 55
			} else if (developConfirmation.equalsIgnoreCase("yes")
					&& player.getResources() < element.getDevelopmentCost()) {
				//if the player would like to but does not have enough resources
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.println("Sorry, you don't have enough resources to develop " + element.getSquareName());
				System.out.println("\n-------------------------------------------------------"); // 55
			} else if (developConfirmation.equalsIgnoreCase("no")) {
				System.out.println("-------------------------------------------------------"); // 55
			}
		}

		if (element.getDevelopmentLevel() == 3) {
			System.out.println(
					"\nYou now have the opportunity to complete \nthe development of " + element.getSquareName() + ".");
			System.out.println(
					"\nThis will be an advanced development, and the final step \nof development for this element.");
			System.out.println("\nDeveloping " + element.getSquareName() + " to this final level will cost \n"
					+ element.getDevelopmentCost() + " resources.");
			System.out.println("\nDo you wish to fully develop this element?\n");
			System.out.println("-------------------------------------------------------"); // 55

			do {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.printf("%39s %n", "> Please enter Yes or No <");
				System.out.println("\n-------------------------------------------------------"); // 55
				developConfirmation = scSystemDevelopment.next();

			} while (!developConfirmation.equalsIgnoreCase("Yes") && !developConfirmation.equalsIgnoreCase("No"));

			afterDevelopmentLevel = element.getDevelopmentLevel() + 1;

			if (developConfirmation.equalsIgnoreCase("yes") && player.getResources() >= element.getDevelopmentCost()) {
				player.setResources(-element.getDevelopmentCost());
				element.setDevelopmentLevel(afterDevelopmentLevel);
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.println(element.getSquareName() + " is now fully developed: Development level "
						+ element.getDevelopmentLevel());
				System.out.println("\nYour resources now stand at " + player.getResources());
				System.out.println("\n-------------------------------------------------------"); // 55
			} else if (developConfirmation.equalsIgnoreCase("yes")
					&& player.getResources() < element.getDevelopmentCost()) {
				System.out.println("-------------------------------------------------------\n"); // 55
				System.out.println("Sorry, you don't have enough resources to develop " + element.getSquareName());
				System.out.println("\n-------------------------------------------------------"); // 55
			} else if (developConfirmation.equalsIgnoreCase("no")) {
				System.out.println("-------------------------------------------------------"); // 55
			}
		}

	}

}
