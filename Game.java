package artemis.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit; //used for pauses in game 
import java.util.Scanner;

public class Game {

	static Scanner sc = new Scanner(System.in);

	// creating a global string variable of hyphens
	// will be used to print a visible separation between players' turns
	public static String lineSep = "-------------------------------------------------------";

	// main method
	public static void main(String[] args) {

		ArtemisLite();
		sc.close();

	}

	/**
	 * @throws InputMismatchException
	 */
	public static void ArtemisLite() throws InputMismatchException {

		// variables and objects
		boolean loadGame;
		int playerChoice = 0;
		int numOfPlayers = 0;
		String quitGame;
		DiceRoller dice = new DiceRoller();
		SystemDevelopment sd = new SystemDevelopment();
		SquareOwnership so = new SquareOwnership();

		int numberRolled = 0;

		// This is game beginning
		loadGame = welcomeToTheGame();

		/*
		 * ArrayList used to store the number of players. The number of players is
		 * determined through the registerNumberOfPlayers method.
		 */
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<GameSquares> squares = new ArrayList<GameSquares>();
		ArrayList<Systems> systems = new ArrayList<Systems>();

		// if the player selected load from the initial options menu
		if (loadGame) {
			setSquares(squares);
			setSystems(systems);

			players = SaveLoadGame.loadPlayer();
			squares = SaveLoadGame.loadSquare();
			systems = SaveLoadGame.loadSystem();

			numOfPlayers = players.size();

			int pCount = 1;
			for (Player p : players) {
				// cycle through players printing their stats
				System.out.println("\n" + lineSep + "\n");
				System.out.println("Player " + pCount + ": " + p.getPlayerName());
				displayStats(squares, systems, p);
				pCount++;
			}

			// else a new game
		} else {
			setSquares(squares);
			setSystems(systems);

			// registers the number of players
			numOfPlayers = registerNumberOfPlayers();
			try {
				TimeUnit.SECONDS.sleep(0);
				System.out.println("\nPlease register the names of the players.");
				System.out.println("The same name cannot be registered twice.");
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}

			for (int i = 0; i < numOfPlayers; i++) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("\n > Please enter the name of Player " + (i + 1) + " < \n");
				System.out.println(lineSep); // 55
				players.add(new Player(scanner.nextLine()));
				System.out.println(lineSep); // 55
			}

			// check that stops the users from playing with the same name
			try {
				for (int i = 0; i < players.size() - 1; i++) {
					for (int j = i + 1; j < players.size(); j++) {
						if (players.get(i).getPlayerName().equalsIgnoreCase(players.get(j).getPlayerName())) {
							do {
								Scanner scanner = new Scanner(System.in);
								System.out.println("\nPlayer " + (j + 1) + ", this name has already been taken.");
								System.out.println("Please enter a different name.");
								System.out.println("\n" + lineSep); // 55
								players.get(j).setPlayerName(scanner.nextLine());
								System.out.println(lineSep);
							} while (players.get(i).getPlayerName().equalsIgnoreCase(players.get(j).getPlayerName()));
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Error - the game will now restart.");
				ArtemisLite();
			}
			System.out.println();
			for (int i = 0; i < players.size(); i++) {
				System.out.println("Name of Player " + (i + 1) + " : " + players.get(i).getPlayerName());
			}
		}

		System.out.println("\n" + lineSep); // 55

		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("\nAll players are awarded 1000 resources at the start of \nthe game.");
			System.out.println("When a player passes Blast Off!, an additional 200 is awarded.\n");
			TimeUnit.SECONDS.sleep(4);
			System.out.println("The aim of this game is to be strategic.");
			System.out.println("You cannot trade, and so you must think carefully about \nwhich elements you can buy.");
			System.out.println("Failure to do so will cost you the mission...\n");
			TimeUnit.SECONDS.sleep(4);
			System.out.println("\t   The game will now begin.\n");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		// This for loop is the players turn, after which it goes to the next player
		for (int i = 0; i < numOfPlayers; i++) {
			System.out.println(lineSep + "\n");
			System.out.println(players.get(i).getPlayerName() + " (Player " + (i + 1) + "), it is now your turn.\n");

			do {
				System.out.println("");
				System.out.println("TURN MENU: ");
				System.out.println("1. Roll Dice");
				System.out.println("2. Show Stats");
				System.out.println("3. Develop System(s)");
				System.out.println("4. Quit\n");
				System.out.println("\n" + lineSep + "\n");
				System.out.printf("%48s %n", "> Please select an option between 1 & 4 <");
				System.out.println("\n" + lineSep);

				try {
					playerChoice = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println(lineSep + "\n"); // 55
					System.out.println("An error has occurred...");
					System.out.println("You have entered an invalid character...");
					System.out.println("The dice will now automatically roll.");
					break;
				}

				switch (playerChoice) {
				case 1:
					// Roll dice, goes to diceRoller method following this loop
					System.out.println(lineSep); // 55
					System.out.println("\n  > Press any key and hit enter to roll the dice <\n");
					System.out.println(lineSep); // 55
					break;
				case 2:
					// Shows the players resources
					System.out.println(lineSep); // 55
					System.out.println("\nYour current statistics are as follows:\n");
					displayStats(squares, systems, players.get(i));
					break;
				case 3:
					// Allows player to develop a system
					sd.developmentCheck(systems, players, squares, players.get(i));
					break;
				case 4:
					// quits the game
					System.out.println(lineSep + "\n");
					System.out.println("Would you like to quit the game?\n");

					do {
						System.out.println(lineSep + "\n");
						System.out.printf("%39s %n", "> Please enter Yes or No <");
						System.out.println("\n" + lineSep);
						quitGame = sc.next();
					} while (!(quitGame.equalsIgnoreCase("Yes")) && !(quitGame.equalsIgnoreCase("No")));

					if (quitGame.equalsIgnoreCase("Yes")) {
						System.out.println(lineSep + "\n");
						try {
							System.out.println("You have chosen to quit.");
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							System.err.format("IOException: %s %n", e);
						}

						// display results at the end of the game
						System.out.println("\n" + lineSep + "\n");
						System.out.println("\nRESULTS:\n");

						for (Player p : players) {
							displayStats(squares, systems, p);
						}
						System.out.println("\n" + lineSep + "\n");

						try {
							TimeUnit.SECONDS.sleep(1);
							System.out.println("See you next time...");
							System.out.println("\n" + lineSep);
						} catch (InterruptedException e) {
							System.err.format("IOException: %s %n", e);
						}
						System.exit(playerChoice);
					} else if (quitGame.equalsIgnoreCase("No")) {
						System.out.println(lineSep);
						System.out.println("\nYou have chosen not to quit.\n");
						System.out.println(lineSep);
						break;
					}
				}

			} while (!(playerChoice == 1));

			numberRolled = dice.rollDice(sc);

			// move forward that number of spaces (SquareOwnership contains
			// all methods involving the movement along and ownership of the game's squares)
			so.moveForward(numberRolled, squares, players, systems, players.get(i));

			// at the end of every turn, display what everyone owns
			System.out.println(lineSep);
			displayStats(squares, systems, players.get(i));

			// Save player stats to an external file
			SaveLoadGame.savePlayers(players);
			// Save squares stats to an external file
			SaveLoadGame.saveSquares(squares);
			// Save systems stats to an external file
			SaveLoadGame.saveSystems(systems);

			// end turn
			endTurn(squares, players.get(i));

			/*
			 * loop to determine how many fully developed squares there are
			 */
			int developedSquares = 0;

			for (int loop = 0; loop < squares.size(); loop++) {
				if (squares.get(loop).getDevelopmentLevel() == 4) {
					developedSquares++;
				}
			}

			// check that all squares (except start and break squares) are fully developed
			if (developedSquares == (squares.size() - 2)) {
				gameOverGood(players, squares, systems, players.get(i));
			}

			// loop to repeat the turns
			if (i == numOfPlayers - 1) {
				i = -1;
			}
		}
	}

	/**
	 * Start of game - the first method run
	 */
	public static boolean welcomeToTheGame() {

		int userSelection = 0;
		int set = 0;
		boolean loadGame = false;

		System.out.printf("%38s %n", "Welcome To ArtemisLite");
		System.out.println("\nHelp land the first woman and man of colour on the moon");
		System.out.println(lineSep); // 55

		do {
			System.out.println();
			System.out.printf("%32s %n", "GAME MENU:"); // this is the syntax to move the text right
			System.out.println("\n1. Start Game");
			System.out.println("2. Game Instructions ");
			System.out.println("3. Load Game\n");
			System.out.printf("%39s %n", "> Please Select 1, 2 or 3 <\n");
			System.out.println(lineSep); // 55

			try {
				userSelection = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(lineSep + "\n"); // 55
				System.out.println("An error has occurred...");
				System.out.println("You have entered an invalid character...");
				restartGameExceptions();
			}

			switch (userSelection) {
			case 1:
				set = 1;
				loadGame = false;
				break; // return did not work
			case 2:
				set = 1;
				gameInstructions();
				break;
			case 3:
				set = 1;
				loadGame = true;
				break;
			default:
				System.out.println("Please select 1 or 2\n");
				set = 0;
				break;
			}
		} while (set != 1);

		return loadGame;
	}

	/**
	 * Game instruction interface
	 */
	public static void gameInstructions() {
		int userSelection = 0;

		do {
			System.out.println();
			System.out.println(lineSep); // 55
			System.out.printf("%40s %n", "Instructions on How to Play");
			System.out.println("\nWhat section of the instructions would you like to view?");
			System.out.println("1. Movement");
			System.out.println("2. Purchasing");
			System.out.println("3. Development");
			System.out.println("4. Aim of The Game");
			System.out.println("5. Quit Instructions Page\n");
			System.out.printf("%48s %n", "> Please select an option between 1 & 5 <\n");
			System.out.println(lineSep); // 55

			try {
				userSelection = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(lineSep + "\n"); // 55
				System.out.println("An error has occurred...");
				System.out.println("You have entered an invalid character...");
				restartGameExceptions();
			}

			switch (userSelection) {
			case 1:
				System.out.println("\nMOVEMENT:\n");
				System.out.println("Each player must take it in turns to roll the dice");
				System.out.println("The player who rolled will then move around the board");
				System.out.println("a number of squares equal to that of the dies total");
				System.out.println("If the player passes the 'collect resources' square");
				System.out.println("they gain 200 resources");
				break;
			case 2:
				System.out.println("\nPURCHASING:\n");
				System.out.println("If a player lands on an unowned element, they can  ");
				System.out.println("purchase it using their resources. If the player does not");
				System.out.println("wish to purchase the element or does not have sufficient ");
				System.out.println("resources they can offer it to another player who does.\n");
				System.out.println("If they land on owned element, they must pay the ");
				System.out.println("owner the set amount of resources.");

				break;
			case 3:
				System.out.println("\nDEVELOPMENT:\n");
				System.out.println("A player can develop an element only if they own all ");
				System.out.println("elements in that system. Elements can be developed ");
				System.out.println("regardless of where the player is located on the board.");
				System.out.println("Once an element has been developed 3 times, it can be ");
				System.out.println("'Further developed'. It is then complete.");
				break;
			case 4:
				System.out.println("\nAIM:\n");
				System.out.println("The aim of the game is to develop every element");
				System.out.println("without any of the players running out of resources");
				System.out.println("However it is up to the individual player on how they ");
				System.out.println("want to play");
				break;
			case 5:
				System.out.println("\nReturning to Welcome Menu\n");
				System.out.println(lineSep);
				welcomeToTheGame();
				break;
			default:
				System.out.println("\n > Please select an option 1 to 5 < \n");
				break;
			}
		} while (userSelection != 5);
	}

	/**
	 * Method to register the number of players who are going to play this game can
	 * only enter a number between 2 and 4 game asks the player to enter a correct
	 * value if this condition is not met
	 * 
	 * @return
	 */
	public static int registerNumberOfPlayers() {

		int numberOfPlayers = 0;
		System.out.println(lineSep + "\n"); // 55
		System.out.printf("%39s %n", " > Please enter the number of players < ");
		System.out.println("(2-4 players are required to play this game)\n");
		System.out.println(lineSep); // 55

		try {
			numberOfPlayers = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(lineSep); // 55
			System.out.println("\nAn error has occurred...");
			System.out.println("You have entered an invalid character...");
			restartGameExceptions();
		}

		while (numberOfPlayers <= 1 || numberOfPlayers >= 5) {
			System.out.println(lineSep); // 55
			System.out.println("\nGame requires 2-4 players. Please enter a different amount.\n");
			System.out.println(lineSep); // 55
			numberOfPlayers = sc.nextInt();
		}
		try {
			System.out.println(lineSep); // 55
			System.out.println("\nYou have registered " + numberOfPlayers + " players.");
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		return numberOfPlayers;

	}

	/**
	 * Method that displays the player stats
	 * 
	 * @param squares
	 * @param systems
	 * @param player
	 */
	public static void displayStats(ArrayList<GameSquares> squares, ArrayList<Systems> systems, Player player) {

		System.out.println("Player: " + player.getPlayerName() + "\n");

		System.out.println("Resources : \n" + player.getResources());

		System.out.println("\n\nElements Owned: ");
		for (GameSquares s : squares) {
			if (s.getElementOwner().equals(player.getPlayerName())) {
				System.out.println(s.getSquareName() + " - Development Level: " + s.getDevelopmentLevel());
			}
		}

		System.out.print("\nSystems Owned: ");
		for (Systems s : systems) {
			if (s.getSystemOwner().equals(player.getPlayerName())) {
				System.out.println(s.getSystemName());
			}
		}
		System.out.println("\n" + lineSep); // 55
	}

	/**
	 * 
	 * Method that allows the player to end their turn
	 * 
	 * @param squares
	 * @param player
	 */
	private static void endTurn(ArrayList<GameSquares> squares, Player player) {

		sc.next();
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("\n\t > Press any key to end your turn. < \n");
			System.out.println(lineSep); // 55
			sc.next();
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

	}

	/**
	 * Method that displays a 'good' ending to the game ('bad' game ending method is
	 * found in SquaresOwnership class)
	 * 
	 * @param players
	 * @param squares
	 * @param systems
	 * @param player
	 */
	private static void gameOverGood(ArrayList<Player> players, ArrayList<GameSquares> squares,
			ArrayList<Systems> systems, Player player) {

		try {
			System.out.println(lineSep);
			System.out.println("\nSystems developing.");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Systems developing..");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("Systems developing...");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("\nAll Systems Are GO!");
			TimeUnit.SECONDS.sleep(1);
			System.out.println(
					"\nCongratulations! The mission was a success! " + "\nArtemis is now ready for launch...\n");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		// Countdown timer to simulate a rocket launch
		for (int i = 10; i > 0; i--) {
			System.out.println(i);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.err.format("IOException: %s %n", e);
			}
		}

		// End game text to inform players about how their actions
		// affected the Artemis Program
		try {
			System.out.println("\nBLAST OFF!!!\n");
			System.out.println(lineSep);
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\n-The Timeline of The Artemis Program-");
			TimeUnit.SECONDS.sleep(1);
			System.out.println("\nJune 2022:");
			System.out.println("Artemis I goes ahead.");
			System.out.println("NASA launches its Orion Spacecraft attached to The Space Launch System.");
			System.out.println("The uncrewed mission completes an orbit of the Moon before returning to earth");
			TimeUnit.SECONDS.sleep(3);
			System.out.println("\nMay 2024:");
			System.out.println("Artemis II launches the same module.");
			System.out.println("A crew of four astronauts complete an orbit of the Moon.");
			System.out.println("They travel further into the solar system than any human has ever gone.");
			TimeUnit.SECONDS.sleep(3);
			System.out.println("\nApril 2025");
			System.out.println("Artemis III successfully lands on the Moon.");
			System.out.println("This is the first lunar landing since 1972.");
			System.out.println("\nWith your help The Artemis Program has paved \n"
					+ "\nthe way for a permanent human setlement on The Moon\n");
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		// final play stats
		System.out.println(lineSep + "\n");
		System.out.println("\nRESULTS:\n");

		for (Player p : players) {
			displayStats(squares, systems, p);
		}

		// use the winner class to calculate the player with the largest amount of
		// resources
		winner(players);
		restartGameGood();
	}

	/**
	 * Method that determines the 'winner' (player that has the most resources at
	 * the end of the game)
	 * 
	 * @param players
	 */
	public static void winner(ArrayList<Player> players) {
		System.out.println("\n" + lineSep + "\n"); // 55
		System.out.println("Congratulations! All players successfully completed the mission,");
		System.out.println("however one player accumulated the largest amount of resources.");

		// Using the compare class to sort the players ArrayList according to their
		// resources
		Collections.sort(players, new ComparePlayerResources());

		// Now the first player in the ArrayList has the largest amount of resources
		System.out.println(players.get(0).getPlayerName() + ", you are the winner!\n");
	}

	/**
	 * Method that allows the player to restart the game
	 */
	public static void restartGameGood() {
		String playerResponse;

		try {
			TimeUnit.SECONDS.sleep(3);
			System.out.println(lineSep); // 55
			System.out.println("\nThank You for playing Artemis Lite. Would you like to play again?");
		} catch (InterruptedException e) {
			System.err.format("IOException: %s %n", e);
		}

		do {
			System.out.println("\n > Please enter Yes or No. < \n");
			System.out.println(lineSep); // 55
			playerResponse = sc.next();
		} while (!playerResponse.equalsIgnoreCase("yes") && !playerResponse.equalsIgnoreCase("No"));

		if (playerResponse.equalsIgnoreCase("Yes")) {
			ArtemisLite();

		} else if (playerResponse.equalsIgnoreCase("No")) {
			System.out.println("\nSee you soon!");
			System.exit(0);
		} else {
			System.out.println("Please enter Yes or No.");
			System.out.println(lineSep); // 55
		}

	}

	/**
	 * Method for restarting the game when an exception is triggered
	 */
	public static void restartGameExceptions() {
		sc.next();
		System.out.println("Press any key and hit enter to restart the game.\n");
		System.out.println(lineSep); // 55
		sc.next();
		System.out.println(lineSep); // 55
		System.out.println("");
		ArtemisLite();
	}

	/**
	 * Adds instances of the GameSquares class, which contain information about the
	 * name, price, development cost and rent of the game's elements, to an
	 * ArrayList
	 * 
	 * @param squares
	 */
	public static void setSquares(ArrayList<GameSquares> squares) {
		squares.add(new GameSquares("Blast Off!", 0, 0, 0, 5));
		squares.add(new GameSquares("Orion Survival Suit (Suit Technology)", 50, 25, 10, 1));
		squares.add(new GameSquares("XEMU Spacesuit (Suit Technology)", 100, 50, 20, 1));
		squares.add(new GameSquares("Human Landing System (Deep Space Transport)", 150, 75, 30, 2));
		squares.add(new GameSquares("Space Launch System (Deep Space Transport)", 200, 100, 40, 2));
		squares.add(new GameSquares("Orion Spacecraft (Deep Space Transport)", 250, 125, 50, 2));
		squares.add(new GameSquares("Mission Break", 0, 0, 60, 5));
		squares.add(new GameSquares("Lunar Terrain Vehicle (Landing Site Exploration)", 300, 150, 70, 3));
		squares.add(new GameSquares("Viper Robotic Scout (Landing Site Exploration)", 350, 175, 80, 3));
		squares.add(new GameSquares("Lunar Reconnaissance Orbiter (Landing Site Exploration)", 400, 200, 90, 3));
		squares.add(new GameSquares("Deep Space Network (Space Infrastructure)", 450, 225, 100, 4));
		squares.add(new GameSquares("The Gateway (Space Infrastructure)", 500, 250, 150, 4));
	}

	/**
	 * Adds instances of the Systems class, containing the name and number of the
	 * systems, to an ArrayList
	 * 
	 * @param systems
	 */
	public static void setSystems(ArrayList<Systems> systems) {
		systems.add(new Systems(1, "System 1 - Suit Technology"));
		systems.add(new Systems(2, "System 2 - Deep Space Transport"));
		systems.add(new Systems(3, "System 3 - Landing Site Exploration"));
		systems.add(new Systems(4, "System 4 - Space Infrastructure"));
	}

}
